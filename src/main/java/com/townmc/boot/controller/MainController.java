package com.townmc.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller("mainController")
@RequestMapping(value = "/main")
@Slf4j
public class MainController {

    @RequestMapping("/index.htm")
    public String index() {
        return "/_demo_/index";
    }

}
