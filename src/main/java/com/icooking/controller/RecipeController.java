package com.icooking.controller;

import com.icooking.entity.Recipe;
import com.icooking.mapper.RecipeMapper;
import com.icooking.result.Result;
import com.icooking.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 请求格式: localhost:8088/recipeDetail?recipeId=1
     * 查询菜谱详细内容
     * @param recipeId
     * @return
     */
    @GetMapping("/recipeDetail")
    public Result<Recipe> getRecipeDetail(int recipeId) {
        log.info("进入{}号菜谱详情页",recipeId);
        Recipe recipe = recipeService.acquireRecipeDetails(recipeId);
        return Result.success(recipe);
    }

    /**
     * 查询排行榜（Top20）
     * @return
     */
    @GetMapping("/Top20Recipes")
    public Result getTop20Recipes() {
        List<Recipe> recipeList = recipeService.findTop20Recipes();
        return Result.success(recipeList);
    }

}
