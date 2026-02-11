package colloney.log;

import java.time.Instant;

import org.springframework.stereotype.Service;

import colloney.log.enums.ConnectionType;
import colloney.log.enums.EventType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionLogService {

    private final ConnectionLogRepository connectionLogRepository;

    public void logConnection(String userId, String username, ConnectionType connectionType, EventType eventType) {
        ConnectionLog log = new ConnectionLog();
        log.setUserId(userId);
        log.setUsername(username);
        log.setConnectionType(connectionType);
        log.setEventType(eventType);
        log.setEventTime(Instant.now());

        connectionLogRepository.save(log);
    }
}
