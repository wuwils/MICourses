package com.xiaomi.wusheng.course_0222.StudentFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        // 创建学生列表
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20, Gender.FEMALE, 85.5));
        students.add(new Student("Bob", 17, Gender.MALE, 45.0));
        students.add(new Student("Charlie", 19, Gender.MALE, 75.0));
        students.add(new Student("Diana", 22, Gender.FEMALE, 55.0));
        students.add(new Student("Eva", 16, Gender.FEMALE, 90.0));

        // 1. 将学生对象转换为一个包含其姓名和年龄的字符串
        Function<Student, String> nameAndAgeToString = student ->
                "name: " + student.getName() + ", age: " + student.getAge();
        System.out.println("学生姓名和年龄的字符串:");
        transformAndPrint(students, nameAndAgeToString);

        // 2. 将学生对象转换为其成绩的整数部分
        Function<Student, Integer> scoreToInt = student -> (int) student.getScore();
        System.out.println("学生成绩的整数部分:");
        transformAndPrint(students, scoreToInt);

        // 3. 将学生对象的成绩乘以一个系数（例如，乘以 1.1）
        Function<Student, Double> multiplyScore = student -> student.getScore() * 1.1;
        System.out.println("学生成绩乘以系数 1.1 后的结果:");
        transformAndPrint(students, multiplyScore);

        // 4. 使用 Function 的组合操作（andThen 和 compose）
        // 4.1 先将学生对象的成绩乘以系数，然后转换为整数部分
        Function<Student, Integer> multiplyAndConvertToInt = multiplyScore.andThen(Double::intValue);
        System.out.println("学生成绩乘以系数 1.1 后转换为整数部分:");
        transformAndPrint(students, multiplyAndConvertToInt);

        // 4.2 先将学生对象转换为姓名和年龄的字符串，然后将字符串全部转换为大写
        Function<Student, String> nameAndAgeToUppercase = nameAndAgeToString.andThen(String::toUpperCase);
        System.out.println("学生姓名和年龄的字符串转换为大写:");
        transformAndPrint(students, nameAndAgeToUppercase);
    }

    // 辅助方法：使用Function对学生列表进行转换并打印结果
    private static <T> void transformAndPrint(List<Student> students, Function<Student, T> function) {
        for (Student student : students) {
            T result = function.apply(student);
            System.out.println(result);
        }
        System.out.println(); // 打印空行分隔不同转换结果
    }
}
