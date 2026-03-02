package com.kiraliza.spring.authenticaion.sso_server.exception;

public class OAuth2ProviderNotFoundException extends RuntimeException
{
    public OAuth2ProviderNotFoundException(String message)
    {
        super(message);
    }
}
