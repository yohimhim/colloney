package colloney.posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {
    @NotNull
    private Long authorId;

    @NotBlank
    private String content;
}
