package com.xiaomi.wusheng.course_0222.StudentFunction;

// 定义Student类
class Student {
    private String name;
    private int age;
    private Gender gender;
    private double score;

    // 构造函数
    public Student(String name, int age, Gender gender, double score) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.score = score;
    }

    // Getter方法
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public double getScore() {
        return score;
    }

    // Setter方法
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", score=" + score +
                '}';
    }
}
