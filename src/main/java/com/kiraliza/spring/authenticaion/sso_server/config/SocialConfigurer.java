package com.kiraliza.spring.authenticaion.sso_server.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SocialConfigurer extends AbstractHttpConfigurer<SocialConfigurer, HttpSecurity>
{
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> getOAuth2UserService()
    {
        return oauth2UserService;
    }

    public SocialConfigurer setOAuth2UserService(OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService)
    {
        this.oauth2UserService = oauth2UserService;
        return this;
    }

    public AuthenticationFailureHandler getFailureHandler()
    {
        return failureHandler;
    }

    public SocialConfigurer setFailureHandler(AuthenticationFailureHandler failureHandler)
    {
        this.failureHandler = failureHandler;
        return this;
    }

    public AuthenticationSuccessHandler getSuccessHandler()
    {
        return successHandler;
    }

    public SocialConfigurer setSuccessHandler(AuthenticationSuccessHandler successHandler)
    {
        this.successHandler = successHandler;
        return this;
    }

    @Override
    public void init(HttpSecurity http)
    {
        http.oauth2Login(oauth2Login -> {
            if (this.oauth2UserService != null)
            {
                oauth2Login.userInfoEndpoint(userInfo -> userInfo.userService(this.oauth2UserService));
            }
            if (this.successHandler != null)
            {
                oauth2Login.successHandler(this.successHandler);
            }
            if (this.failureHandler != null)
            {
                oauth2Login.failureHandler(this.failureHandler);
            }
        });
    }
}
