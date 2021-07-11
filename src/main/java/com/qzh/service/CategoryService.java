package com.qzh.service;

import com.qzh.req.CategoryQueryReq;
import com.qzh.req.CategorySaveReq;
import com.qzh.resp.CategoryQueryResp;
import com.qzh.resp.PageResp;

import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface CategoryService {
    public PageResp<CategoryQueryResp> list(CategoryQueryReq categoryQueryReq);

    public List<CategoryQueryResp> list();

    public List<CategoryQueryResp> likeNameList(CategoryQueryReq categoryQueryReq);

    void save(CategorySaveReq categorySaveReq);

    void delete(long id);
}
