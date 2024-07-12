package com.icooking.controller;

import com.icooking.entity.Category1;
import com.icooking.entity.Category2;
import com.icooking.entity.Category3;
import com.icooking.entity.Recipe;
import com.icooking.mapper.Category1Mapper;
import com.icooking.mapper.Category2Mapper;
import com.icooking.mapper.Category3Mapper;
import com.icooking.mapper.RecipeMapper;
import com.icooking.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类功能的请求管理
 */
@RestController
@CrossOrigin
@Slf4j
public class CategoryController {

    @Autowired
    private Category1Mapper category1Mapper;
    @Autowired
    private Category2Mapper category2Mapper;
    @Autowired
    private Category3Mapper category3Mapper;
    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 查询所有一级分类
     * 返回给前端所有一级分类的编号与名称
     * @return
     */
    @GetMapping("/category1st")
    public Result<List> getAllCategory1() {
        log.info("进入分类页面 获取所有1级分类");
        List<Category1> category1List = category1Mapper.findAllCategories1st();
        return Result.success(category1List);
    }

    /**
     * 查询某一级分类下的二级分类
     * 返回给前端：id=cat1_id的一级分类下所有二级分类的编号与名称（有冗余，cat1_id为冗余）
     * @param cat1_id
     * @return
     */
    @GetMapping("/category2nd")
    public Result<List> getAllCategory2ByCat1Id(int cat1_id) {
        log.info("获取1级分类id为cat1_id下的2级分类");
        List<Category2> category2List = category2Mapper.findAllCategoriesByCat1Id(cat1_id);
        return Result.success(category2List);
    }

    /**
     * 查询某二级分类下的三级分类
     * 返回给前端：id=cat2_id的二级分类下所有三级分类的编号与名称（有冗余，cat2_id为冗余）
     * @param cat2_id
     * @return
     */
    @GetMapping("/category3rd")
    public Result<List> getAllCategory3ByCat2Id(int cat2_id) {
        log.info("获取2级分类id为cat2_id下的3级分类");
        List<Category3> category3List = category3Mapper.findAllCategoriesByCat2Id(cat2_id);
        return Result.success(category3List);
    }

    /**
     * 查询某三级分类下所有菜谱
     * 返回id=cat3_id的三级分类下所有菜谱的信息（不包括步骤)
     * @param cat3_id
     * @return
     */
    @GetMapping("/recipes")
    public Result<List> getAllRecipesByCat3Id(int cat3_id) {
        log.info("获取3级分类id为cat3_id下的所有菜谱");
        List<Recipe> recipeList = recipeMapper.findRecipesByCategory(cat3_id);
        return Result.success(recipeList);
    }

    /**
     * 你好口牙
     * @return
     */
    @GetMapping("/hello")
    public Result hello() {
        return Result.success();
    }
}
