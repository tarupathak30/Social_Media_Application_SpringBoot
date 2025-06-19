package com.social_app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.social_app.config.Jwt_Constants.JWT_HEADER;

public class JwtValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //when we try accessing a secured endpoint,
    //then we will pass the header in the token
        String jwt = request.getHeader(JWT_HEADER);

        if(jwt != null){
            try{
                String email = JwtProvider.getEmailFromJwtToken(jwt);
                List<GrantedAuthority> authorityList = new ArrayList<>();
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }catch(Exception e){
                throw new BadCredentialsException("Invalid Token....");
            }
        }
        filterChain.doFilter(request, response);
    }
}
