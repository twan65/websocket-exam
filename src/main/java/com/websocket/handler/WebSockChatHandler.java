package com.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * Clientから受け取ったメッセージをConsole Logで出力し、ClientにWellcomeメッセージを送るクラス
 *
 */
@Slf4j
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // logにメッセージを出力
        log.info("payload {}", payload);

        // TextMessageを利用しClientにメッセージを送る。
        TextMessage textMessage = new TextMessage("Welcome chatting Server~");
        session.sendMessage(textMessage);
    }
}
