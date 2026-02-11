package colloney.budgeting.category;

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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponse<CategoryResponse> createCategory(Long userId, CategoryRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Category category = CategoryMapper.toEntity(request, user);
        Category saved = categoryRepository.save(category);

        CategoryResponse response = CategoryMapper.toResponse(saved);

        return ApiResponse.ok("Category created successfully", response);
    }

    public ApiResponse<List<CategoryResponse>> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return buildListResponse("All categories retrieved successfully", categories, CategoryMapper::toResponse);
    }

    public ApiResponse<List<CategoryResponse>> getCategoriesForUser(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        List<Category> categories = categoryRepository.findByUser_Id(userId);

        return buildListResponse("User categories retrieved successfully", categories, CategoryMapper::toResponse);
    }

    @Transactional
    public ApiResponse<Void> deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + categoryId));

        categoryRepository.delete(category);

        return ApiResponse.ok("Category deleted successfully", null);
    }

}
