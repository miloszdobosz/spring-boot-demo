package com.example.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    @Lazy
    PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProviderBean;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/api.html",
                "/v3/api-docs/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE).hasRole("admin")
                .requestMatchers(HttpMethod.POST).hasRole("developer")
                .requestMatchers(HttpMethod.GET).hasRole("user")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterAt(authenticationFilter(authenticationManager()), RequestHeaderAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, RequestHeaderAuthenticationFilter.class);
        return http.build();
    }

    // Must not be a bean by itself or will be enabled even for ignored endpoints
    public RequestHeaderAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter authenticationFilter = new RequestHeaderAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setPrincipalRequestHeader(HttpHeaders.AUTHORIZATION);
        return authenticationFilter;
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        ProviderManager manager = new ProviderManager(Collections.singletonList(preAuthenticatedAuthenticationProviderBean));
        manager.setEraseCredentialsAfterAuthentication(false);
        return manager;
    }

    @Bean
    PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userServiceWrapper) {
        final PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userServiceWrapper);
        return provider;
    }

    @Bean
    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userServiceWrapper(UserService userService) {
        final UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
        wrapper.setUserDetailsService(userService);
        return wrapper;
    }
}
