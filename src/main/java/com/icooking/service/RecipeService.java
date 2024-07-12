package com.icooking.service;

import com.icooking.entity.Recipe;

import java.util.List;

public interface RecipeService {

    /**
     * 查询菜谱详情信息（包括具体步骤）
     * @param recipeId
     * @return
     */
    Recipe acquireRecipeDetails(int recipeId);

    /**
     * 获得点赞数与收藏数和前二十的菜谱
     * @return
     */
    List<Recipe> findTop20Recipes();
}
