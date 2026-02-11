package colloney.company;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Companies", description = "Endpoints for retrieving company information")
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // @formatter:off
    @Operation(
        summary = "Get active companies",
        description = """
            Returns companies that are currently considered *active* by the system.

            Active companies are those whose stock symbols are currently being tracked
            and for which the system is fetching live stock quotes. Only symbols that
            match the configured exchanges and the 'Common Stock' type are included.
            """,
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of active tracked companies",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListCompanyResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getActiveCompanies() {
        return wrapResponse(companyService.getActiveCompanies());
    }

    // @formatter:off
    @Operation(
        summary = "Get all companies",
        description = "Returns every company stored in the database, regardless of tracking status.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of all companies",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListCompanyResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAllCompanies() {
        return wrapResponse(companyService.getAllCompanies());
    }
}
