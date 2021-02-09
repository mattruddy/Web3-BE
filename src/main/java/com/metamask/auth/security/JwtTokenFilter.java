package com.metamask.auth.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserIdentity userIdentity;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserIdentity userIdentity) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userIdentity = userIdentity;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                long userId = jwtTokenProvider.getUserId(token);
                userIdentity.create(userId);
            }
        } catch (Exception e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
