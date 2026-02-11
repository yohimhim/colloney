package colloney.ai;

import java.time.Instant;

import lombok.Data;

@Data
public class OllamaResponseDto {

    private String model;
    private String response;
    private Instant createdAt;

}
