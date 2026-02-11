package colloney.budgeting.budget;

import colloney.budgeting.category.Category;
import colloney.user.User;

public class BudgetMapper {

    public static Budget toEntity(CreateBudgetRequest request, Category category, User user) {
        return Budget.builder()
        // @formatter:off
            .user(user)
            .category(category)
            .name(request.getName())
            .limitAmount(request.getLimitAmount())
            .spentAmount(0.0)
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build();
        // @formatter:on
    }

    public static BudgetResponse toResponse(Budget budget) {

        return BudgetResponse.builder()
        // @formatter:off
            .id(budget.getId())
            .categoryId(budget.getCategory().getId())
            .categoryName(budget.getCategory().getName())
            .name(budget.getName())
            .limitAmount(budget.getLimitAmount())
            .spentAmount(budget.getSpentAmount())
            .startDate(budget.getStartDate())
            .endDate(budget.getEndDate())
            .build();
        // @formatter:on
    }

}
