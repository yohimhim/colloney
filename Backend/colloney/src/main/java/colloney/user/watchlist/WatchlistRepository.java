package colloney.user.watchlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, WatchlistId> {
    List<Watchlist> findByUserId(Long userId);

    Optional<Watchlist> findByUserIdAndSymbol(Long userId, String symbol);

    void deleteByUserId(Long userId);

    @Query("SELECT w.symbol, COUNT(w.symbol) FROM Watchlist w GROUP BY w.symbol ORDER BY COUNT(w.symbol) DESC")
    List<Object[]> findMostWatchedSymbol();

    @Query("""
                SELECT new colloney.user.watchlist.WatchlistDetailResponse(
                    w.userId, w.symbol, s.name, q.currentPrice, q.priceChange, q.percentChange, c.logo
                )
                FROM Watchlist w
                JOIN LatestQuote q ON q.symbol = w.symbol
                JOIN Symbol s ON s.symbol = w.symbol
                LEFT JOIN Company c ON c.symbol = w.symbol
                WHERE w.userId = :userId
            """)
    List<WatchlistDetailResponse> findWatchlistWithDetailsByUserId(@Param("userId") Long userId);

}
