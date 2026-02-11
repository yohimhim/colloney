package colloney.user.watchlist;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import colloney.stock.symbol.SymbolRepository;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final SymbolRepository symbolRepository;

    public ApiResponse<List<WatchlistDetailResponse>> getUserWatchlist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }
        List<WatchlistDetailResponse> watchlistDetailResponse = watchlistRepository
                .findWatchlistWithDetailsByUserId(userId);
        return ApiResponse.ok("Watchlist retrieved successfully", watchlistDetailResponse);
    }

    @Transactional
    public ApiResponse<WatchlistResponse> addToWatchlist(Long userId, WatchlistRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }

        String normalizedSymbol = request.getSymbol().toUpperCase();

        if (!symbolRepository.existsBySymbol(normalizedSymbol)) {
            throw new EntityNotFoundException("Symbol '" + normalizedSymbol + "' not found");
        }

        if (watchlistRepository.findByUserIdAndSymbol(userId, normalizedSymbol).isPresent()) {
            throw new DataIntegrityViolationException("Symbol '" + normalizedSymbol + "' already in watchlist");
        }

        Watchlist saved = watchlistRepository.save(Watchlist.builder()
        // @formatter:off
        .userId(userId)
        .symbol(normalizedSymbol)
        .build());
        // @formatter:on
        return ApiResponse.created("Symbol added to watchlist", WatchlistMapper.toResponse(saved));
    }

    @Transactional
    public ApiResponse<WatchlistResponse> removeFromWatchlist(Long userId, String symbol) {
        String normalizedSymbol = symbol.toUpperCase();
        Watchlist entry = watchlistRepository.findByUserIdAndSymbol(userId, normalizedSymbol).orElseThrow(
                () -> new EntityNotFoundException("Symbol '" + normalizedSymbol + "' not found in watchlist"));

        watchlistRepository.delete(entry);

        return ApiResponse.ok("Symbol removed from watchlist", WatchlistMapper.toResponse(entry));
    }

    public String getMostWatchedSymbol() {
        List<Object[]> result = watchlistRepository.findMostWatchedSymbol();
        if (result.isEmpty()) {
            return null;
        }

        return (String) result.get(0)[0];
    }
}
