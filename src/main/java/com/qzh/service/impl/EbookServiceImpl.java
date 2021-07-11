package com.qzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qzh.domain.Ebook;
import com.qzh.domain.EbookExample;
import com.qzh.mapper.EbookMapper;
import com.qzh.req.EbookQueryReq;
import com.qzh.req.EbookSaveReq;
import com.qzh.resp.EbookQueryResp;
import com.qzh.resp.PageResp;
import com.qzh.service.EbookService;
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
public class EbookServiceImpl implements EbookService {

    @Resource
    private EbookMapper ebookMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public PageResp<EbookQueryResp> list(EbookQueryReq ebookQueryReq) {

        EbookExample ebookExample = new EbookExample();//创建示例
        EbookExample.Criteria criteria = ebookExample.createCriteria();//创建where条件
        System.out.println("前端传递的名称"+ebookQueryReq.getName());

        if (!ObjectUtils.isEmpty(ebookQueryReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
            System.out.println("前端加了条件 进入if");
        criteria.andNameLike("%" + ebookQueryReq.getName() + "%");//添加模糊查询条件
        }

        PageHelper.startPage(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        //        列表的复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebooks, EbookQueryResp.class);



        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    @Override
    public List<EbookQueryResp> list() {



        List<Ebook> ebooks = ebookMapper.selectByExample(null);

        //        列表的复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebooks, EbookQueryResp.class);
        return respList;
    }


    @Override
    public List<EbookQueryResp> likeNameList(EbookQueryReq ebookQueryReq) {
        EbookExample ebookExample = new EbookExample();//创建示例
        EbookExample.Criteria criteria = ebookExample.createCriteria();//创建where条件

//        if (!ObjectUtils.isEmpty(ebookReq.getName())){//如果前端不传条件 默认为不加条件 查询所有
            criteria.andNameLike("%" + ebookQueryReq.getName() + "%");//添加模糊查询条件
//        }

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);//根据条件查询所有的ebook

        //不应该响应全部的数据库字段 有些是敏感字段
        List<EbookQueryResp> ebookQueryRespList = new ArrayList<>();
        System.out.println(ebookList);

//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
//            //注意下面源跟目标 源是每一个循环的ebook 目标是对应的实体
//            BeanUtils.copyProperties(ebook,ebookResp);
//            ebookRespList.add(ebookResp);
//
//            //对象的复制
//            EbookResp resp = CopyUtil.copy(ebook, EbookResp.class);
//        }
//        System.out.println(ebookRespList);

//        列表的复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        return respList;
    }

    @Override
    public void save(EbookSaveReq ebookSaveReq) {
        Ebook ebook = CopyUtil.copy(ebookSaveReq, Ebook.class);
        if (ObjectUtils.isEmpty(ebookSaveReq.getId())) {
            long id = snowFlake.nextId();
            ebook.setId(id);
            //新增
            ebookMapper.insert(ebook);
        }else {
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    @Override
    public void delete(long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
