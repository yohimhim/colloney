package colloney.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a company returned from company endpoints.")
public class CompanyResponse {

    @Schema(description = "Stock symbol of the company", example = "AAPL")
    private String symbol;

    @Schema(description = "Country where the company is headquarted", example = "US")
    private String country;

    @Schema(description = "Currency used for stock prices", example = "USD")
    private String currency;

    @Schema(description = "Stock exchange where the company is listed", example = "NASDAQ NMS - GLOBAL MARKET")
    private String exchange;

    @Schema(description = "Industry sector of the company", example = "Technology")
    private String industry;

    @Schema(description = "IPO date of the company", example = "1980-12-12")
    private String ipo;

    @Schema(description = "URL to the company logo", example = "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.png")
    private String logo;

    @Schema(description = "Market capitalization of the company", example = "4025226.3751850543")
    private double marketCapitalization;

    @Schema(description = "Full name of the company", example = "Apple Inc")
    private String name;

    @Schema(description = "Company phone number", example = "14089961010")
    private String phone;

    @Schema(description = "Number of shares outstanding", example = "14840.39")
    private double shareOutstanding;

    @Schema(description = "Company website URL", example = "https://www.apple.com/")
    private String weburl;
}
