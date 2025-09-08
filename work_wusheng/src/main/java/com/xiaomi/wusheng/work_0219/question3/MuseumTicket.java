package com.xiaomi.wusheng.work_0219.question3;

public class MuseumTicket extends Ticket {
    private String specialFeature;

    public MuseumTicket(String id, String name, double price, String date, String specialFeature) {
        super(id, name, price, date);
        this.specialFeature = specialFeature;
    }

    @Override
    public void displayDetails() {
        System.out.println("博物馆门票详情：");
        System.out.println("编号：" + id);
        System.out.println("名称：" + name);
        System.out.println("价格：" + price);
        System.out.println("日期：" + date);
        System.out.println("特色：" + specialFeature + "\n");
    }
}