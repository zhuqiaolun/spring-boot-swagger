package com.demon.springbootswagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * @Description:
 * @Author: Demon
 * @Date: 2020/7/2 23:54
 */
@Controller
@RequestMapping
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping(value = "swagger")
    public String swagger() {
        return "swagger";
    }

    @GetMapping(value = "tags")
    public String tags() {
        return "tags";
    }

    @GetMapping(value = "config")
    public String config() {
        return "config";
    }

    @GetMapping(value = "demo")
    public String demo() {
        return "demo";
    }

}
