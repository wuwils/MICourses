package com.xiaomi.wusheng.course_0226.Template;

public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = new AccountDaoImpl();

    @Override
    public void saveAccount(int money){
        accountDao.saveAccount(money);
    }
}