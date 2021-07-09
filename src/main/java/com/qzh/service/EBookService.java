package com.qzh.service;

import com.qzh.domain.Ebook;
import com.qzh.req.EbookReq;
import com.qzh.resp.EbookResp;

import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface EBookService {
    public List<Ebook> list();

    public List<EbookResp> likeNameList(EbookReq ebookReq);
}
