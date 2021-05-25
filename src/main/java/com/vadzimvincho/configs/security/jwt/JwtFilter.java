package com.vadzimvincho.configs.security.jwt;

import com.vadzimvincho.configs.security.SecurityUserDetails.SecurityUserDetails;
import com.vadzimvincho.configs.security.SecurityUserDetails.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, SecurityUserDetailsService securityUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            SecurityUserDetails securityUserDetails = securityUserDetailsService.loadUserByUsername(userLogin);
            Authentication auth = new UsernamePasswordAuthenticationToken(securityUserDetails, null, securityUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}