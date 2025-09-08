package com.xiaomi.wusheng.course_0228.SpringBootRocketMQ;

import com.xiaomi.wusheng.course_0228.SpringBootRocketMQ.RocketMQProducer ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rocketMQProducer.sendMessage("test-topic", message);
        return "Message sent: " + message;
    }
}
