package com.xiaomi.wusheng.work_0227.controller;

import com.xiaomi.wusheng.work_0227.model.Comment;
import com.xiaomi.wusheng.work_0227.model.Item;
import com.xiaomi.wusheng.work_0227.model.User;
import com.xiaomi.wusheng.work_0227.service.CommentService;
import com.xiaomi.wusheng.work_0227.service.ItemService;
import com.xiaomi.wusheng.work_0227.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String createComment(@ModelAttribute Comment comment, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            comment.setUser(currentUser);
            commentService.saveComment(comment);
            redirectAttributes.addFlashAttribute("success", "评论添加成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "添加评论时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
        }
        return "redirect:/items/" + comment.getItem().getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            Comment comment = commentService.findById(id);
            if (comment != null && comment.getUser() != null && comment.getUser().getId().equals(currentUser.getId())) {
                Long itemId = comment.getItem().getId();
                commentService.deleteComment(id);
                redirectAttributes.addFlashAttribute("success", "评论删除成功");
                return "redirect:/items/" + itemId;
            } else {
                redirectAttributes.addFlashAttribute("error", "你没有权限删除此评论");
                return "redirect:/items/" + comment.getItem().getId();
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除评论时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    @GetMapping("/{id}/edit")
    public String editCommentForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            Comment comment = commentService.findById(id);
            if (comment != null && comment.getUser() != null && comment.getUser().getId().equals(currentUser.getId())) {
                model.addAttribute("comment", comment);
                model.addAttribute("itemId", comment.getItem().getId());
                return "comment-form";
            } else {
                redirectAttributes.addFlashAttribute("error", "你没有权限编辑此评论");
                return "redirect:/items/" + comment.getItem().getId();
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "编辑评论时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    @PostMapping("/{id}/update")
    public String updateComment(@PathVariable Long id, @ModelAttribute Comment comment, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            Comment existingComment = commentService.findById(id);
            if (existingComment != null && existingComment.getUser() != null && existingComment.getUser().getId().equals(currentUser.getId())) {
                existingComment.setContent(comment.getContent());
                commentService.updateComment(existingComment);
                redirectAttributes.addFlashAttribute("success", "评论更新成功");
                return "redirect:/items/" + existingComment.getItem().getId();
            } else {
                redirectAttributes.addFlashAttribute("error", "你没有权限更新此评论");
                return "redirect:/items/" + existingComment.getItem().getId();
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新评论时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    @GetMapping("/item/{itemId}")
    public String listCommentsByItem(@PathVariable Long itemId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Item item = itemService.findById(itemId);
            if (item != null) {
                model.addAttribute("item", item);
                model.addAttribute("comments", commentService.findByItem(item));
                return "item-detail";
            } else {
                redirectAttributes.addFlashAttribute("error", "物品不存在");
                return "redirect:/items";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "查看评论时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUsername(username);
        }
        return null;
    }
}
