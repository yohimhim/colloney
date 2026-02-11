package colloney.stock.quote;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteId implements Serializable {
    private String symbol;
    private Instant quoteTimestamp;
}
