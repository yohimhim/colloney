package colloney.ai;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiResponse {

    private String model;
    private String prompt;
    private String response;
    private Instant createdAt;

}
