package colloney.stock.symbol;

import java.util.List;

import colloney.user.watchlist.Watchlist;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_symbols")
@Builder
public class Symbol {

    @Id
    private String symbol;

    private String currency;
    private String name;
    private String displaySymbol;
    private String figi;
    private String isin;
    private String exchange;
    private String shareClassFigi;
    private String symbol2;
    private String type;

    @OneToMany(mappedBy = "symbolEntity", fetch = FetchType.LAZY)
    private List<Watchlist> watchlists;
}
