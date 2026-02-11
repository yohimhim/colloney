package colloney.posts;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import colloney.user.User;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return buildListResponse("Posts retrieved successfully", posts, PostMapper::toResponse);
    }

    public ApiResponse<PostResponse> createPost(PostRequest postRequest) {
        User author = userRepository.findById(postRequest.getAuthorId()).orElseThrow(
                () -> new EntityNotFoundException("User with id " + postRequest.getAuthorId() + " not found"));

        Post post = PostMapper.toEntity(postRequest, author);
        Post saved = postRepository.save(post);

        PostSocket.broadcastUpdatedPosts();
        return ApiResponse.created("Post created successfully", PostMapper.toResponse(saved));
    }

    @Transactional
    public ApiResponse<PostResponse> deleteOwnPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        postRepository.delete(post);

        PostSocket.broadcastUpdatedPosts();
        return ApiResponse.ok("Post deleted successfully", PostMapper.toResponse(post));
    }

    @Transactional
    public ApiResponse<PostResponse> updateOwnPost(Long postId, Long userId, String newContent) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        if (!post.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("You can only edit your own posts.");
        }

        Post updatedPost = post.toBuilder()
        // @formatter:off
                .content(newContent)
                .build();
        // @formatter:on
        postRepository.save(updatedPost);

        PostSocket.broadcastUpdatedPosts();
        return ApiResponse.ok("Post updated successfully", PostMapper.toResponse(updatedPost));
    }

}
