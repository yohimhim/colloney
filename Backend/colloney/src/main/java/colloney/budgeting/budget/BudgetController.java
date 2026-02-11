package colloney.budgeting.budget;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import colloney.util.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getBudgetsForUser(@PathVariable Long userId) {
        return wrapResponse(budgetService.getBudgetsForUser(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<BudgetResponse>> createBudget(@PathVariable Long userId,
            @RequestBody CreateBudgetRequest request) {
        return wrapResponse(budgetService.createBudget(userId, request));
    }

    @PatchMapping("/{budgetId}/spent")
    public ResponseEntity<ApiResponse<BudgetResponse>> updateBudgetSpent(@PathVariable Long budgetId,
            @RequestBody UpdateBudgetSpentRequest request) {
        return wrapResponse(budgetService.updateBudgetSpent(budgetId, request));
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<ApiResponse<Void>> deleteBudget(@PathVariable Long budgetId) {

        return wrapResponse(budgetService.deleteBudget(budgetId));

    }

}
