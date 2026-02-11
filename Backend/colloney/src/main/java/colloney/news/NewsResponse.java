package colloney.news;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representation of a news article")
public class NewsResponse {

    @Schema(description = "Unique ID of the news article", example = "137479983")
    private Long id;

    @Schema(description = "Category of the news", example = "company")
    private String category;

    @Schema(description = "Publication datetime", example = "2025-11-14T16:09:22Z")
    private Instant datetime;

    @Schema(description = "Headline of the news article", example = "Apple Sees 22% Jump in China iPhone Sales After iPhone 17 Launch")
    private String headline;

    @Schema(description = "URL to the news image", example = "https://s.yimg.com/rz/stage/p/yahoo_finance_en-US_h_p_finance_2.png")
    private String image;

    @Schema(description = "Related stock symbol", example = "AAPL")
    private String related;

    @Schema(description = "Source of the news", example = "Yahoo")
    private String source;

    @Schema(description = "Short summary of the article", example = "Strong demand contrasts with overall market slowdown as local rivals intensify competition.")
    private String summary;

    @Schema(description = "URL to the full news article", example = "https://finnhub.io/api/news?id=c7e493fb33fa87e0f00552d5e081a952f7975777c820c9ea33927aaf7e9889fa")
    private String url;
}
