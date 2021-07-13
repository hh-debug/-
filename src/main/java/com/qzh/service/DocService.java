package com.qzh.service;

import com.qzh.req.DocQueryReq;
import com.qzh.req.DocSaveReq;
import com.qzh.resp.DocQueryResp;
import com.qzh.resp.PageResp;

import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface DocService {
    public PageResp<DocQueryResp> list(DocQueryReq docQueryReq);

    public List<DocQueryResp> list();

    public List<DocQueryResp> likeNameList(DocQueryReq docQueryReq);

    void save(DocSaveReq docSaveReq);

    void delete(List<String> idsStr);

    public String findContent(Long id);
}
