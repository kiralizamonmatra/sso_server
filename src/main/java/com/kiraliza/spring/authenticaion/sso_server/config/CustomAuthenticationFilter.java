package com.kiraliza.spring.authenticaion.sso_server.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.setAuthenticationManager(authenticationManager);
    }
}
