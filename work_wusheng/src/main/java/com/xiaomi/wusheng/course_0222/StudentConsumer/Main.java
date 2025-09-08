package com.xiaomi.wusheng.course_0222.StudentConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        // 创建学生列表
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20, Gender.FEMALE, 85.5));
        students.add(new Student("Bob", 17, Gender.MALE, 45.0));
        students.add(new Student("Charlie", 19, Gender.MALE, 75.0));
        students.add(new Student("Diana", 22, Gender.FEMALE, 55.0));
        students.add(new Student("Eva", 16, Gender.FEMALE, 90.0));

        // 1. 遍历学生列表，打印每个学生的信息
        Consumer<Student> printStudent = student -> System.out.println(student);
        System.out.println("打印学生信息:");
        operateOnStudents(students, printStudent);

        // 2. 遍历学生列表，为每个学生的成绩加上5分
        Consumer<Student> addScore = student -> student.setScore(student.getScore() + 5);
        System.out.println("为每个学生的成绩加上5分:");
        operateOnStudents(students, addScore.andThen(printStudent)); // 加分后打印

        // 3. 遍历学生列表，将每个学生的年龄加1
        Consumer<Student> incrementAge = student -> student.setAge(student.getAge() + 1);
        System.out.println("将每个学生的年龄加1:");
        operateOnStudents(students, incrementAge.andThen(printStudent)); // 年龄加1后打印

        // 4. 使用Consumer的组合操作（andThen）
        // 4.1 先为每个学生的成绩加上5分，然后打印每个学生的信息
        Consumer<Student> addScoreAndPrint = addScore.andThen(printStudent);
        System.out.println("先加分，再打印学生信息:");
        operateOnStudents(students, addScoreAndPrint);

        // 4.2 先将每个学生的年龄加1，然后打印每个学生的信息
        Consumer<Student> incrementAgeAndPrint = incrementAge.andThen(printStudent);
        System.out.println("先年龄加1，再打印学生信息:");
        operateOnStudents(students, incrementAgeAndPrint);
    }

    // 辅助方法：使用Consumer对学生列表进行操作
    private static void operateOnStudents(List<Student> students, Consumer<Student> consumer) {
        for (Student student : students) {
            consumer.accept(student);
        }
        System.out.println();
    }
}
