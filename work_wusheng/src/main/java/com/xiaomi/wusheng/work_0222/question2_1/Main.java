package com.xiaomi.wusheng.work_0222.question2_1;

import java.util.*;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args){
        List<Student>  studentList = Arrays.asList(
                new Student("Zhang San", 17, 75.5),
                new Student("Li Si", 18, 85.0),
                new Student("Wang Wu", 19, 95.5),
                new Student("Xi Yangyang", 21, 99.5),
                new Student("Mei Yangyang", 20, 80.0),
                new Student("Hui Tailang", 35, 90.0)
        );
        System.out.println("全部学生:");
        studentList.forEach(System.out::println);

        List<Student> filteredList = studentList.stream()
                .filter(student -> student.getAge() > 18 && student.getScore() > 80)
                .collect(Collectors.toList());
        System.out.println("\n筛选学生:");
        filteredList.forEach(System.out::println);

        Map<String, Double> studentMap = filteredList.stream()
                .collect(Collectors.toMap(Student::getName, Student::getScore));
        System.out.println("\n姓名成绩:");
        studentMap.forEach((name, score) -> System.out.println(name + ": " + score));

        double averageScore = filteredList.stream()
                .mapToDouble(Student::getScore)
                .average()
                .orElse(0.0);

        System.out.println("\n平均成绩: " + averageScore);
    }
}
