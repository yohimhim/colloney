package colloney.user.watchlist;

public class WatchlistMapper {
    public static WatchlistResponse toResponse(Watchlist watchlist) {
        return WatchlistResponse.builder()
      // @formatter:off
      .userId(watchlist.getUserId())
      .symbol(watchlist.getSymbol())
      .build();
    // @formatter:on
    }
}
