package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "oauth2_registered_client")
public class OAuth2RegisteredClient
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "client_id", length = 100, nullable = false)
    private String clientId;

    @Column(name = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name", length = 200, nullable = false)
    private String clientName;

    @Column(name = "client_authentication_methods", length = 1000, nullable = false)
    private String clientAuthenticationMethods;

    @Column(name = "authorization_grant_types", length = 1000, nullable = false)
    private String authorizationGrantTypes;

    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    @Column(name = "post_logout_redirect_uris", length = 1000)
    private String postLogoutRedirectUris;

    @Column(name = "scopes", length = 1000)
    private String scopes;

    @Column(name = "client_settings", length = 2000)
    private String clientSettings;

    @Column(name = "token_settings", length = 2000)
    private String tokenSettings;

    public String getId()
    {
        return id;
    }

    public OAuth2RegisteredClient setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getClientId()
    {
        return clientId;
    }

    public OAuth2RegisteredClient setClientId(String clientId)
    {
        this.clientId = clientId;
        return this;
    }

    public Instant getClientIdIssuedAt()
    {
        return clientIdIssuedAt;
    }

    public OAuth2RegisteredClient setClientIdIssuedAt(Instant clientIdIssuedAt)
    {
        this.clientIdIssuedAt = clientIdIssuedAt;
        return this;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public OAuth2RegisteredClient setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
        return this;
    }

    public Instant getClientSecretExpiresAt()
    {
        return clientSecretExpiresAt;
    }

    public OAuth2RegisteredClient setClientSecretExpiresAt(Instant clientSecretExpiresAt)
    {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        return this;
    }

    public String getClientName()
    {
        return clientName;
    }

    public OAuth2RegisteredClient setClientName(String clientName)
    {
        this.clientName = clientName;
        return this;
    }

    public String getClientAuthenticationMethods()
    {
        return clientAuthenticationMethods;
    }

    public OAuth2RegisteredClient setClientAuthenticationMethods(String clientAuthenticationMethods)
    {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
        return this;
    }

    public String getAuthorizationGrantTypes()
    {
        return authorizationGrantTypes;
    }

    public OAuth2RegisteredClient setAuthorizationGrantTypes(String authorizationGrantTypes)
    {
        this.authorizationGrantTypes = authorizationGrantTypes;
        return this;
    }

    public String getRedirectUris()
    {
        return redirectUris;
    }

    public OAuth2RegisteredClient setRedirectUris(String redirectUris)
    {
        this.redirectUris = redirectUris;
        return this;
    }

    public String getPostLogoutRedirectUris()
    {
        return postLogoutRedirectUris;
    }

    public OAuth2RegisteredClient setPostLogoutRedirectUris(String postLogoutRedirectUris)
    {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
        return this;
    }

    public String getScopes()
    {
        return scopes;
    }

    public OAuth2RegisteredClient setScopes(String scopes)
    {
        this.scopes = scopes;
        return this;
    }

    public String getClientSettings()
    {
        return clientSettings;
    }

    public OAuth2RegisteredClient setClientSettings(String clientSettings)
    {
        this.clientSettings = clientSettings;
        return this;
    }

    public String getTokenSettings()
    {
        return tokenSettings;
    }

    public OAuth2RegisteredClient setTokenSettings(String tokenSettings)
    {
        this.tokenSettings = tokenSettings;
        return this;
    }
}
