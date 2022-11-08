package com.itfuture.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 10:34
 */
@RestController
public class AdminController {
    @GetMapping("/admin")
    public String login(){
        return "/web/index";
    }
}
