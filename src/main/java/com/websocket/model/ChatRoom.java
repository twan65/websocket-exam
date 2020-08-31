package com.websocket.model;

import com.websocket.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    // Chatに参加しているクライアントの情報を保存
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        // 参加、会話機能があるため、分岐処理
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "さんが参加しました。");
        }

        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}