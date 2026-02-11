package colloney.stock.quote;

import java.time.Instant;

import colloney.stock.symbol.Symbol;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_quotes", indexes = {
        @Index(name = "idx_symbol_quote_timestamp", columnList = "symbol, quote_timestamp") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(QuoteId.class)
@Builder
public class Quote {

    @Id
    private String symbol;

    @Id
    private Instant quoteTimestamp;

    private double currentPrice;
    private double priceChange;
    private double percentChange;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double previousClosePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    private Symbol symbolEntity;
}
