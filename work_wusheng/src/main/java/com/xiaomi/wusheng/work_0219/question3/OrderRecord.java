package com.xiaomi.wusheng.work_0219.question3;

import java.util.ArrayList;
import java.util.List;

public class OrderRecord {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void displayOrders() {
        for (int i = orders.size() - 1; i >= 0; i--) {
            orders.get(i).displayOrderDetails();
        }
    }

    public void displayOldOrder(String orderId) {
        System.out.println("***** 历史订单 *****");
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.displayOrderDetails();
                return;
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
    }
}