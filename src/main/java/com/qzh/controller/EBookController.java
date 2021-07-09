package com.qzh.controller;

import com.qzh.domain.Ebook;
import com.qzh.resp.CommonResp;
import com.qzh.service.EBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class EBookController {

    //测试mybatis集成自动生成插件
    @Autowired
    private EBookServiceImpl eBookService;

//    @RequestMapping("/list")
//    public List<Ebook> hello(){
//        return eBookService.list();
//    }

//    规范后端开发 为了前端能够统一处理逻辑 需要后端统一的返回值

    @RequestMapping("/list")
    public CommonResp list(){
        CommonResp<List<Ebook>> commonResp = new CommonResp<>();
        List<Ebook> list = eBookService.list();
        commonResp.setContent(list);

        return commonResp;
    }

}
