package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Category2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category2Mapper extends BaseMapper<Category2>  {

    @Select("select * from t_category2nd where cat1_id = #{cat1_id}")
    List<Category2> findAllCategoriesByCat1Id(int cat1_id);
}
