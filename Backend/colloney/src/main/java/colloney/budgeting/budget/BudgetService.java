package colloney.budgeting.budget;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import colloney.budgeting.category.Category;
import colloney.budgeting.category.CategoryRepository;
import colloney.user.User;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ApiResponse<List<BudgetResponse>> getBudgetsForUser(Long userId) {
        List<Budget> budgets = budgetRepository.findByUser_Id(userId);

        return buildListResponse("Budgets retrieved successfully", budgets, BudgetMapper::toResponse);
    }

    @Transactional
    public ApiResponse<BudgetResponse> createBudget(Long userId, CreateBudgetRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found: " + userId));

        Category category = categoryRepository.findByIdAndUser_Id(request.getCategoryId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found for this user"));

        Budget budget = BudgetMapper.toEntity(request, category, user);
        Budget saved = budgetRepository.save(budget);

        return ApiResponse.ok("Budget created successfully", BudgetMapper.toResponse(saved));
    }

    @Transactional
    public ApiResponse<BudgetResponse> updateBudgetSpent(Long budgetId, UpdateBudgetSpentRequest request) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found: " + budgetId));

        double spent = budget.getSpentAmount() + request.getSpentAmount();

        budget.setSpentAmount(spent);
        Budget saved = budgetRepository.save(budget);

        return ApiResponse.ok("Budget spent amount updated successfully", BudgetMapper.toResponse(saved));
    }

    @Transactional
    public ApiResponse<Void> deleteBudget(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found: " + budgetId));

        budgetRepository.delete(budget);

        return ApiResponse.ok("Budget deleted successfully", null);
    }

}
