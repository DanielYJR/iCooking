package com.icooking.service.impl;

import com.icooking.entity.Recipe;
import com.icooking.exception.SearchException;
import com.icooking.mapper.RecipeMapper;
import com.icooking.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 返回模糊查询结果，且在查询之前对搜索词进行安全检测
     * 参数化查询，且简单地防止sql注入
     * @param word
     * @return
     */
    @Override
    public List<Recipe> findAllRecipesByWord(String word) {

        // 如果搜索词中含有DROP TABLE、DROP DATABASE或数字符号，则抛出异常，并给出警告信息
        if (word != null && (word.toUpperCase().contains("DROP TABLE") ||
                word.toUpperCase().contains("DROP DATABASE") ||
                word.matches(".*[0-9+\\-*/=()].*"))) {
            throw new SearchException("搜索词存在非法内容");
        }

        try {
            List<Recipe> recipeList = recipeMapper.findAllRecipesByWord(word);
            return recipeList;
        }
        catch (SearchException e) {
            throw new SearchException("搜索词存在非法内容");
        }
    }
}
