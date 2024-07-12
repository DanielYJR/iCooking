package com.icooking.controller;

import com.icooking.entity.Recipe;
import com.icooking.mapper.RecipeMapper;
import com.icooking.result.Result;
import com.icooking.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * localhost:8088/search?word=xxx
     * 模糊查询功能
     * 返回给前端查询到的菜谱
     * @param word
     * @return
     */
    @GetMapping("/search")
    public Result getAllRecipesContainsWord(String word) {
        List<Recipe> recipeList = searchService.findAllRecipesByWord(word);
        return Result.success(recipeList);
    }

}
