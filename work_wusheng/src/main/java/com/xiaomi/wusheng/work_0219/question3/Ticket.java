package com.xiaomi.wusheng.work_0219.question3;

public abstract class Ticket {
    protected String id;
    protected String name;
    protected double price;
    protected String date;

    public Ticket(String id, String name, double price, String date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public abstract void displayDetails();
}