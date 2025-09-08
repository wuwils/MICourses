package com.xiaomi.wusheng.course_0222.StudetPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        // 创建学生列表
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20, Gender.FEMALE, 85.5));
        students.add(new Student("Bob", 17, Gender.MALE, 45.0));
        students.add(new Student("Charlie", 19, Gender.MALE, 75.0));
        students.add(new Student("Diana", 22, Gender.FEMALE, 55.0));
        students.add(new Student("Eva", 16, Gender.FEMALE, 90.0));

        // 1. 筛选出年龄大于等于18岁的学生
        Predicate<Student> agePredicate = student -> student.getAge() >= 18;
        System.out.println("年龄大于等于18岁的学生:");
        filterAndPrint(students, agePredicate);

        // 2. 筛选出成绩大于等于60分的学生
        Predicate<Student> scorePredicate = student -> student.getScore() >= 60;
        System.out.println("成绩大于等于60分的学生:");
        filterAndPrint(students, scorePredicate);

        // 3. 筛选出性别为女性（FEMALE）的学生
        Predicate<Student> genderPredicate = student -> student.getGender() == Gender.FEMALE;
        System.out.println("性别为女性的学生:");
        filterAndPrint(students, genderPredicate);

        // 4. 使用Predicate的组合操作
        // 4.1 筛选出年龄大于等于18岁且成绩大于等于60分的学生
        Predicate<Student> ageAndScorePredicate = agePredicate.and(scorePredicate);
        System.out.println("年龄大于等于18岁且成绩大于等于60分的学生:");
        filterAndPrint(students, ageAndScorePredicate);

        // 4.2 筛选出年龄小于18岁或性别为女性的学生
        Predicate<Student> ageOrGenderPredicate = agePredicate.negate().or(genderPredicate);
        System.out.println("年龄小于18岁或性别为女性的学生:");
        filterAndPrint(students, ageOrGenderPredicate);

        // 4.3 筛选出成绩不及格（小于60分）且年龄大于等于18岁的学生
        Predicate<Student> failAndAgePredicate = scorePredicate.negate().and(agePredicate);
        System.out.println("成绩不及格且年龄大于等于18岁的学生:");
        filterAndPrint(students, failAndAgePredicate);
    }

    // 辅助方法：根据Predicate筛选并打印学生列表
    private static void filterAndPrint(List<Student> students, Predicate<Student> predicate) {
        for (Student student : students) {
            if (predicate.test(student)) {
                System.out.println(student);
            }
        }
        System.out.println();
    }
}
