package com.qzh.controller;

import com.qzh.req.EbookQueryReq;
import com.qzh.req.EbookSaveReq;
import com.qzh.req.PageReq;
import com.qzh.resp.CommonResp;
import com.qzh.resp.EbookQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.impl.EBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();
        PageResp<EbookQueryResp> list = eBookService.list(pageReq);
        commonResp.setContent(list);

        return commonResp;
    }

    @RequestMapping("/all")
    public CommonResp all(){

        CommonResp<List<EbookQueryResp>> commonResp = new CommonResp<>();
        List<EbookQueryResp> list = eBookService.list();
        commonResp.setContent(list);

        return commonResp;
    }

    @RequestMapping("/likeServlet/list")
    public CommonResp likeNameList(EbookQueryReq ebookQueryReq){
        CommonResp<List<EbookQueryResp>> resp = new CommonResp<>();
        List<EbookQueryResp> respList = eBookService.likeNameList(ebookQueryReq);
        System.out.println(respList);
        resp.setContent(respList);
        System.out.println(resp);
        return resp;
    }

    @RequestMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq ebookSaveReq){

        CommonResp commonResp = new CommonResp<>();
        eBookService.save(ebookSaveReq);
        return commonResp;
    }

}
