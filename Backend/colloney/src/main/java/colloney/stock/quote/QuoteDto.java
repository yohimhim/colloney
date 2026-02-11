package colloney.stock.quote;

import lombok.Data;

@Data
public class QuoteDto {
    private double c; // current price
    private double d; // change
    private double dp; // percent change
    private double h; // high price
    private double l; // low price
    private double o; // open price
    private double pc; // previous close price
    private long t; // timestamp (epoch seconds)
}
