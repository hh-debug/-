package com.qzh.controller;

import com.qzh.req.EbookQueryReq;
import com.qzh.req.EbookSaveReq;
import com.qzh.resp.CommonResp;
import com.qzh.resp.EbookQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.impl.EBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    //分页查询全部
    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq ebookQueryReq){

        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();
        PageResp<EbookQueryResp> list = eBookService.list(ebookQueryReq);
        commonResp.setContent(list);

        return commonResp;
    }

    //查询全部
    @RequestMapping("/all")
    public CommonResp all(){

        CommonResp<List<EbookQueryResp>> commonResp = new CommonResp<>();
        List<EbookQueryResp> list = eBookService.list();
        commonResp.setContent(list);

        return commonResp;
    }

    //模糊查询
    @RequestMapping("/likeServlet/list")
    public CommonResp likeNameList(EbookQueryReq ebookQueryReq){
        CommonResp<List<EbookQueryResp>> resp = new CommonResp<>();
        List<EbookQueryResp> respList = eBookService.likeNameList(ebookQueryReq);
        System.out.println(respList);
        resp.setContent(respList);
        System.out.println(resp);
        return resp;
    }

    //新增或修改数据
    @PostMapping ("/save")
    public CommonResp save(@RequestBody @Valid EbookSaveReq ebookSaveReq){

        CommonResp commonResp = new CommonResp<>();
        eBookService.save(ebookSaveReq);
        return commonResp;
    }

    //删除数据
    @PostMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable long id){

        CommonResp commonResp = new CommonResp<>();
        eBookService.delete(id);
        return commonResp;
    }

}
