package com.xiaomi.wusheng.work_0227.service;

import com.xiaomi.wusheng.work_0227.model.Comment;
import com.xiaomi.wusheng.work_0227.model.Item;
import com.xiaomi.wusheng.work_0227.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findByItem(Item item) {
        return commentRepository.findByItem(item);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
