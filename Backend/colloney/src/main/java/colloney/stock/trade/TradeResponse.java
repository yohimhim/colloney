package colloney.stock.trade;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeResponse {

    private Long tradeId;

    private String symbol;
    private int quantity;
    private double price;
    private double totalValue;
    private double realizedGain;
    private TradeType tradeType;
    private Instant timestamp;
}
