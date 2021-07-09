package com.qzh.controller;

import com.qzh.domain.Ebook;
import com.qzh.service.EBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @packageName:com.qzh.controller
 * @ClassName:TestController
 * @date:2021/7/910:38
 */
@RestController
@RequestMapping("/ebook")
public class TestController {

//    测试自定义配置 变量要加spring表达式
    @Value("${hello.test}")
    private String ConfigTestHello;

    //测试mybatis集成自动生成插件
    @Autowired
    private EBookServiceImpl eBookService;

    @RequestMapping("/list")
    public List<Ebook> hello(){
        return eBookService.list();
    }
}
