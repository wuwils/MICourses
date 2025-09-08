package com.xiaomi.wusheng.course_0226.SpringJDBC;
import org.springframework.stereotype.Component;

@Component("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Override
    public void saveAccount(int money) {
        System.out.println("保存账户");
    }
}
