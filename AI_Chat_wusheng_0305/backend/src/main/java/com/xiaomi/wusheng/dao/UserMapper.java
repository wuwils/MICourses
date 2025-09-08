package com.xiaomi.wusheng.dao;

import com.xiaomi.wusheng.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(userName, password, email, status) VALUES(#{userName}, #{password}, #{email}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Select("SELECT * FROM users WHERE userName = #{userName}")
    User selectByUserName(String userName);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User selectByEmail(String email);

    // 登录时使用邮箱查询
    @Select("SELECT * FROM users WHERE email = #{email}")
    User selectByLoginEmail(String email);

    // 更新用户登录时间
    @Update("UPDATE users SET updateTime = CURRENT_TIMESTAMP WHERE userId = #{userId}")
    int updateLoginTime(Long userId);
}
