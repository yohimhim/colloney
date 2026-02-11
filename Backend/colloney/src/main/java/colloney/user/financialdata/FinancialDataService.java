package colloney.user.financialdata;

import static colloney.user.financialdata.FinancialDataMapper.toResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import colloney.user.User;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialDataService {
    private final FinancialDataRepository financialDataRepository;
    private final UserRepository userRepository;

    public ApiResponse<FinancialDataResponse> getByUserId(Long userId) {
        FinancialData data = financialDataRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Financial data not found for userId " + userId));

        return ApiResponse.ok("Financial data retrieved successfully", toResponse(data));
    }

    @Transactional
    public ApiResponse<FinancialDataResponse> createUserFinancialData(Long userId, FinancialData body) {
        if (financialDataRepository.existsById(userId)) {
            throw new DataIntegrityViolationException("Financial data already exists for userId " + userId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        body.setUser(user);

        FinancialData saved = financialDataRepository.save(body);

        return ApiResponse.created("Financial data created successfully", toResponse(saved));
    }

    public ApiResponse<FinancialDataResponse> updateUserFinancialData(Long userId, FinancialData body) {
        financialDataRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Financial data not found for userId " + userId));

        FinancialData updated = FinancialData.builder()
        // @formatter:off
            .userId(userId)
            .user(body.getUser())
            .buyingPowerBalance(body.getBuyingPowerBalance())
            .school(body.getSchool())
            .residencyStatus(body.getResidencyStatus())
            .housingStatus(body.getHousingStatus())
            .otherExpenses(body.getOtherExpenses())
            .savingsGoal(body.getSavingsGoal())
            .savingsGoalDueDate(body.getSavingsGoalDueDate())
            .scholarships(body.getScholarships())
            .schoolType(body.getSchoolType())
            .build();
        // @formatter:on
        FinancialData saved = financialDataRepository.save(updated);

        return ApiResponse.ok("Financial data has been updated for userId " + userId, toResponse(saved));
    }

    public ApiResponse<FinancialDataResponse> deleteUserFinancialData(Long userId) {
        FinancialData existing = financialDataRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Financial data not found for userId " + userId));

        financialDataRepository.delete(existing);

        return ApiResponse.ok("UserId " + userId + "'s Financial data has been deleted", toResponse(existing));
    }

}
