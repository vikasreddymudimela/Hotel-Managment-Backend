package com.vikas.hotelmanagment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vikas.hotelmanagment.security.jwt.AuthTokenFilter;
import com.vikas.hotelmanagment.security.jwt.JwtAuthEntrypoint;
import com.vikas.hotelmanagment.security.user.HotelUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class HotelSecurityConfig {
    private final HotelUserDetailsService userdetailsservice;
    private final JwtAuthEntrypoint jwtauthentrypoint;
    
    @Autowired
    private AuthTokenFilter authtokenfilter;

    public AuthTokenFilter authenticationtokenfilter() {
        System.out.println("Creating AuthTokenFilter bean...");
        return authtokenfilter;
    }

    public DaoAuthenticationProvider authenticationprovider() {
        System.out.println("Creating DaoAuthenticationProvider bean...");
        var authprovider = new DaoAuthenticationProvider();
        authprovider.setUserDetailsService(userdetailsservice);
        authprovider.setPasswordEncoder(passwordencoder());
        return authprovider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
        System.out.println("Creating AuthenticationManager bean...");
        return authconfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        System.out.println("Configuring security filter chain...");

        http.csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtauthentrypoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated());

        http.authenticationProvider(authenticationprovider());
        http.addFilterBefore(authenticationtokenfilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordencoder() {
        System.out.println("Creating PasswordEncoder bean...");
        return new BCryptPasswordEncoder();
    }
}
