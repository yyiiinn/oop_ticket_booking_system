package com.oop.springbootmvc;

import com.oop.springbootmvc.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
        // CSRF Token
        .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth ->
            auth
            .requestMatchers("/profile").authenticated()
            .requestMatchers("/login").permitAll()
            .anyRequest().permitAll()
        )
        .formLogin(login ->
            login.loginPage("/login")
            .loginProcessingUrl("/api/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .permitAll()
        )
        .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
        );

        return http.build();
    }
}
