package colloney.user.financialdata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import colloney.util.ControllerUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/{userId}/financialData")
@RequiredArgsConstructor
public class FinancialDataController {
    private final FinancialDataService financialDataService;

    @GetMapping
    public ResponseEntity<ApiResponse<FinancialDataResponse>> getFinancialData(@PathVariable Long userId) {
        return ControllerUtils.wrapResponse(financialDataService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FinancialDataResponse>> createUserFinancialData(@PathVariable Long userId,
            @RequestBody FinancialData financialData) {
        return ControllerUtils.wrapResponse(financialDataService.createUserFinancialData(userId, financialData));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<FinancialDataResponse>> updateUserFinancialData(@PathVariable Long userId,
            @RequestBody FinancialData financialData) {
        return ControllerUtils.wrapResponse(financialDataService.updateUserFinancialData(userId, financialData));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<FinancialDataResponse>> deleteUserFinancialData(@PathVariable Long userId) {
        return ControllerUtils.wrapResponse(financialDataService.deleteUserFinancialData(userId));
    }
}
