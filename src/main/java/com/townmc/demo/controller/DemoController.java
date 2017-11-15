package com.townmc.demo.controller;

import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.DemoService;
import com.townmc.demo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/_demo_")
public class DemoController {

    @Autowired private DemoService demoService;

    /**
     * 访问页面
     * @return
     */
    @RequestMapping("/index.htm")
    public String indexPage() {
        return "/index";
    }


    @RequestMapping("/add.json")
    @ResponseBody
    public ApiResponse add(@RequestParam(name="id", required=true) String id,
                           @RequestParam(name="name", required=false) String name) {

        Demo re = demoService.add(id, name);
        return ApiResponse.success(re);
    }

    /**
     * 通过post的请求体传参
     * @param requestBody
     * @return
     */
    @PostMapping("/update.json")
    @ResponseBody
    public ApiResponse add(@RequestBody String requestBody) {

        return ApiResponse.success("");
    }

    /**
     * 通过rest风格path传参
     * @param id
     * @return
     */
    @RequestMapping("/{id}/get.json")
    @ResponseBody
    public ApiResponse get(@PathVariable("id") String id) {
        Demo re = demoService.findOne(id);
        return ApiResponse.success(re);
    }
}
