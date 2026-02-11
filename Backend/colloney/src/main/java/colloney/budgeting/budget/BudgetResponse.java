package colloney.budgeting.budget;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private double limitAmount;
    private double spentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
