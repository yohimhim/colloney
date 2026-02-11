package colloney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import colloney.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeHttpRequests(authorizeRequests -> authorizeRequests
        // .requestMatchers("/**").authenticated()
        // .requestMatchers(HttpMethod.POST, "/users").permitAll()
        // .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().permitAll()
      )
      .httpBasic(Customizer.withDefaults())
      .formLogin(Customizer.withDefaults())
      // .logout(logout -> logout
      //   .logoutUrl("/logout")
      //   .logoutSuccessUrl("/login?logout")
      //   .invalidateHttpSession(true)
      //   .clearAuthentication(true)
      // )
      .logout(Customizer.withDefaults())
      .headers(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable());
    // @formatter:on
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    // https://github.com/spring-projects/spring-security/issues/13652
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
