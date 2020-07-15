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

    @GetMapping(value = "info")
    public String swagger() {
        return "swagger-info/info";
    }

    @GetMapping(value = "tags")
    public String tags() {
        return "swagger-tags/tags";
    }

    @GetMapping(value = "url")
    public String url() {
        return "swagger-url/url";
    }

    @GetMapping(value = "demo")
    public String demo() {
        return "help/demo_data";
    }

}
