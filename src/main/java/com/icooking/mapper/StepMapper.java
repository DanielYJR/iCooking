package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Step;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StepMapper extends BaseMapper<Step> {

    @Select("select * from recipe_steps where recipe_id = #{recipeId} order by seq")
    List<Step> findStepByRecipeId(int recipeId);

}
