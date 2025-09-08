package com.xiaomi.wusheng.course_0219.Zoo;

// 子类：Monkey
public class Monkey extends Animal {
    public Monkey(String name) {
        super(name);
    }

    @Override
    public void bark() {
        System.out.println(name + " is barking: Ooh ooh ah ah!");
    }
}
