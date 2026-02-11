package colloney.stock.trade;

import java.time.Instant;

import colloney.banking.account.Account;

public class TradeMapper {

    public static Trade toBuyEntity(Account account, double price, TradeRequest request) {
        double total = price * request.getQuantity();

        return Trade.builder()
        // @formatter:off
            .account(account)
            .symbol(request.getSymbol())
            .quantity(request.getQuantity())
            .price(price)
            .totalValue(total)
            .realizedGain(0)
            .tradeType(TradeType.BUY)
            .timestamp(Instant.now())
            .build();
            // @formatter:on
    }

    public static Trade toSellEntity(Account account, double price, TradeRequest request, double realized) {
        double total = price * request.getQuantity();

        return Trade.builder()
        // @formatter:off
            .account(account)
            .symbol(request.getSymbol())
            .quantity(request.getQuantity())
            .price(price)
            .totalValue(total)
            .realizedGain(realized)
            .tradeType(TradeType.SELL)
            .timestamp(Instant.now())
            .build();
            // @formatter:off
    }

    public static TradeResponse toResponse(Trade trade) {
        return TradeResponse.builder()
            // @formatter:off
            .tradeId(trade.getId())
            .symbol(trade.getSymbol())
            .quantity(trade.getQuantity())
            .price(trade.getPrice())
            .totalValue(trade.getTotalValue())
            .realizedGain(trade.getRealizedGain())
            .tradeType(trade.getTradeType())
            .timestamp(trade.getTimestamp())
            .build();
            // @formatter:on
    }
}
