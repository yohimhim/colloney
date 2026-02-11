package colloney.user.financialdata;

public class FinancialDataMapper {

    public static FinancialDataResponse toResponse(FinancialData data) {
        return FinancialDataResponse.builder()
        // @formatter:off
            .userId(data.getUserId())
            .buyingPowerBalance(data.getBuyingPowerBalance())
            .school(data.getSchool())
            .residencyStatus(data.getResidencyStatus())
            .housingStatus(data.getHousingStatus())
            .otherExpenses(data.getOtherExpenses())
            .savingsGoal(data.getSavingsGoal())
            .savingsGoalDueDate(data.getSavingsGoalDueDate())
            .scholarships(data.getScholarships())
            .schoolType(data.getSchoolType())
            .build();
        // @formatter:on
    }

}
