package com.xiaomi.wusheng.work_0219.question3;

public class ConcertTicket extends Ticket {
    private String singer;
    private String seatNumber;

    public ConcertTicket(String id, String name, double price, String date, String singer, String seatNumber) {
        super(id, name, price, date);
        this.singer = singer;
        this.seatNumber = seatNumber;
    }

    @Override
    public void displayDetails() {
        System.out.println("演唱会门票详情：");
        System.out.println("编号：" + id);
        System.out.println("名称：" + name);
        System.out.println("价格：" + price);
        System.out.println("日期：" + date);
        System.out.println("歌手：" + singer);
        System.out.println("座位号：" + seatNumber + "\n");
    }
}