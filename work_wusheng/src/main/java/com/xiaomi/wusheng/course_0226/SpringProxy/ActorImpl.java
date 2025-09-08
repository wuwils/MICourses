package com.xiaomi.wusheng.course_0226.SpringProxy;

public class ActorImpl implements IActor {

    @Override
    public void basicPlay(float money) {
        System.out.println("拿到钱，基本演出:" + money);
    }

    @Override
    public void dangerPlay(float money) {
        System.out.println("拿到钱，危险演出:" + money);
    }
}
