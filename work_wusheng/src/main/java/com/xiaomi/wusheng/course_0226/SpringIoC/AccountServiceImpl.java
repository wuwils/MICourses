package com.xiaomi.wusheng.course_0226.SpringIoC;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("accountService")
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;
//    private List<String> transactionLogs;
//
//    private String name;
//    private Integer age;
//    private Date birthday;

//    private IAccountDao accountDao = new AccountDaoImpl();

    @Resource(name = "accountDao")
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

//    // 构造函数注入
//    public AccountServiceImpl(String name, Integer age, Date birthday) {
//        this.name = name;
//        this.age = age;
//        this.birthday = birthday;
//    }

//    // Setter注入
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public void setAge(Integer age){
//        this.age = age;
//    }
//
//    public void setBirthday(Date birthday){
//        this.birthday = birthday;
//    }

//    public void setTransactionLogs(List<String> transactionLogs) {
//        this.transactionLogs = transactionLogs;
//    }

    @Override
    public void saveAccount(int money){
        accountDao.saveAccount(money);
//        System.out.println("name: " + name + ", age: " + age + ", birthday: " + birthday);
//        if (transactionLogs != null) {
//            for (String log : transactionLogs) {
//                System.out.println("Transaction Log: " + log);
//            }
//        }
    }
}