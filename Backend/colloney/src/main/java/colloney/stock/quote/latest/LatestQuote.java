package colloney.stock.quote.latest;

import java.time.Instant;

import colloney.stock.symbol.Symbol;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "latest_quotes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LatestQuote {
    @Id
    private String symbol;

    private Instant quoteTimestamp;
    private double currentPrice;
    private double priceChange;
    private double percentChange;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double previousClosePrice;

    @OneToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    private Symbol symbolEntity;
}
