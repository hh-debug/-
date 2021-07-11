package com.qzh.service;

import com.qzh.req.EbookReq;
import com.qzh.req.PageReq;
import com.qzh.resp.EbookResp;
import com.qzh.resp.PageResp;

import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface EBookService {
    public PageResp<EbookResp> list(PageReq pageReq);

    public List<EbookResp> likeNameList(EbookReq ebookReq);
}
