package com.xiaomi.wusheng.work_0222.question2_2;

public class UserComment {
    private final String name;
    private final String content;
    private final long time;
    private final String ip;

    public UserComment(String name, String content, long time, String ip) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.ip = ip;
    }

    public String getDateTime() {
        return DateConverter.getDate(time);
    }

    public String getComment() {
        return String.format("%-4s %-8s  %-10s  %-4s", name, content, getDateTime(), ip);
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public long getTime(){
        return time;
    }

    public String getIp(){
        return ip;
    }
}
