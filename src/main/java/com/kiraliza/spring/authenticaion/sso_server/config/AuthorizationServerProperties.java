package com.kiraliza.spring.authenticaion.sso_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth.server")
public class AuthorizationServerProperties
{
    private String issuer;

    public String getIssuer()
    {
        return issuer;
    }

    public AuthorizationServerProperties setIssuer(String issuer)
    {
        this.issuer = issuer;
        return this;
    }
}
