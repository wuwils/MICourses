package com.xiaomi.wusheng.course_0219.Zoo;

// 子类：Turtle
public class Turtle extends Animal {
    public Turtle(String name) {
        super(name);
    }

    @Override
    public void bark() {
        System.out.println(name + " is barking: Hiss hiss!");
    }
}
