package com.xiaomi.wusheng.course_0222.ProductStream;

import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    private List<String> tags;
    private String describe;

    public Product(int id, String name, double price, String category, List<String> tags, String describe) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.tags = tags;
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTags() {
        return tags;
    }

    public  String getDescribe(){
        return describe;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", tags=" + tags +
                ", describe=" + describe +
                '}';
    }
}
