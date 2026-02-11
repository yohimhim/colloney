package colloney.stock.symbol;

public class SymbolMapper {
    public static Symbol toEntity(SymbolDto symbol) {
        return Symbol.builder()
      // @formatter:off
      .symbol(symbol.getSymbol())
      .currency(symbol.getCurrency())
      .name(symbol.getDescription())
      .displaySymbol(symbol.getDisplaySymbol())
      .figi(symbol.getFigi())
      .exchange(symbol.getMic())
      .shareClassFigi(symbol.getShareClassFIGI())
      .symbol2(symbol.getSymbol2())
      .type(symbol.getType())
      .build();
      // @formatter:on
    }

    public static SymbolResponse toResponse(Symbol symbol) {
        return SymbolResponse.builder()
        // @formatter:off
        .symbol(symbol.getSymbol())
        .name(symbol.getName())
        .exchange(symbol.getExchange())
        .type(symbol.getType())
        .build();
        // @formatter:on
    }
}
