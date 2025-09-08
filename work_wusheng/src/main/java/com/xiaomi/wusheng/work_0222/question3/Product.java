package com.xiaomi.wusheng.work_0222.question3;

class Product{
    private String productName;
    private double price;
    private int quantity;

    public Product(String productName, double price, int quantity){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName(){
        return productName;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    @Override
    public String toString(){
        return "productName = " + productName + ", price = " + price + ", quantity = " + quantity;
    }
}
