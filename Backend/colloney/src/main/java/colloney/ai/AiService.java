package colloney.ai;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import colloney.user.User;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final AiRepository aiRepository;

    @Value("${ollama.base-url}")
    private String ollamaBaseUrl;

    public ApiResponse<AiResponse> generate(Long userId, AiRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Map<String, Object> body = new HashMap<>();
        body.put("model", request.getModel());
        body.put("prompt", request.getPrompt());
        body.put("stream", false);

        String url = ollamaBaseUrl + "/api/generate";

        OllamaResponseDto ollamaResponse = restTemplate.postForObject(url, body, OllamaResponseDto.class);

        if (ollamaResponse == null || ollamaResponse.getResponse() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Ollama returned an empty or null response.");
        }

        AiChat chat = AiMapper.toEntity(user, request, ollamaResponse);
        aiRepository.save(chat);

        AiResponse response = AiMapper.toResponse(chat);

        return ApiResponse.ok("Response Generated", response);

    }

    public ApiResponse<List<AiResponse>> getChatsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<AiChat> logs = aiRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return buildListResponse("History fetched successfully", logs, AiMapper::toResponse);
    }

    @Transactional
    public ApiResponse<Void> deleteChatsByUserId(Long userId) {
        aiRepository.deleteByUserId(userId);
        return ApiResponse.ok("Deleted chats for user " + userId + " successfully", null);
    }

}
