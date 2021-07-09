package com.qzh.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName:com.qzh.controller
 * @ClassName:TestController
 * @date:2021/7/910:38
 */
@RestController
public class TestController {

//    测试自定义配置 变量要加spring表达式
    @Value("${hello.test}")
    private String ConfigTestHello;

    @RequestMapping("/hello")
    public String hello(){
        return "hello" + ConfigTestHello;
    }
}
