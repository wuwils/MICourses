package com.xiaomi.wusheng.work_0222.question2_2;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long currentTime = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTimeFormatted = LocalDateTime.now().format(formatter);
        System.out.println("当前时间: " + currentTimeFormatted);

        List<UserComment> comments = Arrays.asList(
                new UserComment("小明", "物超所值", currentTime - 2 * 60 * 1000, "北京"),
                new UserComment("小红", "包装精美", currentTime - 55 * 60 * 1000, "上海"),
                new UserComment("小刚", "送货很快", currentTime - 5 * 3660 * 1000, "广东"),
                new UserComment("小美", "客服专业", currentTime - 15 * 3600 * 1000, "浙江"),
                new UserComment("小强", "有点色差", currentTime - 35 * 3600 * 1000, "江苏"),
                new UserComment("小丑", "强烈推荐", currentTime - 55 * 3600 * 1000, "四川"),
                new UserComment("小夫", "收藏品级", currentTime - 555 * 24 * 3600 * 1000L, "陕西")
        );

        System.out.println("用户评论列表：");
        comments.forEach(comment -> System.out.println(comment.getComment()));
    }
}