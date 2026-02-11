package colloney.news;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "News", description = "Endpoints for retrieving news articles")
@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    // @formatter:off
    @Operation(
        summary = "Get all news",
        description = "Retrieve all news articles.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of news articles",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListNewsResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getAllNews(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return wrapResponse(newsService.getAllNews(page, size));
    }

    // @formatter:off
    @Operation(
        summary = "Get news by symbol",
        description = "Retrieve news articles related to a specific stock symbol.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of news articles for the symbol",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListNewsResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getNewsBySymbol(@PathVariable String symbol,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return wrapResponse(newsService.getNewsBySymbol(symbol, page, size));
    }

    // @formatter:off
    @Operation(
        summary = "Get news by source",
        description = "Retrieve news articles from a specific source.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of news articles for the source",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListNewsResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/source/{source}")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getNewsBySource(@PathVariable String source,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return wrapResponse(newsService.getNewsBySource(source, page, size));
    }

    // @formatter:off
    @Operation(
        summary = "Get news by category",
        description = "Retrieve news articles for a specific category.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of news articles for the category",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListNewsResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getNewsByCategory(@PathVariable String category,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return wrapResponse(newsService.getNewsByCategory(category, page, size));
    }
}
