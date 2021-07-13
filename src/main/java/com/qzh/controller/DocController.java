package com.qzh.controller;

import com.qzh.req.DocQueryReq;
import com.qzh.req.DocSaveReq;
import com.qzh.resp.DocQueryResp;
import com.qzh.resp.CommonResp;
import com.qzh.resp.PageResp;
import com.qzh.service.impl.DocServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @packageName:com.qzh.controller
 * @ClassName:TestController
 * @date:2021/7/910:38
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    //测试mybatis集成自动生成插件
    @Autowired
    private DocServiceImpl docService;

//    @RequestMapping("/list")
//    public List<Doc> hello(){
//        return docService.list();
//    }

//    规范后端开发 为了前端能够统一处理逻辑 需要后端统一的返回值

    //分页查询全部
    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq docQueryReq){

        CommonResp<PageResp<DocQueryResp>> commonResp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(docQueryReq);
        commonResp.setContent(list);

        return commonResp;
    }

    //查询全部
    @RequestMapping("/all")
    public CommonResp all(){

        CommonResp<List<DocQueryResp>> commonResp = new CommonResp<>();
        List<DocQueryResp> list = docService.list();
        commonResp.setContent(list);

        return commonResp;
    }

    //模糊查询
    @RequestMapping("/likeServlet/list")
    public CommonResp likeNameList(DocQueryReq docQueryReq){
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> respList = docService.likeNameList(docQueryReq);
        System.out.println(respList);
        resp.setContent(respList);
        System.out.println(resp);
        return resp;
    }

    //新增或修改数据
    @PostMapping ("/save")
    public CommonResp save(@RequestBody @Valid DocSaveReq docSaveReq){

        CommonResp commonResp = new CommonResp<>();
        docService.save(docSaveReq);
        return commonResp;
    }

    //删除数据
    @PostMapping ("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){

        String[] split = idsStr.split(",");
        List<String> strings = Arrays.asList(split);

        CommonResp commonResp = new CommonResp<>();
        docService.delete(strings);
        return commonResp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

}
