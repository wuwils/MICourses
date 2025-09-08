package com.xiaomi.wusheng.course_0221.FileSerialize;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("wusheng", 22);

        String filePath = new String("./src/main/java/com/xiaomi/wusheng/course_0221/FileSerialize/");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath + "person.ser"))) {
            oos.writeObject(person);
            System.out.println("Person 对象已序列化到 person.ser 文件");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath + "person.ser"))) {
            Person deserializedPerson = (Person) ois.readObject();
            System.out.println("Person 对象已从 person.ser 文件反序列化");

            System.out.println("反序列化后的 Person 对象: " + deserializedPerson);
            System.out.println("Name: " + deserializedPerson.getName());
            System.out.println("Age: " + deserializedPerson.getAge());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}