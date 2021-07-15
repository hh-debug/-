package com.qzh.job;


import com.qzh.service.impl.DocServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DocJob {

    @Resource
    private DocServiceImpl docService;

    /**
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "1/5 * * * * ?")
    public void cron() {
        docService.updateEbookInfo();
    }

}