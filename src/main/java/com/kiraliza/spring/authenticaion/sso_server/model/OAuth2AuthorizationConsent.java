package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "oauth2_authorization_consent")
public class OAuth2AuthorizationConsent
{
    @EmbeddedId
    private OAuth2AuthorizationConsentKey id;

    @Column(name = "authorities", nullable = false, length = 1000)
    private String authorities;

    public OAuth2AuthorizationConsent()
    {
    }

    public OAuth2AuthorizationConsent(String registeredClientId, String principalName, String authorities)
    {
        this.id = new OAuth2AuthorizationConsentKey(registeredClientId, principalName);
        this.authorities = authorities;
    }

    public OAuth2AuthorizationConsentKey getId()
    {
        return id;
    }

    public OAuth2AuthorizationConsent setId(OAuth2AuthorizationConsentKey id)
    {
        this.id = id;
        return this;
    }

    public String getRegisteredClientId()
    {
        return id.getRegisteredClientId();
    }

    public OAuth2AuthorizationConsent setRegisteredClientId(String registeredClientId)
    {
        this.id.setRegisteredClientId(registeredClientId);
        return this;
    }

    public String getPrincipalName()
    {
        return id.getPrincipalName();
    }

    public OAuth2AuthorizationConsent setPrincipalName(String principalName)
    {
        this.id.setPrincipalName(principalName);
        return this;
    }

    public String getAuthorities()
    {
        return authorities;
    }

    public OAuth2AuthorizationConsent setAuthorities(String authorities)
    {
        this.authorities = authorities;
        return this;
    }
}
