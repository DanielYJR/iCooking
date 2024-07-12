package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Mark;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MarkMapper extends BaseMapper<Mark> {

    @Select("select * from t_marks where recipe_id = #{recipeId} and user_id = #{userId}")
    Mark findMarkByRecipeIdAndUserId(int recipeId, int userId);

    @Insert("insert into t_marks (recipe_id, user_id, status) values (#{recipeId}, #{userId}, #{status})")
    void insertMark(Mark mark);

    @Update("UPDATE t_marks\n" +
            "SET status = CASE\n" +
            "    WHEN status = 1 THEN 0\n" +
            "    WHEN status = 0 THEN 1\n" +
            "    ELSE status\n" +
            "END\n" +
            "WHERE recipe_id = #{recipeId} AND user_id = #{userId};")
    void revertMarkStatus(Mark mark);

    @Delete("delete from t_marks where recipe_id = #{recipeId} and user_id = #{userId}, #{status}")
    void deleteMark(Mark mark);
}