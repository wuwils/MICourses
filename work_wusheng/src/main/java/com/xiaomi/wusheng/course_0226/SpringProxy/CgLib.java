package com.xiaomi.wusheng.course_0226.SpringProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class CgLib {
    public static void main(String[] args) {
        ActorCg actorCg = new ActorCg();
        Enhancer.create(actorCg.getClass(), (MethodInterceptor)(o, method, args1, methodProxy) -> {
            String name = method.getName();
            Float money = (Float) args1[0];
            Object rtValue = null;
            if ("basicPlay".equals(name)) {
                if (money > 2000) {
                    rtValue = method.invoke(actorCg, money/2);
                }
            }

            if ("dangerPlay".equals(name)) {
                if (money > 5000) {
                    rtValue = method.invoke(actorCg, money - 5000);
                }
            }
            return rtValue;
        });
        actorCg.basicPlay(4000f);
        actorCg.dangerPlay(10000f);
    }
}
