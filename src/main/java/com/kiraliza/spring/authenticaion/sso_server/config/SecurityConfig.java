package com.kiraliza.spring.authenticaion.sso_server.config;

import com.kiraliza.spring.authenticaion.sso_server.service.AuthUserDetailsService;
import com.kiraliza.spring.authenticaion.sso_server.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig
{
    @Value("{auth.server.default.username}")
    private String defaultUsername;

    @Value("{auth.server.default.password}")
    private String defaultPassword;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Autowired
    private OAuth2UserService oauth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(OrderedFilter.LOWEST_PRECEDENCE)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
    {
        SocialConfigurer socialConfigurer = new SocialConfigurer().setOAuth2UserService(oauth2UserService);

        http
            .apply(socialConfigurer);

        http
            .getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService);

        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        http
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults()
    {
        return new GrantedAuthorityDefaults("");
    }
}
