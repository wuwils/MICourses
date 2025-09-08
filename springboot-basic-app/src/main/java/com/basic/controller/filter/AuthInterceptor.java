package com.basic.controller.filter;

import com.basic.dao.ApiPvMapper;
import com.basic.model.ApiPv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    ApiPvMapper apiPvMapper;

    Map<String, Integer> pvMap = new HashMap<>();

    /*
    * 使用pvMap暂存PV并定时批量保存到数据库的设计，
    * 不仅能够显著提升性能，减少数据库压力，
    * 还能保证数据的一致性和系统的容错能力。
    * 因此，相比于每次请求直接写入数据库，
    * 这种批量处理的方式更为合理和高效
    * */
    @Scheduled(fixedRate = 1000 * 60)
    void savePv() {
        synchronized (pvMap) {
            for (Map.Entry<String, Integer> entry : pvMap.entrySet()) {
                String uri = entry.getKey();
                Integer pv = entry.getValue();
                apiPvMapper.insertOrUpdate(uri, pv);
            }
            pvMap.clear();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object)
            throws Exception {
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        String uri = httpServletRequest.getRequestURI();
        synchronized (pvMap) {
//            apiPvMapper.insertOrUpdate(uri, 1); // 更新pv，计数
            Integer value = pvMap.get(uri);
            if (value == null) {
                value = 0;
            }
            pvMap.put(uri, value + 1);
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(NoAuth.class)) {
            return true;
        }
        return true;
    }
}
