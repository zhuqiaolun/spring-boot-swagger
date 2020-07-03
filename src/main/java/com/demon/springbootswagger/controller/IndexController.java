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
        return "swagger-ui/swagger";
    }

}
