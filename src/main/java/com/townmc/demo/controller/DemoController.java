package com.townmc.demo.controller;

import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.DemoService;
import com.townmc.demo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired private DemoService demoService;

    @RequestMapping("/add.json")
    @ResponseBody
    public ApiResponse add(String id, String name) {

        Demo re = demoService.add(id, name);
        return ApiResponse.success(re);
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public ApiResponse get(String id) {
        Demo re = demoService.findOne(id);
        return ApiResponse.success(re);
    }
}
