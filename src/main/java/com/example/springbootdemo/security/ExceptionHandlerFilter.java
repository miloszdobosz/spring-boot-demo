package com.example.springbootdemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (PreAuthenticatedCredentialsNotFoundException e) {
            // No field "Authorization" in request header

            // Solution 1: Tomcat error page
//            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());

            // Solution 2: Plain string error page
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.getWriter().write(e.getMessage());

            // Solution 3: Redirection
            response.sendRedirect("/api.html");
        }
    }
}