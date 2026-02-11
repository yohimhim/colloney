package colloney.budgeting.budget;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateBudgetRequest {
    private Long categoryId;
    private String name;
    private double limitAmount;
    private LocalDate startDate;
    private LocalDate endDate;
}
