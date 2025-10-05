package com.example.jpa_entries.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (not needed for APIs)
                .csrf(csrf -> csrf.disable())

                // Allow unauthenticated access to public endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated())

                // Use HTTP Basic auth (works with Postman)
                .httpBasic(Customizer.withDefaults())

                // Disable the HTML login form completely
                .formLogin(form -> form.disable())

                // Disable logout redirect (optional)
                .logout(logout -> logout.disable());

        return http.build();
    }
}
