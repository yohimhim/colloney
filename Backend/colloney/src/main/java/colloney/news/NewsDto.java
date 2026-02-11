package colloney.news;

import lombok.Data;

@Data
public class NewsDto {
    private Long id;
    private String category;
    private Long datetime;
    private String headline;
    private String image;
    private String related;
    private String source;
    private String summary;
    private String url;
}
