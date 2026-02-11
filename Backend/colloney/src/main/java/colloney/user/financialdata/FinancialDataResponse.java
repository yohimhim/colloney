package colloney.user.financialdata;

import java.math.BigDecimal;
import java.time.LocalDate;

import colloney.user.financialdata.enums.HousingStatus;
import colloney.user.financialdata.enums.ResidencyStatus;
import colloney.user.financialdata.enums.SchoolType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinancialDataResponse {

    private Long userId;
    private BigDecimal buyingPowerBalance;
    private String school;
    private ResidencyStatus residencyStatus;
    private HousingStatus housingStatus;
    private BigDecimal otherExpenses;
    private BigDecimal savingsGoal;
    private LocalDate savingsGoalDueDate;
    private BigDecimal scholarships;
    private SchoolType schoolType;

}
