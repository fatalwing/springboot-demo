package com.townmc.demo.web.controller;

import com.townmc.demo.domain.dto.DemoResponse;
import com.townmc.demo.utils.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/index.htm")
    public String index() {
        return "/index";
    }

    @RequestMapping("/success.json")
    @ResponseBody
    public ApiResponse apiSuccess() {
        DemoResponse dr = new DemoResponse();
        dr.setId("a1");
        dr.setName("张三");
        return ApiResponse.success(dr);
    }

    @RequestMapping("/fail.json")
    @ResponseBody
    public ApiResponse apiFail() {
        return ApiResponse.fail("10001", "有异常");
    }

}
