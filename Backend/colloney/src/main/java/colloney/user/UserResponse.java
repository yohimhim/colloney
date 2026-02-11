package colloney.user;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents a user account")
public class UserResponse {

    @Schema(description = "Unique identifier of the user", example = "39")
    private Long id;

    @Schema(description = "Username of the user", example = "drew")
    private String username;

    @Schema(description = "Email address of the user", example = "drew@gmail.com")
    private String email;

    @Schema(description = "Timestamp when the user was created", example = "2025-10-07T23:32:50.150206Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the user was last updated", example = "2025-10-09T15:34:22.569121Z")
    private Instant updatedAt;

    @Schema(description = "Role of the user", example = "USER")
    private Role role;
}
