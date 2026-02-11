package colloney.posts;

import colloney.user.User;

public class PostMapper {
    public static Post toEntity(PostRequest request, User author) {
        return Post.builder()
        // @formatter:off
            .author(author)
            .content(request.getContent())
            .build();
        // @formatter:on
    }

    public static PostResponse toResponse(Post post) {
        return PostResponse.builder().id(post.getId())
        // @formatter:off
            .author(
            UserSummary.builder()
                .id(post.getAuthor().getId())
                .username(post.getAuthor().getUsername())
                .build())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .build();
        // @formatter:on
    }
}
