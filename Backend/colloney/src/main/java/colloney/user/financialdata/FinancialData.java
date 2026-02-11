package colloney.user.financialdata;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import colloney.user.User;
import colloney.user.financialdata.enums.HousingStatus;
import colloney.user.financialdata.enums.ResidencyStatus;
import colloney.user.financialdata.enums.SchoolType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_financial_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FinancialData {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal buyingPowerBalance;

    @Column(nullable = false)
    private String school;

    @Enumerated(EnumType.STRING)
    private ResidencyStatus residencyStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HousingStatus housingStatus;

    @Column(precision = 18, scale = 2)
    private BigDecimal otherExpenses;

    @Column(precision = 18, scale = 2)
    private BigDecimal savingsGoal;

    // JSON Value: YYYY-MM-DD
    private LocalDate savingsGoalDueDate;

    @Column(precision = 18, scale = 2)
    private BigDecimal scholarships;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType schoolType;

}
