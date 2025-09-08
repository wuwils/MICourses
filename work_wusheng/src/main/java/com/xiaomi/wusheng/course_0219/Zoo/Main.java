package com.xiaomi.wusheng.course_0219.Zoo;

import java.util.ArrayList;
import java.util.List;

// 动物园类
public class Main {
    private List<Animal> animals;

    public Main() {
        animals = new ArrayList<>();
    }

    // 添加动物
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // 展示所有动物的行为
    public void showAnimals() {
        for (Animal animal : animals) {
            animal.eat();
            animal.bark();
            System.out.println(); // 换行
        }
    }

    public static void main(String[] args) {
        // 创建动物园
        Main zoo = new Main();

        // 添加动物
        zoo.addAnimal(new Monkey("Monkey King"));
        zoo.addAnimal(new Marmota("Groundhog"));
        zoo.addAnimal(new Turtle("Ninja Turtle"));

        // 展示动物行为
        zoo.showAnimals();
    }
}
