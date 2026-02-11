package colloney.budgeting.category;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import colloney.util.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@PathVariable Long userId,
            @RequestBody CategoryRequest request) {

        return wrapResponse(categoryService.createCategory(userId, request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return wrapResponse(categoryService.getAllCategories());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesForUser(@PathVariable Long userId) {
        return wrapResponse(categoryService.getCategoriesForUser(userId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        return wrapResponse(categoryService.deleteCategory(categoryId));
    }

}
