package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.Category3;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category3Mapper extends BaseMapper<Category3> {

    @Select("select * from t_category3rd where cat2_id = #{cat2Id}")
    List<Category3> findAllCategoriesByCat2Id(int cat2Id);
}
