package com.xiaomi.wusheng.work_0221.question2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// 测试类
public class LoggerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 选择日志级别
        System.out.println("1. DEBUG");
        System.out.println("2. INFO");
        System.out.println("3. WARN");
        System.out.println("4. ERROR");
        System.out.print("请选择日志级别，输入对应数字: ");
        int levelChoice = scanner.nextInt();
        scanner.nextLine();

        LogLevel level = LogLevel.DEBUG;
        switch (levelChoice) {
            case 1:
                level = LogLevel.DEBUG;
                break;
            case 2:
                level = LogLevel.INFO;
                break;
            case 3:
                level = LogLevel.WARN;
                break;
            case 4:
                level = LogLevel.ERROR;
                break;
            default:
                System.out.println("无效选择，默认使用 DEBUG 级别。");
        }

        // 选择日志输出方式
        System.out.println("\n1. 控制台");
        System.out.println("2. 文件");
        System.out.println("3. 数据库");
        System.out.print("请选择日志输出方式，输入对应数字: ");
        int outputChoice = scanner.nextInt();
        scanner.nextLine();

        Logger logger = null;
        switch (outputChoice) {
            case 1:
                logger = new ConsoleLogger(level);
                break;
            case 2:
                System.out.print("请输入文件路径: "); // src/main/resources/work_0221/question2/logFile.txt
                String filePath = scanner.nextLine();
                logger = new FileLogger(filePath);
                break;
            case 3:
                logger = new DatabaseLogger();
                break;
            default:
                System.out.println("无效选择，默认使用控制台输出日志。");
                logger = new ConsoleLogger(level);
        }

        // 选择日志装饰器
        System.out.println("\n1. 添加时间戳");
        System.out.println("2. 转换为大写");
        System.out.println("3. 过滤敏感信息");
        System.out.print("请选择日志装饰器（可多选，用逗号分隔）: ");
        String decoratorChoices = scanner.nextLine();

        // 动态组合装饰器
        for (String choice : decoratorChoices.split(",")) {
            switch (choice.trim()) {
                case "1":
                    logger = new TimestampLogger(logger);
                    break;
                case "2":
                    logger = new UpperCaseLogger(logger);
                    break;
                case "3":
                    logger = new FilterLogger(logger);
                    break;
                default:
                    System.out.println("无效选择: " + choice);
            }
        }

        // 记录日志
        LogSubject logSubject = new LogSubject();
        logSubject.addObserver(new ConsoleLogObserver(level));
        logSubject.addObserver(new FileLogObserver("src/main/resources/work_0221/question2/logFile.txt"));

        /**
         * 测试日志输入：
         *
         * 1、"用户登录:
         *    username=admin,
         *    password=123456,
         *    phone=13800138000"
         *
         * 2、"用户注册:
         *    email=test@example.com,
         *    pwd=abcdef"
         **/
        System.out.print("\n请输入日志消息（输入 'exit' 结束）: ");
        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            logSubject.notifyObservers(level, message);
            logger.log(message); // 使用装饰器处理日志
            System.out.print("请输入下一条日志消息（输入 'exit' 结束）: ");
        }
        scanner.close();
    }
}