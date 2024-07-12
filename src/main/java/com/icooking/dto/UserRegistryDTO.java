package com.icooking.dto;

import lombok.Data;

import java.sql.Date;

/*
    注册时前端传递注册信息的数据传输对象
 */

@Data
public class UserRegistryDTO {

    private int id;
    private String username;
    private String password;
//    private int isAdmin;
    private Date birthday;

}
