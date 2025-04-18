package com.example.CENProj.config;

import com.example.CENProj.service.UserDetailsServiceImpl;
import com.example.CENProj.service.UserServiceImpl;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserServiceImpl userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ASYNC, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/user/account").authenticated()
                        .anyRequest().permitAll()
                ).formLogin(form -> {
                    form.loginPage("/user/login").permitAll();
                    form.loginProcessingUrl("/login").permitAll();
                    form.failureUrl("/user/login?error").permitAll();
                })
                .authenticationManager(authenticationManager());
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) {
        builder.eraseCredentials(false);
    }

    // Use custom auth manager with custom password encoder and userdetails service for fetching user credentials for login flow
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        ProviderManager authenticationManager = new ProviderManager(authenticationProvider);
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return new UserDetailsServiceImpl(userService);
    }

    // Use Bcrypt for password encoding/decoding for login flow
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
