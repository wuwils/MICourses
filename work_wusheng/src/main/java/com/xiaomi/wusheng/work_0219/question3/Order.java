package com.xiaomi.wusheng.work_0219.question3;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<Ticket> products;

    public Order(String orderId) {
        this.orderId = orderId;
        this.products = new ArrayList<>();
    }

    public void addProduct(Ticket ticket) {
        products.add(ticket);
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Ticket ticket : products) {
            total += ticket.price;
        }
        return total;
    }

    public void displayOrderDetails() {
        System.out.println("===== 订单详情 =====");
        System.out.println("订单编号：" + orderId);
        System.out.println("产品列表：");
        for (Ticket ticket : products) {
            ticket.displayDetails();
        }
        System.out.printf("订单总价：%.2f元\n\n", calculateTotalPrice());
    }

    public String getOrderId() {
        return orderId;
    }
}