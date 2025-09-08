package com.xiaomi.wusheng.course_0226.SpringProxy;

import java.lang.reflect.Proxy;

public class JDKProxy {

    public static void main(String[] args) {
        IActor actor = new ActorImpl();
        IActor proxyActor = (IActor) Proxy.newProxyInstance(actor.getClass().getClassLoader(),
                actor.getClass().getInterfaces(), (proxy, method, args1) -> {
                    String name = method.getName();
                    Float money = (Float) args1[0];
                    Object rtValue = null;
                    if ("basicPlay".equals(name)) {
                        if (money > 2000) {
                            rtValue = method.invoke(actor, money / 2);
                        }
                    }
                    if ("dangerPlay".equals(name)) {
                        if (money > 5000) {
                            rtValue = method.invoke(actor, money - 2000);
                        }
                    }
                    return rtValue;
                });
        proxyActor.basicPlay(4000f);
        proxyActor.dangerPlay(10000f);

    }
}
