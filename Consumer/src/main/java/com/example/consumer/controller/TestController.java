package com.example.consumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lgk
 * @date 2024-12-30 17:07:12
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "success";
    }
}
