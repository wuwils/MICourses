package com.xiaomi.user.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class User {
    private Long userId;

    @NotBlank(message = "昵称不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public User() {}

    public User(String userName, String password, String email, Byte status) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    // Getter 和 Setter 方法

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{userId=" + userId + ", userName='" + userName + "', email='" + email + "'}";
    }

}
