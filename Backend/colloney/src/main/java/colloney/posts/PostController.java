package colloney.posts;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Posts", description = "Endpoints for managing posts")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // @formatter:off
    @Operation(
        summary = "Get all posts",
        description = "Retrieve a list of all posts.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of posts",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListPostResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts() {
        return wrapResponse(postService.getAllPosts());
    }

    // @formatter:off
    @Operation(
        summary = "Create a new post",
        description = "Create a new post authored by a user.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the created post",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponsePostResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest postRequest) {
        return wrapResponse(postService.createPost(postRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Delete a post",
        description = "Delete a post authored by the current user.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the deleted post",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponsePostResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> deleteOwnPost(@PathVariable Long postId) {
        return wrapResponse(postService.deleteOwnPost(postId));
    }

    // @formatter:off
    @Operation(
        summary = "Update a post",
        description = "Update the content of a post authored by the current user.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the updated post",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponsePostResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updateOwnPost(@PathVariable Long postId,
            @Valid @RequestBody PostRequest request) {
        return wrapResponse(postService.updateOwnPost(postId, request.getAuthorId(), request.getContent()));
    }

}
