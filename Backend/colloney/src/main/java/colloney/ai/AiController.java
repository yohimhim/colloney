package colloney.ai;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import colloney.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<AiResponse>> generate(@RequestParam Long userId,
            @Valid @RequestBody AiRequest request) {
        return wrapResponse(aiService.generate(userId, request));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<ApiResponse<List<AiResponse>>> getChatsByUserId(@PathVariable Long userId) {
        return wrapResponse(aiService.getChatsByUserId(userId));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteChatsByUserId(@PathVariable Long userId) {
        return wrapResponse(aiService.deleteChatsByUserId(userId));
    }
}
