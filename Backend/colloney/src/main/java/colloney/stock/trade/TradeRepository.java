package colloney.stock.trade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByAccountIdOrderByTimestampDesc(Long accountId);
}
