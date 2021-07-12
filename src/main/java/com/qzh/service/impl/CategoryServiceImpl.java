package com.qzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qzh.domain.Category;
import com.qzh.domain.CategoryExample;
import com.qzh.mapper.CategoryMapper;
import com.qzh.req.CategoryQueryReq;
import com.qzh.req.CategorySaveReq;
import com.qzh.resp.CategoryQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.CategoryService;
import com.qzh.util.CopyUtil;
import com.qzh.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookServiceImpl
 * @date:2021/7/913:37
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public PageResp<CategoryQueryResp> list(CategoryQueryReq categoryQueryReq) {

        CategoryExample categoryExample = new CategoryExample();//创建示例
        CategoryExample.Criteria criteria = categoryExample.createCriteria();//创建where条件
//        System.out.println("前端传递的名称"+categoryQueryReq.getName());

//        if (!ObjectUtils.isEmpty(categoryQueryReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
//            System.out.println("前端加了条件 进入if");
//        criteria.andNameLike("%" + categoryQueryReq.getName() + "%");//添加模糊查询条件
//        }

        PageHelper.startPage(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        //        列表的复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categorys, CategoryQueryResp.class);



        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    @Override
    public List<CategoryQueryResp> list() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");

        List<Category> categorys = categoryMapper.selectByExample(categoryExample);

        //        列表的复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categorys, CategoryQueryResp.class);
        return respList;
    }


    @Override
    public List<CategoryQueryResp> likeNameList(CategoryQueryReq categoryQueryReq) {
        CategoryExample categoryExample = new CategoryExample();//创建示例
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();//创建where条件

//        if (!ObjectUtils.isEmpty(categoryReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
//            criteria.andNameLike("%" + categoryQueryReq.getName() + "%");//添加模糊查询条件
//        }

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);//根据条件查询所有的category

        //不应该响应全部的数据库字段 有些是敏感字段
        List<CategoryQueryResp> categoryQueryRespList = new ArrayList<>();
        System.out.println(categoryList);

//        for (Category category : categoryList) {
////            CategoryResp categoryResp = new CategoryResp();
//            //注意下面源跟目标 源是每一个循环的category 目标是对应的实体
//            BeanUtils.copyProperties(category,categoryResp);
//            categoryRespList.add(categoryResp);
//
//            //对象的复制
//            CategoryResp resp = CopyUtil.copy(category, CategoryResp.class);
//        }
//        System.out.println(categoryRespList);

//        列表的复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return respList;
    }

    @Override
    public void save(CategorySaveReq categorySaveReq) {
        Category category = CopyUtil.copy(categorySaveReq, Category.class);
        System.out.println("复制后的实体:"+category);
        if (ObjectUtils.isEmpty(categorySaveReq.getId())) {
            long id = snowFlake.nextId();
            category.setId(id);
            //新增
            categoryMapper.insert(category);
        }else {
            //修改
            System.out.println("传递的主键id"+category.getId());
            int i = categoryMapper.updateByPrimaryKey(category);
            categoryMapper.updateByPrimaryKey(category);
            System.out.println("修改影响行数:"+i);
        }
    }

    @Override
    public void delete(long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
