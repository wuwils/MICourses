package com.xiaomi.wusheng.course_0219.Zoo;

// 子类：Marmota
public class Marmota extends Animal {
    public Marmota(String name) {
        super(name);
    }

    @Override
    public void bark() {
        System.out.println(name + " is barking: Squeak squeak!");
    }
}