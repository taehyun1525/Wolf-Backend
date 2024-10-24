package com.kdt.wolf.global.config;

import com.kdt.wolf.global.auth.dto.UserRoleType;
import com.kdt.wolf.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                        authz ->
                                authz.requestMatchers("/api/v1/auth/google", "/api/v1/auth/login", "/api/v1/auth/test-login",
                                                "/api/v1/auth/reissue", "/api/v1/post/search/{keyword}", "/api/v1/post/{postId}", "/api/v1/post/view/{type}",
                                                "/api/v1/faqs/{category}", "/api/v1/post/{groupId}/question/{option}", "/api/v1/post/{groupId}/news",
                                                "/api/v1/notices/{notices}","/api/v1/notices").permitAll()
                                        .requestMatchers("/api/v1/**").authenticated()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/post/{options}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/admin/auth/login").permitAll()
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Profile("!prod")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring()
                        .requestMatchers(
                                "/resources/**",
                                "/static/**",
                                "/actuator/**",
                                /* swagger */
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                                /* external callback */
                        );
    }
}
