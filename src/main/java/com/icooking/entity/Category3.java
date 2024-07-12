package com.icooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_category3rd")
public class Category3 {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("cat2_id")
    private Integer cat2Id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCat2Id() {
        return cat2Id;
    }

    public void setCat2Id(Integer cat2Id) {
        this.cat2Id = cat2Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
