package colloney.log;

import java.time.Instant;

import colloney.log.enums.ConnectionType;
import colloney.log.enums.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String username;

    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private Instant eventTime;
}
