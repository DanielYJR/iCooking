package com.icooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icooking.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users where username = #{username}")
    User findUserByUserName(String username);

    @Insert("insert into users (username, password, isAdmin, birthday)" +
            "values (#{username}, #{password}, #{isAdmin}, #{birthday})")
    void insertUser(User user);

    @Select("select username from users where id = #{currentUserId}")
    String getNameById(int currentUserId);
}
