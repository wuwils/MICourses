package com.xiaomi.wusheng.work_0219.question3;

public class Main {
    public static void main(String[] args) {
        // 创建门票实例
        MuseumTicket museumTicket1 = new MuseumTicket("12323", "湖北省博物院", 20.0, "2025-02-19", "越王勾践剑");
        MuseumTicket museumTicket2 = new MuseumTicket("12543", "故宫博物院", 40.0, "2025-03-01", "紫禁城");
        ConcertTicket concertTicket1 = new ConcertTicket("32121", "薛之谦演唱会", 880.0, "2025-02-21", "薛之谦", "9排9座");
        ConcertTicket concertTicket2 = new ConcertTicket("34543", "周杰伦演唱会", 1880.0, "2025-02-23", "周杰伦", "6排6座");
        ConcertTicket concertTicket3 = new ConcertTicket("34544", "林俊杰演唱会", 1280.0, "2025-03-05", "林俊杰", "12排12座");
        ConcertTicket concertTicket4 = new ConcertTicket("34545", "五月天演唱会", 580.0, "2025-03-10", "五月天", "15排15座");

        // 创建订单实例
        Order order1 = new Order("0033");
        order1.addProduct(museumTicket1);
        order1.addProduct(concertTicket1);

        Order order2 = new Order("0108");
        order2.addProduct(concertTicket2);

        Order order3 = new Order("0202");
        order3.addProduct(museumTicket2);
        order3.addProduct(concertTicket3);
        order3.addProduct(concertTicket4);

        Order order4 = new Order("0303");
        order4.addProduct(concertTicket1);
        order4.addProduct(concertTicket4);

        // 创建订单记录并添加订单
        OrderRecord record = new OrderRecord();
        record.addOrder(order1);
        record.addOrder(order2);
        record.addOrder(order3);
        record.addOrder(order4);

        // 显示所有订单和特定订单
        record.displayOrders();

        record.displayOldOrder("0108");
        record.displayOldOrder("0202");
    }
}