package com.food_easy_back.backend_food_easy.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;


import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwFilter  extends OncePerRequestFilter {


    private final JwUtil jwUtil;

    
    private final UserDetailsService userDetailService;

    
    


    @Autowired
    public JwFilter(JwUtil jwUtil, UserDetailsService userDetailService) {
        this.jwUtil = jwUtil;
        this.userDetailService = userDetailService;
    }






    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader==null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1].trim();

        if(!this.jwUtil.isValid(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwUtil.getUserName(jwt);

        User user = (User)this.userDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(user.getUsername()
        ,user.getPassword(),user.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
