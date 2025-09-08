package com.xiaomi.wusheng.work_0222.question2_2;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    public static String getDate(long time){
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime commentTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                zoneId
        );

        Duration duration = Duration.between(commentTime, now);
        long minutes = duration.toMinutes();
        long hours = duration.toHours();

        if (minutes < 5){
            return "刚刚";
        } else if (minutes < 60) {
            return minutes + "分钟前";
        } else if (hours < 12) {
            return hours + "小时前";
        } else if (hours < 48) {
            return Within48(commentTime, now);
        } else {
            return Beyond48(commentTime, now);
        }
    }

    private static DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter Md = DateTimeFormatter.ofPattern("M-d");
    private static DateTimeFormatter yyyyMd = DateTimeFormatter.ofPattern("yyyy-M-d");

    private static String Within48(LocalDateTime commentTime, LocalDateTime now){
        LocalDate commentDate = commentTime.toLocalDate();
        LocalDate nowDate = now.toLocalDate();

        if (commentDate.isEqual(nowDate)){
            return "今天 " + commentTime.format(HHmm);
        } else if (commentDate.toEpochDay() == nowDate.toEpochDay() - 1){
            return "昨天 " + commentTime.format(HHmm);
        }
        return commentTime.format(HHmm);
    }

    private static String Beyond48(LocalDateTime commentTime, LocalDateTime now){
        LocalDate commentDate = commentTime.toLocalDate();
        LocalDate nowDate = now.toLocalDate();

        return (commentDate.getYear() == nowDate.getYear())
                ? commentDate.format(Md)
                : commentDate.format(yyyyMd);
    }
}
