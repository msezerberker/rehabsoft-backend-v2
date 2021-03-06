package com.hacettepe.rehabsoft.config;

import com.hacettepe.rehabsoft.service.OnlineMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

    private OnlineMeetingService onlineMeetingService;
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public SocketHandler(OnlineMeetingService onlineMeetingService) {
        this.onlineMeetingService = onlineMeetingService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();

        for (WebSocketSession webSocketSession : sessions) {
            System.out.println(webSocketSession.getPrincipal());
            if (webSocketSession.isOpen() && isThisUserNotCurrentUserAndHasMeetingWithCurrentConnectedUser(session.getPrincipal().getName(), webSocketSession.getPrincipal().getName())) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("connections: "+session.toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Server transport error: " + exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("access-token");
    }

    private boolean isThisUserNotCurrentUserAndHasMeetingWithCurrentConnectedUser(String currentConnectedUser, String otherUser) throws Exception {
        return !currentConnectedUser.equals(otherUser)
                && onlineMeetingService.getOnlineMeetingsByUsername(currentConnectedUser)
                .stream()
                .anyMatch(onlineMeetingDto -> onlineMeetingDto.getDoctorUser().getUsername().equals(otherUser) || onlineMeetingDto.getPatientUser().getUsername().equals(otherUser));
    }
}
