package book.loan.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "api/v1/books/find/{title}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"api/v1/books/loan/find").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "api/v1/books").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "api/v1/books/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/books/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/books/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/books/loan/return/book").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "api/v1/books/loan/rent").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "api/v1/books/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/books/delete/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

