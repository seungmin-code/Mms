package com.min.mms.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 로거 설정
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    // 패스워드 인코더
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 개발 편의성을 위한 CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 인가 설정
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/").permitAll()
                .requestMatchers("/security/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/favicon.ico").permitAll()
                .requestMatchers(
                        "/common/**",
                        "/gismap/**",
                        "/notices/**",
                        "/station/**",
                        "/finedustalert/**",
                        "/realTimeSido/**",
                        "/realTimeDay/**",
                        "/realTimeMonth/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
        )
        .formLogin(login -> login
                .loginPage("/security/loginForm").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/security/loginForm?error=true")
                .failureHandler((request, response, exception) -> {
                    logger.error("Login failed. {}", exception.getMessage());
                    request.getRequestDispatcher("/security/loginForm?error=true").forward(request, response);
                })
        )

        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
        );
        return http.build();
    }

}
