package colloney.stock.trade;

public class PositionMapper {

    public static PositionResponse toResponse(Position position, String name, double currentPrice, double priceChange,
            double percentChange, String logoUrl) {

        double marketValue = position.getTotalShares() * currentPrice;
        double unrealizedGain = marketValue - position.getTotalCost();

        return PositionResponse.builder()
        // @formatter:off
            .symbol(position.getSymbol())
            .name(name)
            .totalShares(position.getTotalShares())
            .averageCost(position.getAverageCost())
            .totalCost(position.getTotalCost())
            .currentPrice(currentPrice)
            .priceChange(priceChange)
            .percentChange(percentChange)
            .marketValue(marketValue)
            .unrealizedGain(unrealizedGain)
            .logoUrl(logoUrl)
            .lastUpdated(position.getLastUpdated())
            .build();
            // @formatter:on
    }
}
