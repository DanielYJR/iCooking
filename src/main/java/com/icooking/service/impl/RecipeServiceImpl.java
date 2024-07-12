package com.icooking.service.impl;

import com.icooking.entity.Recipe;
import com.icooking.mapper.RecipeMapper;
import com.icooking.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 查询菜谱详情信息（包括具体步骤）
     * @param recipeId
     * @return
     */
    @Override
    public Recipe acquireRecipeDetails(int recipeId) {

        // 将recipeId对应的菜谱点赞数同步到数据库中
        String recipeLikes = "recipe:" + recipeId + ":LikesCount";
        Integer likesCount = (Integer) redisTemplate.opsForValue().get(recipeLikes);
        if (likesCount != null) {
            recipeMapper.updateLikesCount(recipeId, likesCount);
        }

        Recipe recipe = recipeMapper.acquireRecipeDetailsById(recipeId);
        return recipe;
    }

    /**
     * 获得点赞数与收藏数和前二十的菜谱
     * @return
     */
    @Override
    public List<Recipe> findTop20Recipes() {
        List<Recipe> recipeList = recipeMapper.findTop20RecipesByLikesAndMarks();

        for (Recipe recipe : recipeList) {
            int recipeId = recipe.getId();
            // 将recipeId对应的菜谱点赞数同步到数据库中
            String recipeLikes = "recipe:" + recipeId + ":LikesCount";
            Integer likesCount = (Integer) redisTemplate.opsForValue().get(recipeLikes);
            if (likesCount != null) {
                recipeMapper.updateLikesCount(recipeId, likesCount);
            }
        }

        recipeList = recipeMapper.findTop20RecipesByLikesAndMarks();
        return recipeList;
    }

}
