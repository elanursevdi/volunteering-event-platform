package com.elanur_sude.gonulluluk_proje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Şifre hashleme için PasswordEncoder türünde bir bean tanımlandı.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Tüm istekler serbest (şimdilik login/register test için)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF korumasını kapatır
            .authorizeHttpRequests(auth -> auth //Tüm istekleri doğrulama olmadan (oturum/rol kontrolü olmadan)kabul eder.
                .anyRequest().permitAll() // bütün endpoint'ler serbest
            );

        return http.build();
    }
}
