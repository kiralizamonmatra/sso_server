package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "oauth2_authorization")
public class OAuth2Authorization
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "registered_client_id", length = 100, nullable = false)
    private String registeredClientId;

    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    @Column(name = "authorization_grant_type", length = 100, nullable = false)
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    @Lob
    @Column(name = "attributes")
    private byte[] attributes;

    @Lob
    @Column(name = "state")
    private byte[] state;

    @Lob
    @Column(name = "authorization_code_value")
    private byte[] authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Lob
    @Column(name = "authorization_code_metadata")
    private byte[] authorizationCodeMetadata;

    @Lob
    @Column(name = "access_token_value")
    private byte[] accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Lob
    @Column(name = "access_token_metadata")
    private byte[] accessTokenMetadata;

    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Lob
    @Column(name = "oidc_id_token_value")
    private byte[] oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Lob
    @Column(name = "oidc_id_token_metadata")
    private byte[] oidcIdTokenMetadata;

    @Lob
    @Column(name = "refresh_token_value")
    private byte[] refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Lob
    @Column(name = "refresh_token_metadata")
    private byte[] refreshTokenMetadata;

    @Lob
    @Column(name = "id_token_value")
    private byte[] idTokenValue;

    @Column(name = "id_token_issued_at")
    private Instant idTokenIssuedAt;

    @Column(name = "id_token_expires_at")
    private Instant idTokenExpiresAt;

    @Lob
    @Column(name = "id_token_metadata")
    private byte[] idTokenMetadata;

    @Lob
    @Column(name = "user_code_value")
    private byte[] userCodeValue;

    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;

    @Lob
    @Column(name = "user_code_metadata")
    private byte[] userCodeMetadata;

    @Lob
    @Column(name = "device_code_value")
    private byte[] deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;

    @Lob
    @Column(name = "device_code_metadata")
    private byte[] deviceCodeMetadata;

    public String getId()
    {
        return id;
    }

    public OAuth2Authorization setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getRegisteredClientId()
    {
        return registeredClientId;
    }

    public OAuth2Authorization setRegisteredClientId(String registeredClientId)
    {
        this.registeredClientId = registeredClientId;
        return this;
    }

    public String getPrincipalName()
    {
        return principalName;
    }

    public OAuth2Authorization setPrincipalName(String principalName)
    {
        this.principalName = principalName;
        return this;
    }

    public String getAuthorizationGrantType()
    {
        return authorizationGrantType;
    }

    public OAuth2Authorization setAuthorizationGrantType(String authorizationGrantType)
    {
        this.authorizationGrantType = authorizationGrantType;
        return this;
    }

    public String getAuthorizedScopes()
    {
        return authorizedScopes;
    }

    public OAuth2Authorization setAuthorizedScopes(String authorizedScopes)
    {
        this.authorizedScopes = authorizedScopes;
        return this;
    }

    public byte[] getAttributes()
    {
        return attributes;
    }

    public OAuth2Authorization setAttributes(byte[] attributes)
    {
        this.attributes = attributes;
        return this;
    }

    public byte[] getState()
    {
        return state;
    }

    public OAuth2Authorization setState(byte[] state)
    {
        this.state = state;
        return this;
    }

    public byte[] getAuthorizationCodeValue()
    {
        return authorizationCodeValue;
    }

    public OAuth2Authorization setAuthorizationCodeValue(byte[] authorizationCodeValue)
    {
        this.authorizationCodeValue = authorizationCodeValue;
        return this;
    }

    public Instant getAuthorizationCodeIssuedAt()
    {
        return authorizationCodeIssuedAt;
    }

    public OAuth2Authorization setAuthorizationCodeIssuedAt(Instant authorizationCodeIssuedAt)
    {
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
        return this;
    }

    public Instant getAuthorizationCodeExpiresAt()
    {
        return authorizationCodeExpiresAt;
    }

    public OAuth2Authorization setAuthorizationCodeExpiresAt(Instant authorizationCodeExpiresAt)
    {
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
        return this;
    }

    public byte[] getAuthorizationCodeMetadata()
    {
        return authorizationCodeMetadata;
    }

    public OAuth2Authorization setAuthorizationCodeMetadata(byte[] authorizationCodeMetadata)
    {
        this.authorizationCodeMetadata = authorizationCodeMetadata;
        return this;
    }

    public byte[] getAccessTokenValue()
    {
        return accessTokenValue;
    }

    public OAuth2Authorization setAccessTokenValue(byte[] accessTokenValue)
    {
        this.accessTokenValue = accessTokenValue;
        return this;
    }

    public Instant getAccessTokenIssuedAt()
    {
        return accessTokenIssuedAt;
    }

    public OAuth2Authorization setAccessTokenIssuedAt(Instant accessTokenIssuedAt)
    {
        this.accessTokenIssuedAt = accessTokenIssuedAt;
        return this;
    }

    public Instant getAccessTokenExpiresAt()
    {
        return accessTokenExpiresAt;
    }

    public OAuth2Authorization setAccessTokenExpiresAt(Instant accessTokenExpiresAt)
    {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        return this;
    }

    public byte[] getAccessTokenMetadata()
    {
        return accessTokenMetadata;
    }

    public OAuth2Authorization setAccessTokenMetadata(byte[] accessTokenMetadata)
    {
        this.accessTokenMetadata = accessTokenMetadata;
        return this;
    }

    public String getAccessTokenType()
    {
        return accessTokenType;
    }

    public OAuth2Authorization setAccessTokenType(String accessTokenType)
    {
        this.accessTokenType = accessTokenType;
        return this;
    }

    public String getAccessTokenScopes()
    {
        return accessTokenScopes;
    }

    public OAuth2Authorization setAccessTokenScopes(String accessTokenScopes)
    {
        this.accessTokenScopes = accessTokenScopes;
        return this;
    }

    public byte[] getOidcIdTokenValue()
    {
        return oidcIdTokenValue;
    }

    public OAuth2Authorization setOidcIdTokenValue(byte[] oidcIdTokenValue)
    {
        this.oidcIdTokenValue = oidcIdTokenValue;
        return this;
    }

    public Instant getOidcIdTokenIssuedAt()
    {
        return oidcIdTokenIssuedAt;
    }

    public OAuth2Authorization setOidcIdTokenIssuedAt(Instant oidcIdTokenIssuedAt)
    {
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
        return this;
    }

    public Instant getOidcIdTokenExpiresAt()
    {
        return oidcIdTokenExpiresAt;
    }

    public OAuth2Authorization setOidcIdTokenExpiresAt(Instant oidcIdTokenExpiresAt)
    {
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
        return this;
    }

    public byte[] getOidcIdTokenMetadata()
    {
        return oidcIdTokenMetadata;
    }

    public OAuth2Authorization setOidcIdTokenMetadata(byte[] oidcIdTokenMetadata)
    {
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;
        return this;
    }

    public byte[] getRefreshTokenValue()
    {
        return refreshTokenValue;
    }

    public OAuth2Authorization setRefreshTokenValue(byte[] refreshTokenValue)
    {
        this.refreshTokenValue = refreshTokenValue;
        return this;
    }

    public Instant getRefreshTokenIssuedAt()
    {
        return refreshTokenIssuedAt;
    }

    public OAuth2Authorization setRefreshTokenIssuedAt(Instant refreshTokenIssuedAt)
    {
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;
        return this;
    }

    public Instant getRefreshTokenExpiresAt()
    {
        return refreshTokenExpiresAt;
    }

    public OAuth2Authorization setRefreshTokenExpiresAt(Instant refreshTokenExpiresAt)
    {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        return this;
    }

    public byte[] getRefreshTokenMetadata()
    {
        return refreshTokenMetadata;
    }

    public OAuth2Authorization setRefreshTokenMetadata(byte[] refreshTokenMetadata)
    {
        this.refreshTokenMetadata = refreshTokenMetadata;
        return this;
    }

    public byte[] getIdTokenValue()
    {
        return idTokenValue;
    }

    public OAuth2Authorization setIdTokenValue(byte[] idTokenValue)
    {
        this.idTokenValue = idTokenValue;
        return this;
    }

    public Instant getIdTokenIssuedAt()
    {
        return idTokenIssuedAt;
    }

    public OAuth2Authorization setIdTokenIssuedAt(Instant idTokenIssuedAt)
    {
        this.idTokenIssuedAt = idTokenIssuedAt;
        return this;
    }

    public Instant getIdTokenExpiresAt()
    {
        return idTokenExpiresAt;
    }

    public OAuth2Authorization setIdTokenExpiresAt(Instant idTokenExpiresAt)
    {
        this.idTokenExpiresAt = idTokenExpiresAt;
        return this;
    }

    public byte[] getIdTokenMetadata()
    {
        return idTokenMetadata;
    }

    public OAuth2Authorization setIdTokenMetadata(byte[] idTokenMetadata)
    {
        this.idTokenMetadata = idTokenMetadata;
        return this;
    }

    public byte[] getUserCodeValue()
    {
        return userCodeValue;
    }

    public OAuth2Authorization setUserCodeValue(byte[] userCodeValue)
    {
        this.userCodeValue = userCodeValue;
        return this;
    }

    public Instant getUserCodeIssuedAt()
    {
        return userCodeIssuedAt;
    }

    public OAuth2Authorization setUserCodeIssuedAt(Instant userCodeIssuedAt)
    {
        this.userCodeIssuedAt = userCodeIssuedAt;
        return this;
    }

    public Instant getUserCodeExpiresAt()
    {
        return userCodeExpiresAt;
    }

    public OAuth2Authorization setUserCodeExpiresAt(Instant userCodeExpiresAt)
    {
        this.userCodeExpiresAt = userCodeExpiresAt;
        return this;
    }

    public byte[] getUserCodeMetadata()
    {
        return userCodeMetadata;
    }

    public OAuth2Authorization setUserCodeMetadata(byte[] userCodeMetadata)
    {
        this.userCodeMetadata = userCodeMetadata;
        return this;
    }

    public byte[] getDeviceCodeValue()
    {
        return deviceCodeValue;
    }

    public OAuth2Authorization setDeviceCodeValue(byte[] deviceCodeValue)
    {
        this.deviceCodeValue = deviceCodeValue;
        return this;
    }

    public Instant getDeviceCodeIssuedAt()
    {
        return deviceCodeIssuedAt;
    }

    public OAuth2Authorization setDeviceCodeIssuedAt(Instant deviceCodeIssuedAt)
    {
        this.deviceCodeIssuedAt = deviceCodeIssuedAt;
        return this;
    }

    public Instant getDeviceCodeExpiresAt()
    {
        return deviceCodeExpiresAt;
    }

    public OAuth2Authorization setDeviceCodeExpiresAt(Instant deviceCodeExpiresAt)
    {
        this.deviceCodeExpiresAt = deviceCodeExpiresAt;
        return this;
    }

    public byte[] getDeviceCodeMetadata()
    {
        return deviceCodeMetadata;
    }

    public OAuth2Authorization setDeviceCodeMetadata(byte[] deviceCodeMetadata)
    {
        this.deviceCodeMetadata = deviceCodeMetadata;
        return this;
    }
}
