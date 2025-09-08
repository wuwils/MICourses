package com.xiaomi.wusheng.course_0227.SpringWebMVC;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // 创建用户
    @PostMapping
    public String createUser(@RequestBody User user) {
        users.add(user);
        return "User created successfully!";
    }

    // 更新用户
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .ifPresent(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                });
        return "User updated successfully!";
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
        return "User deleted successfully!";
    }
}
