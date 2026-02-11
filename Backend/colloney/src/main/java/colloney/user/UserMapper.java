package colloney.user;

public class UserMapper {
    public static User toEntity(UserRequest request) {
        return User.builder()
      // @formatter:off
      .username(request.getUsername())
      .email(request.getEmail())
      .password(request.getPassword())
      .build();
    // @formatter:on
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
      // @formatter:off
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .role(user.getRole())
      .build();
    // @formatter:on
    }
}
