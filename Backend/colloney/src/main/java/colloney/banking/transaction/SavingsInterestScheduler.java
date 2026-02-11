package colloney.banking.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class SavingsInterestScheduler {
    @Value("${banking.savings.interest-rate}")
    private double interestRate;

    private final TransactionService transactionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void runDailyInterest() {
        double dailyRate = interestRate / 365;
        transactionService.applyInterest(dailyRate);
        log.info("Applied daily interest to all savings accounts");
    }
}
