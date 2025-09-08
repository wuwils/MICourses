package com.xiaomi.wusheng.course_0226.SpringAOP;
import org.springframework.stereotype.Component;

@Component("casAccountDao")
public class CasAccountDaoImpl implements IAccountDao {

    @Override
    public void saveAccount(int money) {
        System.out.println("保存cas账号");
    }
}
