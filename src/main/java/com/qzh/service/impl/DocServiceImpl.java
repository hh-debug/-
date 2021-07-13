package com.qzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qzh.domain.Content;
import com.qzh.domain.Doc;
import com.qzh.domain.DocExample;
import com.qzh.mapper.ContentMapper;
import com.qzh.mapper.DocMapper;
import com.qzh.req.DocQueryReq;
import com.qzh.req.DocSaveReq;
import com.qzh.resp.DocQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.DocService;
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
public class DocServiceImpl implements DocService {

    @Resource
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public PageResp<DocQueryResp> list(DocQueryReq docQueryReq) {

        DocExample docExample = new DocExample();//创建示例
        DocExample.Criteria criteria = docExample.createCriteria();//创建where条件
//        System.out.println("前端传递的名称"+docQueryReq.getName());

//        if (!ObjectUtils.isEmpty(docQueryReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
//            System.out.println("前端加了条件 进入if");
//        criteria.andNameLike("%" + docQueryReq.getName() + "%");//添加模糊查询条件
//        }

        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docs = docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo = new PageInfo<>(docs);
        //        列表的复制
        List<DocQueryResp> respList = CopyUtil.copyList(docs, DocQueryResp.class);



        PageResp<DocQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    @Override
    public List<DocQueryResp> list() {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");

        List<Doc> docs = docMapper.selectByExample(docExample);

        //        列表的复制
        List<DocQueryResp> respList = CopyUtil.copyList(docs, DocQueryResp.class);
        return respList;
    }


    @Override
    public List<DocQueryResp> likeNameList(DocQueryReq docQueryReq) {
        DocExample docExample = new DocExample();//创建示例
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();//创建where条件

//        if (!ObjectUtils.isEmpty(docReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
//            criteria.andNameLike("%" + docQueryReq.getName() + "%");//添加模糊查询条件
//        }

        List<Doc> docList = docMapper.selectByExample(docExample);//根据条件查询所有的doc

        //不应该响应全部的数据库字段 有些是敏感字段
        List<DocQueryResp> docQueryRespList = new ArrayList<>();
        System.out.println(docList);

//        for (Doc doc : docList) {
////            DocResp docResp = new DocResp();
//            //注意下面源跟目标 源是每一个循环的doc 目标是对应的实体
//            BeanUtils.copyProperties(doc,docResp);
//            docRespList.add(docResp);
//
//            //对象的复制
//            DocResp resp = CopyUtil.copy(doc, DocResp.class);
//        }
//        System.out.println(docRespList);

//        列表的复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }

    @Override
    public void save(DocSaveReq docSaveReq) {
        Doc doc = CopyUtil.copy(docSaveReq, Doc.class);
        Content content = CopyUtil.copy(docSaveReq, Content.class);
        System.out.println("复制后的实体:"+doc);
        if (ObjectUtils.isEmpty(docSaveReq.getId())) {
            doc.setId(snowFlake.nextId());
            //新增
            docMapper.insert(doc);


            content.setId(doc.getId());
            //新增
            contentMapper.insert(content);
        }else {
            //修改
            System.out.println("传递的主键id"+doc.getId());
            int i = docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
            System.out.println("修改影响行数:"+i);
        }
    }

    @Override
    public void delete(List<String> idsStr) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(idsStr);

        docMapper.deleteByExample(docExample);
//        docMapper.deleteByPrimaryKey(id);
    }
}
