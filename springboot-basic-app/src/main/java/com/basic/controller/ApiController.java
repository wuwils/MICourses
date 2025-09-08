package com.basic.controller;

import com.basic.client.WeatherClient;
import com.basic.controller.filter.ResponseUtils;
import com.basic.model.ApiPv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    WeatherClient weatherClient;

    // 每秒执行一次
    @Scheduled(fixedDelay = 1000)
    void reloadData() {
        log.info("loadData");
    }

    @RequestMapping("/weather")
    Object getWeather() {
        String weather = weatherClient.getWeather();
        return ResponseUtils.result(weather);
    }
}
