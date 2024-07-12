package com.icooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_category2nd")
public class Category2 {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("cat1_id")
    private Integer cat1Id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCat1Id() {
        return cat1Id;
    }

    public void setCat1Id(Integer cat1Id) {
        this.cat1Id = cat1Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
