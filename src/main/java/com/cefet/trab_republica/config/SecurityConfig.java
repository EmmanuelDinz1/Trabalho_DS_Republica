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

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        // usuário de teste “admin” / “123456”
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
          // desabilita CSRF para facilitar testes de API (só para dev!)
          .csrf(csrf -> csrf.disable())

          // necessário para h2-console em iframe
          .headers(headers -> headers.frameOptions(frame -> frame.disable()))

          // regras de acesso
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/h2-console/**").permitAll()   // libera console H2
              .anyRequest().authenticated()                    // resto exige login
          )

          // usa HTTP Basic em vez de formulário
          .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
