package com.pickemsystem.pickemsystembackend.security;

import com.pickemsystem.pickemsystembackend.filters.CustomAuthenticationFilter;
import com.pickemsystem.pickemsystembackend.filters.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private AuthManager authManager;
    @Autowired
    private JWTTokenManager jwtTokenManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests().antMatchers("/login").permitAll();
        http.authorizeHttpRequests().antMatchers("/registry").permitAll();
        http.authorizeHttpRequests().antMatchers("/token/refresh").permitAll();
        http.authorizeHttpRequests().antMatchers("/verify/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/password/email/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/password/reset/**").permitAll();

        http.authorizeHttpRequests().anyRequest().authenticated();

        http.addFilterBefore(
                new CustomAuthenticationFilter(authManager, jwtTokenManager),
                UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(new CustomAuthorizationFilter(jwtTokenManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
