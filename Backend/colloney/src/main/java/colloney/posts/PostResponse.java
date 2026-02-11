package colloney.posts;

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
@Schema(description = "Representation of a post")
public class PostResponse {

    @Schema(description = "Unique identifier of the post", example = "101")
    private Long id;

    @Schema(description = "Summary of the author of the post")
    private UserSummary author;

    @Schema(description = "Text content of the post", example = "Just bought AAPL stock!")
    private String content;

    @Schema(description = "Timestamp when the post was created", example = "2025-11-07T14:42:45.106240Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the post was last updated", example = "2025-11-15T04:07:17.393516Z")
    private Instant updatedAt;
}
