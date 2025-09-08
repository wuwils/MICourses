package com.xiaomi.wusheng.course_0219.Zoo;

// 抽象类：Animal
public abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    // 吃饭方法
    public void eat() {
        System.out.println(name + " is eating.");
    }

    // 抽象方法：叫声
    public abstract void bark();
}