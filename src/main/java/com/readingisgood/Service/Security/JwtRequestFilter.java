package com.readingisgood.Service.Security;


import com.readingisgood.Service.Security.Service.JWTService;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ICustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = httpServletRequest.getHeader("Authorization");
            if (jwtService.validateJwtToken(token)) {
                authenticateUser(httpServletRequest, token);
            }
        }catch (Exception e){
            log.error("Authentication failed: {0}", e);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void authenticateUser(HttpServletRequest httpServletRequest, String token) {
        String subject = jwtService.parseJWT(token);
        UserDetails userDetails = customerService.loadUserByEmail(subject);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
