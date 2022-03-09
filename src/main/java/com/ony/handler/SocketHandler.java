package com.ony.handler;

import io.undertow.util.CopyOnWriteMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * Socket处理器
 *
 * @author Tony
 * @date 2021/8/17
 */
@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {

    public static Map<String, WebSocketSession> sessions = new CopyOnWriteMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("收到消息 session = " + session + "message = " + message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("收到链接: " + session);
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("断开链接: " + session);
        String id = session.getId();
        sessions.remove(id);
    }
}