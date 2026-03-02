package com.kiraliza.spring.authenticaion.sso_server.controller;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizationFailureHandler;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomOAuth2AuthorizationFailureHandler implements OAuth2AuthorizationFailureHandler
{

    @Override
    public void onAuthorizationFailure(OAuth2AuthorizationException authorizationException, Authentication principal, Map<String, Object> attributes)
    {
        LogHelper.error("==============OAuth2AuthorizationFailureHandler::onAuthorizationFailure");
        LogHelper.error(authorizationException.getMessage());
    }
}
