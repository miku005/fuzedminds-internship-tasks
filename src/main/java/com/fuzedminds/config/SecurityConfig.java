package com.fuzedminds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
private JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
//        http.authorizeHttpRequests().anyRequest().permitAll();
            http.authorizeHttpRequests().requestMatchers("/api/user/sign-up","/api/user/login","/api/user/property/sign-up")
                    .permitAll()
                    .requestMatchers("/api/v1/property/addProperty")
                    .hasRole("OWNER")
                    .requestMatchers("/api/v1/property/deleteProperty")
                    .hasAnyRole("OWNER","ADMIN")
                    .requestMatchers("/api/user/blog/sign-up")
                    .hasRole("ADMIN")
                    .anyRequest().authenticated();
        return http.build();
    }
}
