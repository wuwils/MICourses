package com.xiaomi.wusheng.course_0226.SpringIoC;
import org.springframework.stereotype.Component;

@Component("accountDao")
public class AccountDaoImpl implements IAccountDao{

//    // 静态工厂方法
//    public static IAccountDao createAccountDao() {
//        return new AccountDaoImpl();
//    }
//
//    // 实例工厂方法
//    public IAccountDao createAccountDaoInstance() {
//        return new AccountDaoImpl();
//    }

    @Override
    public void saveAccount(int money){
        System.out.println("保存账户");
    }
}
