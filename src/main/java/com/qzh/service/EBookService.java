package com.qzh.service;

import com.qzh.req.EbookQueryReq;
import com.qzh.req.EbookSaveReq;
import com.qzh.req.PageReq;
import com.qzh.resp.EbookQueryResp;
import com.qzh.resp.PageResp;

import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface EBookService {
    public PageResp<EbookQueryResp> list(PageReq pageReq);

    public List<EbookQueryResp> list();

    public List<EbookQueryResp> likeNameList(EbookQueryReq ebookQueryReq);

    void save(EbookSaveReq ebookSaveReq);

    void delete(long id);
}
