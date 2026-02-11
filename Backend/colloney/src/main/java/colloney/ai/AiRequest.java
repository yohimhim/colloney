package colloney.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiRequest {

    private String model;
    @NotBlank
    private String prompt;

}
