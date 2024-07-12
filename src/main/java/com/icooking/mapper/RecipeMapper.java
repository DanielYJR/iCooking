package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Recipe;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {

    @Update("UPDATE recipes\n" +
            "SET likes = likes + 1\n" +
            "WHERE id = #{recipeId};")
    void addOneLikes(int recipeId);

    @Update("UPDATE recipes\n" +
            "SET likes = likes - 1\n" +
            "WHERE id = #{recipeId};")
    void subOneLikes(int recipeId);

    @Update("UPDATE recipes\n" +
            "SET marks = marks + 1\n" +
            "WHERE id = #{recipeId};")
    void addOneMarks(int recipeId);

    @Update("UPDATE recipes\n" +
            "SET marks = marks - 1\n" +
            "WHERE id = #{recipeId};")
    void subOneMarks(int recipeId);

    @Select("SELECT * FROM recipes WHERE id IN (SELECT recipe_id FROM t_belong WHERE t_belong.cat3_id = #{cat3_id})")
    List<Recipe> findRecipesByCategory(@Param("cat3_id") int cat3Id);

    @Select("select * from recipes where id = #{recipeId}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "name", property = "name"),
        @Result(column = "image_url", property = "imageUrl"),
        @Result(column = "benefits", property = "benefits"),
        @Result(column = "ingredients", property = "ingredients"),
        @Result(column = "likes", property = "likes"),
        @Result(column = "mark", property = "mark"),
        @Result(column = "id", property = "steps", javaType = List.class,
                many = @Many(select = "com.icooking.mapper.StepMapper.findStepByRecipeId"))
    })
    Recipe acquireRecipeDetailsById(int recipeId);

    @Select("select * from recipes " +
            "where id in (select recipe_id from t_marks " +
                        "where user_id = #{currentUserId} and status = 1)")
    List<Recipe> findAllRecipesMarkedByUserId(int currentUserId);

//    // 下面的写法容易被sql注入
//    @Select("select * from recipes where name like '%#{word}'%;")
//    List<Recipe> findAllRecipesByWord(String word);

    // 参数化查询
    @Select("SELECT * FROM recipes WHERE name LIKE CONCAT('%', #{word}, '%')")
    List<Recipe> findAllRecipesByWord(@Param("word") String word);

    @Select("select * from recipes order by likes + marks desc limit 20")
    List<Recipe> findTop20RecipesByLikesAndMarks();

    @Select("select likes from recipes where id = #{recipeId};")
    int getLikesCount(int recipeId);

    @Update("update recipes set likes = #{likesCount} where id = #{recipeId}")
    void updateLikesCount(int recipeId, Integer likesCount);

    @Select("select marks from recipes where id = #{recipeId};")
    int getMarksCount(int recipeId);
}