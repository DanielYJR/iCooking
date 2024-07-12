package com.icooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_belong")
public class Belong {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("cat3_id")
    private Integer cat3Id;
    @TableField("recipe_id")
    private Integer recipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(Integer cat3Id) {
        this.cat3Id = cat3Id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
}
