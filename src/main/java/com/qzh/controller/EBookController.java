package com.qzh.controller;

import com.qzh.req.EbookReq;
import com.qzh.req.PageReq;
import com.qzh.resp.CommonResp;
import com.qzh.resp.EbookResp;
import com.qzh.resp.PageResp;
import com.qzh.service.impl.EBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CommonResp list(PageReq pageReq){

        CommonResp<PageResp<EbookResp>> commonResp = new CommonResp<>();
        PageResp<EbookResp> list = eBookService.list(pageReq);
        commonResp.setContent(list);

        return commonResp;
    }

    @RequestMapping("/all")
    public CommonResp all(){

        CommonResp<List<EbookResp>> commonResp = new CommonResp<>();
        List<EbookResp> list = eBookService.list();
        commonResp.setContent(list);

        return commonResp;
    }

    @RequestMapping("/likeServlet/list")
    public CommonResp likeNameList(EbookReq ebookReq){
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> respList = eBookService.likeNameList(ebookReq);
        System.out.println(respList);
        resp.setContent(respList);
        System.out.println(resp);
        return resp;
    }

}
