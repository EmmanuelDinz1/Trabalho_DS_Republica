package com.cefet.trab_republica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// --- Imports Adicionais para CORS ---
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
// --- Fim dos Imports Adicionais ---

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
            .username("admin")
            .password(encoder.encode("123456"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          // ======================== MUDANÇA 1 ========================
          // Habilita a configuração de CORS definida no Bean abaixo
          .cors(Customizer.withDefaults())
          // ==========================================================

          .csrf(csrf -> csrf.disable())
          .headers(headers -> headers.frameOptions(frame -> frame.disable()))
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/h2-console/**").permitAll()
              .anyRequest().authenticated()
          )
          .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ======================== MUDANÇA 2 ========================
    // Bean que define as regras de CORS para a aplicação
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Define as origens permitidas (seu frontend em dev e prod)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200", 
            "https://trabalho-ds-republica.onrender.com" // Adicione a URL do seu frontend em produção se for diferente
        ));
        // Define os métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Define os cabeçalhos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "x-auth-token"));
        // Permite o envio de credenciais (cookies, etc.)
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica a configuração a todos os endpoints da sua API
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
    // ==========================================================
}