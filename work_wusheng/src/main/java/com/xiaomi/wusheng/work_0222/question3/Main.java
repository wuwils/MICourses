package com.xiaomi.wusheng.work_0222.question3;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Order> orderList = getOrders();
        System.out.println("全部订单:");
        orderList.forEach(System.out::println);

        List<Order> filteredList = orderList.stream()
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2025, 1, 1))
                        && order.getOrderDate().isBefore(LocalDate.of(2025, 1, 29)))
                .filter(order -> order.getCustomerName().contains("张"))
                .collect(Collectors.toList());
        System.out.println("\n筛选订单:");
        filteredList.forEach(System.out::println);

        Map<String, Double> CostMap = filteredList.stream()
                .collect(Collectors.toMap(Order::getOrderId, Main::getCost));
        System.out.println("\n订单编号和总金额:");
        CostMap.forEach((orderId, cost) ->
                System.out.println("订单编号: " + orderId + ", 总金额: " + cost)
        );

        Optional<Order> highedCost = filteredList.stream()
                .max(Comparator.comparingDouble(Main::getCost));
        if (highedCost.isPresent()) {
            System.out.println("\n总金额最高的订单: " + highedCost.get());
        } else {
            System.out.println("\n不存在总金额最高的订单！");
        }

        List<Order> sortedList = orderList.stream()
                .sorted(Comparator.comparing(Order::getOrderDate)
                        .thenComparing(Main::getCost, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        System.out.println("\n排序后的订单:");
        sortedList.forEach(System.out::println);
    }

    private static List<Order> getOrders() {
        Product product1 = new Product("水杯", 60.00, 1);
        Product product2 = new Product("雨伞", 40.00, 2);
        Product product3 = new Product("本子", 10.00, 5);
        Product product4 = new Product("水笔", 5.00, 3);
        Product product5 = new Product("书包", 200.00, 1);

        List<Order> orderList = Arrays.asList(
                new Order("1", "张四", LocalDateTime.of(2024, 12, 25, 5, 5),
                        Arrays.asList(product1, product4)),
                new Order("2", "张三", LocalDateTime.of(2025, 1, 5, 5, 5),
                        Arrays.asList(product2, product5)),
                new Order("3", "老张", LocalDateTime.of(2025, 1, 15, 5, 5),
                        Arrays.asList(product1, product2, product3)),
                new Order("4", "老八", LocalDateTime.of(2025, 1, 15, 5, 5),
                        Arrays.asList(product3, product4, product5)),
                new Order("5", "老六", LocalDateTime.of(2025, 1, 25, 5, 5),
                        Arrays.asList(product2, product3, product4))
        );
        return orderList;
    }

    private static double getCost(Order order){
        return order.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }
}
