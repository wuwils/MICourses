package com.basic.controller;

import com.basic.controller.filter.ResponseUtils;
import com.basic.dao.ApiPvMapper;
import com.basic.model.ApiPv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class HealthController {

    @Autowired
    ApiPvMapper apiPvMapper;

    @RequestMapping("/health")
    Object health() {
//        Map<String, String> map = new HashMap<>();
//        map.put("status", "ok");
//        map.put("time", "2019-01-01");
//        return map;
//        return "are you ok";
        return ResponseUtils.error(0, "ok", null);
    }

    @RequestMapping("/health/pv")
    Object getPv() {
        ApiPv apiPv = apiPvMapper.getPv("/health");
        return ResponseUtils.result(apiPv);
    }
}
