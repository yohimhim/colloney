package colloney.stock.trade;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionResponse {

    private String symbol;
    private String name;
    private int totalShares;
    private double averageCost;
    private double totalCost;
    private double currentPrice;
    private double priceChange;
    private double percentChange;
    private double marketValue;
    private double unrealizedGain;
    private String logoUrl;
    private Instant lastUpdated;
}
