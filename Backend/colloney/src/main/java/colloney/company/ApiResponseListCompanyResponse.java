package colloney.company;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseCompanyList", description = "API wrapper for a list of companies")
public class ApiResponseListCompanyResponse extends ApiResponse<List<CompanyResponse>> {
}
