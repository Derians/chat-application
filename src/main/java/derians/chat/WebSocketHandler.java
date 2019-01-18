package derians.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ivan Chaykin
 * Date: 28.12.2018
 * Time: 11:23
 */
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    public static List<WebSocketSession> sessions = new ArrayList<>();
    public static Map<WebSocketSession, String> mapSessions = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        String[] cookies = handshakeHeaders.get("cookie").get(0).split(";");
        String userName = (cookies[1].split("="))[1];
        System.out.println(userName);
        mapSessions.put(session, userName);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        mapSessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {

        Message message = new Message();
        message.setType("message");
        message.setText((String) webSocketMessage.getPayload());
        message.setSender(mapSessions.get(session));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(message);
        sessions.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}
