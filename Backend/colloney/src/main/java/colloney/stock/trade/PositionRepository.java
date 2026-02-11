package colloney.stock.trade;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByAccountId(Long accountId);

    Optional<Position> findByAccountIdAndSymbol(Long accountId, String symbol);
}
