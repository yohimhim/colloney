package colloney.company;

public class CompanyMapper {
    public static Company toEntity(CompanyDto company, String symbol) {
        return Company.builder()
      // @formatter:off
      .symbol(symbol)
      .name(company.getName())
      .country(company.getCountry())
      .exchange(company.getExchange())
      .currency(company.getCurrency())
      .industry(company.getFinnhubIndustry())
      .ipo(company.getIpo())
      .logo(company.getLogo())
      .marketCapitalization(company.getMarketCapitalization())
      .phone(company.getPhone())
      .shareOutstanding(company.getShareOutstanding())
      .weburl(company.getWeburl())
      .build();
      // @formatter:on
    }

    public static CompanyResponse toResponse(Company company) {
        return CompanyResponse.builder()
      // @formatter:off
      .symbol(company.getSymbol())
      .name(company.getName())
      .country(company.getCountry())
      .exchange(company.getExchange())
      .currency(company.getCurrency())
      .industry(company.getIndustry())
      .ipo(company.getIpo())
      .logo(company.getLogo())
      .marketCapitalization(company.getMarketCapitalization())
      .phone(company.getPhone())
      .shareOutstanding(company.getShareOutstanding())
      .weburl(company.getWeburl())
      .build();
      // @formatter:on
    }
}
