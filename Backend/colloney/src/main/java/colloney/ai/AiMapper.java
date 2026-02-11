package colloney.ai;

import java.time.Instant;

import colloney.user.User;

public class AiMapper {

    public static AiChat toEntity(User user, AiRequest request, OllamaResponseDto response) {
        return AiChat.builder()
        // @formatter:off
                .user(user)
                .model(request.getModel())
                .prompt(request.getPrompt())
                .response(response.getResponse())
                .createdAt(Instant.now())
                .build();
            // @formatter:on

    }

    public static AiResponse toResponse(AiChat log) {
        return AiResponse.builder()
        // @formatter:off

                .model(log.getModel())
                .prompt(log.getPrompt())
                .response(log.getResponse())
                .createdAt(log.getCreatedAt())
                .build();
            // @formatter:on
    }

}
