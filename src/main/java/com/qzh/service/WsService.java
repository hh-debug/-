package com.qzh.service;

import com.qzh.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @packageName:com.qzh.service
 * @ClassName:WsService
 * @date:2021/7/1517:50
 */
@Service
public class WsService {
    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message, String logId) {
        MDC.put("LOG_ID", logId);
        webSocketServer.sendInfo(message);
    }
}
