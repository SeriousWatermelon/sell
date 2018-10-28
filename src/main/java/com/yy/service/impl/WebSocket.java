package com.yy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 稻草人 on 2018/10/28.
 * 卖家端消息推送配置
 */
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocket {

    private Session session;

    /**
     * 定义CopyOnWriteArraySet容器，用于存储session
     * 不能使用普通的Set是因为
     */
    private static CopyOnWriteArraySet<WebSocket> websocketSet = new CopyOnWriteArraySet<>();

    /**
     * 开启websocket
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        websocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数：{}",websocketSet.size());
    }

    /**
     * websocket连接关闭
     * 移除session
     */
    @OnClose
    public void onClose(){
        websocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数：{}",websocketSet.size());
    }

    /**
     * websocket收到消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端信息：{}",message);
    }

    public void sendMessage(String message){
        for (WebSocket webSocket : websocketSet){
            log.info("【websocket消息】广播消息，message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
