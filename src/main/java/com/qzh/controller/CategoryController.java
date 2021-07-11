package com.qzh.controller;

import com.qzh.req.CategoryQueryReq;
import com.qzh.req.CategorySaveReq;
import com.qzh.resp.CommonResp;
import com.qzh.resp.CategoryQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.impl.CategoryServiceImpl;
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
@RequestMapping("/category")
public class CategoryController {

    //测试mybatis集成自动生成插件
    @Autowired
    private CategoryServiceImpl categoryService;

//    @RequestMapping("/list")
//    public List<Category> hello(){
//        return categoryService.list();
//    }

//    规范后端开发 为了前端能够统一处理逻辑 需要后端统一的返回值

    //分页查询全部
    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQueryReq){

        CommonResp<PageResp<CategoryQueryResp>> commonResp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(categoryQueryReq);
        commonResp.setContent(list);

        return commonResp;
    }

    //查询全部
    @RequestMapping("/all")
    public CommonResp all(){

        CommonResp<List<CategoryQueryResp>> commonResp = new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.list();
        commonResp.setContent(list);

        return commonResp;
    }

    //模糊查询
    @RequestMapping("/likeServlet/list")
    public CommonResp likeNameList(CategoryQueryReq categoryQueryReq){
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> respList = categoryService.likeNameList(categoryQueryReq);
        System.out.println(respList);
        resp.setContent(respList);
        System.out.println(resp);
        return resp;
    }

    //新增或修改数据
    @PostMapping ("/save")
    public CommonResp save(@RequestBody @Valid CategorySaveReq categorySaveReq){

        CommonResp commonResp = new CommonResp<>();
        categoryService.save(categorySaveReq);
        return commonResp;
    }

    //删除数据
    @PostMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable long id){

        CommonResp commonResp = new CommonResp<>();
        categoryService.delete(id);
        return commonResp;
    }

}
