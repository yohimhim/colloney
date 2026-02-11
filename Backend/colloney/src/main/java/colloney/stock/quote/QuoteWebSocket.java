package colloney.stock.quote;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import colloney.config.SpringContext;
import colloney.log.ConnectionLogService;
import colloney.log.enums.ConnectionType;
import colloney.log.enums.EventType;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/ws/stock/quotes/symbol/{symbol}/{userId}/{username}")
public class QuoteWebSocket {
    private static final Map<String, Set<Session>> symbolSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("symbol") String symbol, @PathParam("userId") String userId,
            @PathParam("username") String username) {
        symbol = symbol.toLowerCase();

        ConnectionLogService logger = SpringContext.getBean(ConnectionLogService.class);
        logger.logConnection(userId, username, ConnectionType.QUOTE, EventType.CONNECT);

        log.info("Client connected for symbol: {}", symbol);
        symbolSessions.computeIfAbsent(symbol, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("symbol") String symbol, @PathParam("userId") String userId,
            @PathParam("username") String username) {
        symbol = symbol.toLowerCase();

        ConnectionLogService logger = SpringContext.getBean(ConnectionLogService.class);
        logger.logConnection(userId, username, ConnectionType.QUOTE, EventType.DISCONNECT);

        log.info("Client disconnected from symbol: {}", symbol);
        Set<Session> sessions = symbolSessions.get(symbol);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                symbolSessions.remove(symbol);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket error", throwable);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("Received message from client: {}", message);
    }

    public static void broadcast(String symbol, String jsonMessage) {
        symbol = symbol.toLowerCase();
        Set<Session> sessions = symbolSessions.get(symbol);
        if (sessions != null) {
            sessions.forEach(session -> {
                try {
                    session.getBasicRemote().sendText(jsonMessage);
                } catch (IOException e) {
                    log.error("Error sending WebSocket message, removing session", e);
                }
            });
        }
    }
}
