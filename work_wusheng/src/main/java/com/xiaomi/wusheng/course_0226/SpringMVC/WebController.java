package com.xiaomi.wusheng.course_0226.SpringMVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class WebController {

    @RequestMapping("/check")
    public String check() {
        System.out.println("check success");
        return "success";
    }


}
