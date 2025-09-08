package com.xiaomi.wusheng.work_0227.repository;

import com.xiaomi.wusheng.work_0227.model.Comment;
import com.xiaomi.wusheng.work_0227.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItem(Item item);
}