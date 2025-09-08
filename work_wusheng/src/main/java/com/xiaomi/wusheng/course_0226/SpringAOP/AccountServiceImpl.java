package com.xiaomi.wusheng.course_0226.SpringAOP;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component("accountService")
//@Scope("prototype")
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    @Resource(name = "accountDao")
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void saveAccount(int money) {
        accountDao.saveAccount(money);
    }
}