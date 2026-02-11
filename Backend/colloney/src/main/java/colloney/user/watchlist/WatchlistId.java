package colloney.user.watchlist;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistId implements Serializable {
    private Long userId;
    private String symbol;
}
