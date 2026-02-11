package colloney.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
@Builder
public class Company {

    @Id
    private String symbol;

    private String name;
    private String exchange;
    private String currency;
    private String country;
    private String industry;
    private String ipo;
    private String logo;
    private double marketCapitalization;
    private String phone;
    private double shareOutstanding;
    private String weburl;
}
