package com.ankarabt.stajyonetim.core.config;

import com.ankarabt.stajyonetim.core.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //HERKESE AÇIK
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/error").permitAll()

                        .requestMatchers("/api/auth/**").permitAll()

                        //ROL BAZLI KISITLAMALAR
                        .requestMatchers("/api/kurumlar/**", "/api/loglar/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_KURUMYONETICI")

                        // Sadece Akademisyen veya Mentör sisteme materyal ekleyebilir veya staj raporunu onaylayabilir
                        .requestMatchers("/api/materyaller/**").hasAnyAuthority("ROLE_AKADEMISYEN", "ROLE_MENTOR")
                        .requestMatchers("/api/raporlar/*/sirket-onayla", "/api/raporlar/*/akademisyen-onayla").hasAnyAuthority("ROLE_AKADEMISYEN", "ROLE_MENTOR")

                        // Stajyer kendi raporunu yazabilir ama başkasınınkine karışamaz (İleride JWT ile ID eşleşmesi de yapacağız)
                        .requestMatchers(HttpMethod.POST, "/api/raporlar").hasAuthority("ROLE_STAJYER")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}