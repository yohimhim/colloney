package colloney.budgeting.category;

import colloney.user.User;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request, User user) {
        return Category.builder()
        // @formatter:off
            .name(request.getName())
            .icon(request.getIcon())
            .user(user)
            .build();
        // @formatter:on
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
        // @formatter:off
                .userId(category.getUser().getId())
                .categoryId(category.getId())
                .name(category.getName())
                .icon(category.getIcon())
                .build();
        // @formatter:on
    }

}
