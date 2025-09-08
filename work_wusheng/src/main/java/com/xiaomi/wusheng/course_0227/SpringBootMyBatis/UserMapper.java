package com.xiaomi.wusheng.course_0227.SpringBootMyBatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 查询所有用户
    @Select("SELECT * FROM users")
    List<User> findAll();

    // 根据ID查询用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    // 插入用户
    @Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    // 更新用户
    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    void update(User user);

    // 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteById(Long id);
}
