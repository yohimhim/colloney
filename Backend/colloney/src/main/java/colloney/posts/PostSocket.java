package colloney.posts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import colloney.config.SpringContext;
import colloney.log.ConnectionLogService;
import colloney.log.enums.ConnectionType;
import colloney.log.enums.EventType;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@ServerEndpoint(value = "/posts/{userId}/{username}")
public class PostSocket {

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("username") String username)
            throws IOException {
        ConnectionLogService logger = SpringContext.getBean(ConnectionLogService.class);
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        logger.logConnection(userId, username, ConnectionType.POST, EventType.CONNECT);
        log.info("WebSocket opened for user '{}', session ID: {}", username, session.getId());
        log.info("Current connected users: {}", usernameSessionMap.keySet());
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        String username = sessionUsernameMap.get(session);
        ConnectionLogService logger = SpringContext.getBean(ConnectionLogService.class);
        logger.logConnection(userId, username, ConnectionType.POST, EventType.DISCONNECT);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        log.info("WebSocket closed.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket error", throwable);
    }

    public static void broadcastUpdatedPosts() {
        try {
            PostRepository repo = SpringContext.getBean(PostRepository.class);
            List<Post> posts = repo.findAll();

            List<Map<String, Object>> postDtos = posts.stream().map(PostSocket::toPostDto).toList();

            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(postDtos);

            for (Session session : sessionUsernameMap.keySet()) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            }
        } catch (Exception e) {
            log.error("Error during broadcastUpdatedPosts()", e);
        }
    }

    private static Map<String, Object> toPostDto(Post p) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", p.getId());
        m.put("content", p.getContent());

        if (p.getCreatedAt() != null) {
            m.put("createdAt", p.getCreatedAt().toString());
        }
        if (p.getUpdatedAt() != null) {
            m.put("updatedAt", p.getUpdatedAt().toString());
        }

        if (p.getAuthor() != null) {
            Map<String, Object> author = new HashMap<>();
            author.put("id", p.getAuthor().getId());
            author.put("username", p.getAuthor().getUsername());
            m.put("author", author);
        }

        return m;
    }

}
