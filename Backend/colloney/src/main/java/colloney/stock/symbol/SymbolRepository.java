package colloney.stock.symbol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, String> {
    List<Symbol> findByExchangeInAndTypeIn(List<String> exchanges, List<String> types);

    List<Symbol> findByTypeIn(List<String> types);

    boolean existsBySymbol(String symbol);

    @Query("SELECT s.symbol FROM Symbol s")
    List<String> findAllSymbols();

}
