package colloney.company;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import colloney.stock.symbol.Symbol;
import colloney.stock.symbol.SymbolRepository;
import colloney.util.ApiResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final SymbolRepository symbolRepository;

    @Value("#{'${finnhub.stocks.types}'.split(',')}")
    private List<String> types;

    @Value("#{'${finnhub.stocks.exchanges}'.split(',')}")
    private List<String> exchanges;

    public ApiResponse<List<CompanyResponse>> getActiveCompanies() {
        List<Symbol> activeSymbols = symbolRepository.findByExchangeInAndTypeIn(exchanges, List.of("Common Stock"));

        List<String> symbolIds = new ArrayList<>();
        for (Symbol symbol : activeSymbols) {
            symbolIds.add(symbol.getSymbol());
        }

        List<Company> companies = companyRepository.findAllById(symbolIds);
        return buildListResponse("Companies retrieved successfully", companies, CompanyMapper::toResponse);
    }

    public ApiResponse<List<CompanyResponse>> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return buildListResponse("Companies retrieved successfully", companies, CompanyMapper::toResponse);
    }
}
