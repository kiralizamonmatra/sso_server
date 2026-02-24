package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OAuth2AuthorizationConsentKey
{
    @Column(name = "registered_client_id", length = 100, nullable = false)
    private String registeredClientId;

    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    public OAuth2AuthorizationConsentKey()
    {
    }

    public OAuth2AuthorizationConsentKey(String registeredClientId, String principalName)
    {
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
    }

    public String getRegisteredClientId()
    {
        return registeredClientId;
    }

    public OAuth2AuthorizationConsentKey setRegisteredClientId(String registeredClientId)
    {
        this.registeredClientId = registeredClientId;
        return this;
    }

    public String getPrincipalName()
    {
        return principalName;
    }

    public OAuth2AuthorizationConsentKey setPrincipalName(String principalName)
    {
        this.principalName = principalName;
        return this;
    }
}
