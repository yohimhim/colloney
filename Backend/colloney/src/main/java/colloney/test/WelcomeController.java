package colloney.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test", description = "Basic test and welcome endpoints")
@RestController
public class WelcomeController {

    @Operation(summary = "Welcome message", description = "Returns a simple HTML welcome message and link to the Swagger UI.")
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Colloney! Explore the API through <a href='/swagger-ui.html'>Swagger UI</a>.";
    }
}
