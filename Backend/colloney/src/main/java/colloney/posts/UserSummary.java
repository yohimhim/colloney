package colloney.posts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Summary of information of a user")
public class UserSummary {

    @Schema(description = "Unique identifier of the user", example = "39")
    private Long id;

    @Schema(description = "Username of the user", example = "drew")
    private String username;
}
