package com.icooking.service.impl;

import com.icooking.context.BaseContext;
import com.icooking.dto.UserLikeClickDTO;
import com.icooking.dto.UserLoginDTO;
import com.icooking.dto.UserMarkClickDTO;
import com.icooking.dto.UserRegistryDTO;
import com.icooking.entity.Like;
import com.icooking.entity.Mark;
import com.icooking.entity.Recipe;
import com.icooking.entity.User;
import com.icooking.exception.EntryException;
import com.icooking.exception.RegistryException;
import com.icooking.mapper.LikeMapper;
import com.icooking.mapper.MarkMapper;
import com.icooking.mapper.RecipeMapper;
import com.icooking.mapper.UserMapper;
import com.icooking.properties.JWTProperties;
import com.icooking.service.UserService;
import com.icooking.utils.JWTUtils;
import com.icooking.vo.UserLikeClickVO;
import com.icooking.vo.UserLoginVO;
import com.icooking.vo.UserMarkClickVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTProperties jwtProperties;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private MarkMapper markMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(UserRegistryDTO userRegistryDTO) {
        String newUserName = userRegistryDTO.getUsername();

        User user = userMapper.findUserByUserName(newUserName);
        if (user != null) {
            throw new RegistryException("用户名已存在");

        }
        user = new User();
        // 对象属性拷贝
        BeanUtils.copyProperties(userRegistryDTO, user);
        user.setIsAdmin(0);
        String rawPassWord = user.getPassword();
        user.setPassword(DigestUtils.sha256Hex(rawPassWord));

        userMapper.insertUser(user);
    }

    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.findUserByUserName(username);

        if (user == null) {
            throw new EntryException("账号不存在");
        }

        password = DigestUtils.sha256Hex(password);
        if (!password.equals(user.getPassword())) {
            throw new EntryException("密码错误");
        }

        return user;
    }

    @Override
    public UserLoginVO getTokenForUserLogin(User user) {
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JWTUtils.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .token(token)
                .build();

        return userLoginVO;
    }

    // 未用redis优化的点赞功能
//    @Override
//    public void processLikeClick(UserLikeClickDTO userLikeDTO) {
//
//        int recipeId = userLikeDTO.getRecipeId();
//        int userId = BaseContext.getCurrentUserId();
//
//        Like like = likeMapper.findLikeByRecipeIdAndUserId(recipeId, userId);
//
//        if (like == null) {
//            like = new Like(-1,  userId, recipeId, 1);
//            likeMapper.insertLike(like);
//            recipeMapper.addOneLikes(recipeId);
//        }
//        else {
//            likeMapper.revertLikeStatus(like);
//            if (like.getStatus() == 1) {
//                recipeMapper.subOneLikes(recipeId);
//            }
//            else {
//                recipeMapper.addOneLikes(recipeId);
//            }
//        }
//    }

    /**
     * 点赞以及回显点赞数功能
     * 使用redis实现实时回显
     * @param userLikeDTO
     * @return
     */
    @Transactional
    @Override
    public UserLikeClickVO processLikeClick(UserLikeClickDTO userLikeDTO) {

        int recipeId = userLikeDTO.getRecipeId();
        int userId = BaseContext.getCurrentUserId();
        String recipeUserStatus = "recipe:" + recipeId + " user:" + userId + " status";
        String recipeLikes = "recipe:" + recipeId + ":LikesCount";

        Integer status = (Integer) redisTemplate.opsForValue().get(recipeUserStatus);
        Integer likesCount = (Integer) redisTemplate.opsForValue().get(recipeLikes);

        // 缓存未命中
        if (status == null || likesCount == null) {

            Like like = likeMapper.findLikeByRecipeIdAndUserId(recipeId, userId);
            // MySQL数据库中还没有点赞记录
            if (like == null) {
                like = new Like(-1,  userId, recipeId, 1);
                likeMapper.insertLike(like);
                recipeMapper.addOneLikes(recipeId);
            }
            else {
                likeMapper.revertLikeStatus(like);
                // 注意，此处判断时，like还为进行本次点赞前的记录
                if (like.getStatus() == 1) {
                    like.setStatus(0);
                    recipeMapper.subOneLikes(recipeId);
                }
                else {
                    like.setStatus(1);
                    recipeMapper.addOneLikes(recipeId);
                }
            }

            Integer statusValue = like.getStatus();
            Integer likeCountValue = recipeMapper.getLikesCount(recipeId);
            redisTemplate.opsForValue().set(recipeUserStatus, statusValue);
            redisTemplate.opsForValue().set(recipeLikes, likeCountValue);

        }
        // 缓存命中
        else {
            if (status == 1) {
                // !! 只有使用StringRedisTemplate序列化器才能使用decr和incr方法
                //  redisTemplate.opsForValue().decrement(recipeUserStatus);
                //  redisTemplate.opsForValue().decrement(recipeLikes);
                status --;
                redisTemplate.opsForValue().set(recipeUserStatus, status);
                likesCount --;
                redisTemplate.opsForValue().set(recipeLikes, likesCount);
            }
            else {
                //  redisTemplate.opsForValue().increment(recipeUserStatus);
                //  redisTemplate.opsForValue().increment(recipeLikes);
                status ++;
                redisTemplate.opsForValue().set(recipeUserStatus, status);
                likesCount ++;
                redisTemplate.opsForValue().set(recipeLikes, likesCount);
            }
        }

        int resultStatus = (Integer) redisTemplate.opsForValue().get(recipeUserStatus);
        int resultLikesCount = (Integer) redisTemplate.opsForValue().get(recipeLikes);

        UserLikeClickVO userLikeClickVO = UserLikeClickVO.builder()
                .status(resultStatus)
                .likesCount(resultLikesCount)
                .build();

        return userLikeClickVO;
    }

    /**
     * 用户点击收藏按钮
     * @param userMarkDTO
     * @return
     */
    @Override
    public UserMarkClickVO processMarkClick(UserMarkClickDTO userMarkDTO) {

        int recipeId = userMarkDTO.getRecipeId();
        int userId = BaseContext.getCurrentUserId();

        Mark mark = markMapper.findMarkByRecipeIdAndUserId(recipeId, userId);
        boolean markIsNull = false;

        if (mark == null) {
            markIsNull = true;
            mark = new Mark(-1, userId, recipeId, 1);
            markMapper.insertMark(mark);
            recipeMapper.addOneMarks(recipeId);
        }
        else {
            markMapper.revertMarkStatus(mark);
            if (mark.getStatus() == 1) {
                recipeMapper.subOneMarks(recipeId);
            }
            else {
                recipeMapper.addOneMarks(recipeId);
            }
        }

        int resultStatus = mark.getStatus() == 0 ? 1 : 0;
        if (markIsNull) {
            resultStatus = 1;
        }
        int resultMarksCount = recipeMapper.getMarksCount(recipeId);

        UserMarkClickVO userMarkClickVO = UserMarkClickVO.builder()
                                            .status(resultStatus)
                                            .marksCount(resultMarksCount)
                                            .build();

        return userMarkClickVO;
    }

    /**
     * 返回用户收藏夹中的菜谱数据
     * @return
     */
    @Override
    public List<Recipe> getMarkedRecipes() {
        int currentUserId = BaseContext.getCurrentUserId();
        List<Recipe> recipeList = recipeMapper.findAllRecipesMarkedByUserId(currentUserId);
        return recipeList;
    }

}
