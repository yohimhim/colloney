package colloney.news;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public ApiResponse<List<NewsResponse>> getAllNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datetime")));
        Page<News> newsPage = newsRepository.findAllPrioritized(pageable);
        return buildListResponse("News retrieved successfully", newsPage, NewsMapper::toResponse);
    }

    public ApiResponse<List<NewsResponse>> getNewsBySymbol(String symbol, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datetime")));
        Page<News> newsPage = newsRepository.findByRelatedIgnoreCase(symbol, pageable);
        if (newsPage.isEmpty()) {
            throw new EntityNotFoundException("No news found for symbol " + symbol);
        }
        return buildListResponse("News retrieved successfully", newsPage, NewsMapper::toResponse);
    }

    public ApiResponse<List<NewsResponse>> getNewsBySource(String source, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datetime")));
        Page<News> newsPage = newsRepository.findBySourceIgnoreCase(source, pageable);
        if (newsPage.isEmpty()) {
            throw new EntityNotFoundException("No news found for source " + source);
        }
        return buildListResponse("News retrieved successfully", newsPage, NewsMapper::toResponse);
    }

    public ApiResponse<List<NewsResponse>> getNewsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datetime")));
        Page<News> newsPage = newsRepository.findByCategoryIgnoreCase(category, pageable);
        if (newsPage.isEmpty()) {
            throw new EntityNotFoundException("No news found for category " + category);
        }
        return buildListResponse("News retrieved successfully", newsPage, NewsMapper::toResponse);
    }
}
