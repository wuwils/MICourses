package com.xiaomi.wusheng.work_0225.question_2;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        // 测试用户105借出BBBBB，归还EEEEE
        while(true){
            System.out.println("\n请选择操作:");
            System.out.println("1. 借书");
            System.out.println("2. 还书");
            System.out.println("3. 退出");
            System.out.print("输入选择: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                System.out.print("\n请输入借书书名: ");
                String bookTitle = scanner.nextLine();
                BorrowBook.borrowBook(bookTitle, userId);
            }else if(choice == 2){
                System.out.print("\n请输入还书书名: ");
                String bookTitle = scanner.nextLine();
                ReturnBook.returnBook(bookTitle, userId);
            }else if(choice == 3){
                System.out.println("\n退出系统...");
                break;
            }else{
                System.out.println("\n请重新选择！");
            }
            System.out.println();
        }
        scanner.close();
    }
}

