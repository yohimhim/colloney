package colloney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ColloneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColloneyApplication.class, args);
    }

}
