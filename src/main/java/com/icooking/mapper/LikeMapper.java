package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Like;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper extends BaseMapper<Like> {

    @Select("select * from t_likes where recipe_id = #{recipeId} and user_id = #{userId}")
    Like findLikeByRecipeIdAndUserId(int recipeId, int userId);

    @Insert("insert into t_likes (recipe_id, user_id, status) values (#{recipeId}, #{userId}, #{status})")
    void insertLike(Like like);

    @Update("UPDATE t_likes\n" +
            "SET status = CASE\n" +
            "    WHEN status = 1 THEN 0\n" +
            "    WHEN status = 0 THEN 1\n" +
            "    ELSE status\n" +
            "END\n" +
            "WHERE recipe_id = #{recipeId} AND user_id = #{userId};")
    void revertLikeStatus(Like like);

    @Delete("delete from t_likes where recipe_id = #{recipeId} and user_id = #{userId}, #{status}")
    void deleteLike(Like like);
}
