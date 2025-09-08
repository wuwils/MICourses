package com.xiaomi.wusheng.work_0222.question3;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class Order{
    private String orderId;
    private String customerName;
    private LocalDateTime orderDateTime;
    private List<Product> products;

    public Order(String orderId, String customerName, LocalDateTime orderDateTime, List<Product> products){
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDateTime = orderDateTime;
        this.products = products;
    }

    public String getOrderId(){
        return orderId;
    }

    public String getCustomerName(){
        return customerName;
    }

    public LocalDate getOrderDate(){
        return orderDateTime.toLocalDate();
    }

    public List<Product> getProducts(){
        return products;
    }

    @Override
    public String toString(){
        String Products = products.stream()
                .map(product -> "                  " + product.toString())
                .collect(Collectors.joining("\n", "\n", ""));
        return "Order:" +
                "\n       orderId = " + orderId +
                "\n       customerName = " + customerName +
                "\n       orderDate = " + orderDateTime +
                "\n       products : " + Products;
    }
}
