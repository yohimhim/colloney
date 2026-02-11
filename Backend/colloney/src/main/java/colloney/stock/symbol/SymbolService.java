package colloney.stock.symbol;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import colloney.util.ApiResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolService {
    private final SymbolRepository symbolRepository;

    @Value("#{'${finnhub.stocks.exchanges}'.split(',')}")
    private List<String> exchanges;

    @Value("#{'${finnhub.stocks.types}'.split(',')}")
    private List<String> types;

    public ApiResponse<List<SymbolResponse>> getActiveSymbols() {
        List<Symbol> activeSymbols = symbolRepository.findByExchangeInAndTypeIn(exchanges, types);
        return buildListResponse("Symbols retrieved successfully", activeSymbols, SymbolMapper::toResponse);
    }

    public ApiResponse<List<SymbolResponse>> getAllSymbols() {
        List<Symbol> symbols = symbolRepository.findAll();
        return buildListResponse("Symbols retrieved successfully", symbols, SymbolMapper::toResponse);
    }
}
