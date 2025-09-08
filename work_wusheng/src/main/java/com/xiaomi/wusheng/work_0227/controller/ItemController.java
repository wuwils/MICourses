package com.xiaomi.wusheng.work_0227.controller;

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
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @PostMapping
    public String createItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            item.setUser(currentUser);
            itemService.saveItem(item);
            redirectAttributes.addFlashAttribute("success", "物品添加成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "添加物品时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
        }
        return "redirect:/items";
    }

    @GetMapping("/{id}")
    public String viewItem(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Item item = itemService.findById(id);
            if (item != null) {
                model.addAttribute("item", item);
                model.addAttribute("comments", commentService.findByItem(item));
                return "item-detail";
            } else {
                redirectAttributes.addFlashAttribute("error", "物品不存在");
                return "redirect:/items";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "查看物品时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    @GetMapping("/{id}/edit")
    public String editItemForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Item item = itemService.findById(id);
            if (item != null) {
                User currentUser = getCurrentUser();
                if (item.getUser() != null && item.getUser().getId().equals(currentUser.getId())) {
                    model.addAttribute("item", item);
                    return "item-form";
                } else {
                    redirectAttributes.addFlashAttribute("error", "你没有权限编辑此物品");
                    return "redirect:/items";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "物品不存在");
                return "redirect:/items";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "编辑物品时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
            return "redirect:/items";
        }
    }

    @PostMapping("/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            Item existingItem = itemService.findById(id);
            if (existingItem != null && existingItem.getUser() != null && existingItem.getUser().getId().equals(currentUser.getId())) {
                item.setId(id);
                item.setUser(existingItem.getUser()); // 确保用户信息不被覆盖
                itemService.saveItem(item);
                redirectAttributes.addFlashAttribute("success", "物品更新成功");
            } else {
                redirectAttributes.addFlashAttribute("error", "你没有权限更新此物品");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新物品时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
        }
        return "redirect:/items";
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            Item item = itemService.findById(id);
            if (item != null && item.getUser() != null && item.getUser().getId().equals(currentUser.getId())) {
                itemService.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "物品删除成功");
            } else {
                redirectAttributes.addFlashAttribute("error", "你没有权限删除此物品");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除物品时发生错误: " + e.getMessage());
            e.printStackTrace(); // 或者使用日志记录
        }
        return "redirect:/items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam String query, Model model) {
        model.addAttribute("items", itemService.searchByName(query));
        return "item-list";
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
