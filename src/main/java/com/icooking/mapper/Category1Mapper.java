package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Category1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category1Mapper extends BaseMapper<Category1> {

    @Select("Select * from t_category1st")
    List<Category1> findAllCategories1st();
}
