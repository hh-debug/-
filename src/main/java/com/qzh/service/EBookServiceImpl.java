package com.qzh.service;

import com.qzh.domain.Ebook;
import com.qzh.mapper.EbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookServiceImpl
 * @date:2021/7/913:37
 */
@Service
public class EBookServiceImpl implements EBookService{

    @Autowired
    private EbookMapper ebookMapper;

    @Override
    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }
}
