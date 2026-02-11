package colloney.news;

import java.time.Instant;

public class NewsMapper {
    public static News toEntity(NewsDto news) {
        return News.builder()
        // @formatter:off
        .id(news.getId())
        .category(news.getCategory())
        .datetime(Instant.ofEpochSecond(news.getDatetime()))
        .headline(news.getHeadline())
        .image(news.getImage())
        .related(news.getRelated())
        .source(news.getSource())
        .summary(news.getSummary())
        .url(news.getUrl())
        .build();
        // @formatter:on
    }

    public static NewsResponse toResponse(News news) {
        return NewsResponse.builder()
        // @formatter:off
        .id(news.getId())
        .category(news.getCategory())
        .headline(news.getHeadline())
        .datetime(news.getDatetime())
        .image(news.getImage())
        .related(news.getRelated())
        .source(news.getSource())
        .summary(news.getSummary())
        .url(news.getUrl())
        .build();
        // @formatter:on
    }
}
