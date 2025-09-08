package com.xiaomi.wusheng.work_0227.repository;

import com.xiaomi.wusheng.work_0227.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
