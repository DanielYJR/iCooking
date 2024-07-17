package com.icooking.controller;

import com.icooking.dto.UserLikeClickDTO;
import com.icooking.dto.UserLoginDTO;
import com.icooking.dto.UserMarkClickDTO;
import com.icooking.dto.UserRegistryDTO;
import com.icooking.entity.Recipe;
import com.icooking.entity.User;
import com.icooking.result.Result;
import com.icooking.service.UserService;
import com.icooking.vo.UserLikeClickVO;
import com.icooking.vo.UserLoginVO;
import com.icooking.vo.UserMarkClickVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * 将用户id、用户名与生成的令牌返回给前端
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/entry")
    public Result userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录请求：{}",userLoginDTO);

        User user = userService.userLogin(userLoginDTO);
        UserLoginVO userLoginVO = userService.getTokenForUserLogin(user);

        return Result.success(userLoginVO);
    }

    /**
     * 用户退出
     * (前端跳转到首页)
     * @return
     */
    @PostMapping("/logout")
    public Result userLogout() {
        log.info("用户退出");
        return Result.success();
    }

    /**
     * 用户注册
     * @param userRegistryDTO
     * @return
     */
    @PostMapping("/reg")
    public Result userRegistry(@RequestBody UserRegistryDTO userRegistryDTO) {
        userService.save(userRegistryDTO);
        return Result.success();
    }

    /**
     * 用户点击点赞按钮
     * @param userLikeClickDTO
     * @return
     */
    @PostMapping("/likeClick")
    public Result likeClick(@RequestBody UserLikeClickDTO userLikeClickDTO) {
        UserLikeClickVO userLikeClickVO = userService.processLikeClick(userLikeClickDTO);
        return Result.success(userLikeClickVO);
//        userService.processLikeClick(userLikeClickDTO);
//        return Result.success();
    }

    /**
     * 用户点击收藏按钮
     * @param userMarkDTO
     * @return
     */
    @PostMapping("/markClick")
    public Result markClick(@RequestBody UserMarkClickDTO userMarkDTO) {
        UserMarkClickVO userMarkClickVO = userService.processMarkClick(userMarkDTO);
        return Result.success(userMarkClickVO);
    }

    /**
     * 用户收藏夹
     * @return
     */
    @GetMapping("/favorites")
    public Result getMarksOfCurrentUser(){
        List<Recipe> recipeList = userService.getMarkedRecipes();
        return Result.success(recipeList);
    }

    @GetMapping("/currentUser")
    public Result getCurrentUserName() {
        return Result.success(userService.getCurrentUserName());
    }
}
