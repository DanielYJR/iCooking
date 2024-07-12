package com.icooking.service;

import com.icooking.dto.UserLikeClickDTO;
import com.icooking.dto.UserLoginDTO;
import com.icooking.dto.UserMarkClickDTO;
import com.icooking.dto.UserRegistryDTO;
import com.icooking.entity.Recipe;
import com.icooking.entity.User;
import com.icooking.vo.UserLikeClickVO;
import com.icooking.vo.UserLoginVO;
import com.icooking.vo.UserMarkClickVO;

import java.util.List;

public interface UserService {
    /**
     * 用户注册
     * @param userRegistryDTO
     */
    void save(UserRegistryDTO userRegistryDTO);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User userLogin(UserLoginDTO userLoginDTO);

    /**
     * 在“用户登录”方法调用后调用，用于生成JWT令牌
     * @param user
     * @return
     */
    UserLoginVO getTokenForUserLogin(User user);

//    void processLikeClick(UserLikeClickDTO userLikeDTO);

    /**
     * 用户点赞处理逻辑
     * @param userLikeDTO
     * @return
     */
    UserLikeClickVO processLikeClick(UserLikeClickDTO userLikeDTO);

    /**
     * 用户收藏处理逻辑
     * @param userMarkDTO
     * @return
     */
    UserMarkClickVO processMarkClick(UserMarkClickDTO userMarkDTO);

    /**
     * 用户获取收藏夹
     * @return
     */
    List<Recipe> getMarkedRecipes();
}
