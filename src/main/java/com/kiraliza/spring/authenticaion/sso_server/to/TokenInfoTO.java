package com.kiraliza.spring.authenticaion.sso_server.to;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenIntrospection;

import java.net.URL;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class TokenInfoTO
{
    private Boolean active;
    private String subject;
    private List<String> audience;
    private Instant notBefore;
    private List<String> scopes;
    private URL issuer;
    private Instant expiresAt;
    private Instant issuedAt;
    private String tokenId;
    private String clientId;
    private String tokenType;
    private Object principal;
    private Collection<? extends GrantedAuthority> authorities;

    public TokenInfoTO()
    {
    }

    public TokenInfoTO init(OAuth2TokenIntrospection claims)
    {
        this.active = true;
        this.subject = claims.getSubject();
        this.audience = claims.getAudience();
        this.notBefore = claims.getNotBefore();
        this.scopes = claims.getScopes();
        this.issuer = claims.getIssuer();
        this.expiresAt = claims.getExpiresAt();
        this.issuedAt = claims.getIssuedAt();
        this.tokenId = claims.getId();
        this.clientId = claims.getClientId();
        this.tokenType = claims.getTokenType();

        return this;
    }

    public Boolean getActive()
    {
        return active;
    }

    public TokenInfoTO setActive(Boolean active)
    {
        this.active = active;
        return this;
    }

    public String getSubject()
    {
        return subject;
    }

    public TokenInfoTO setSubject(String subject)
    {
        this.subject = subject;
        return this;
    }

    public List<String> getAudience()
    {
        return audience;
    }

    public TokenInfoTO setAudience(List<String> audience)
    {
        this.audience = audience;
        return this;
    }

    public Instant getNotBefore()
    {
        return notBefore;
    }

    public TokenInfoTO setNotBefore(Instant notBefore)
    {
        this.notBefore = notBefore;
        return this;
    }

    public List<String> getScopes()
    {
        return scopes;
    }

    public TokenInfoTO setScopes(List<String> scopes)
    {
        this.scopes = scopes;
        return this;
    }

    public URL getIssuer()
    {
        return issuer;
    }

    public TokenInfoTO setIssuer(URL issuer)
    {
        this.issuer = issuer;
        return this;
    }

    public Instant getExpiresAt()
    {
        return expiresAt;
    }

    public TokenInfoTO setExpiresAt(Instant expiresAt)
    {
        this.expiresAt = expiresAt;
        return this;
    }

    public Instant getIssuedAt()
    {
        return issuedAt;
    }

    public TokenInfoTO setIssuedAt(Instant issuedAt)
    {
        this.issuedAt = issuedAt;
        return this;
    }

    public String getTokenId()
    {
        return tokenId;
    }

    public TokenInfoTO setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
        return this;
    }

    public String getClientId()
    {
        return clientId;
    }

    public TokenInfoTO setClientId(String clientId)
    {
        this.clientId = clientId;
        return this;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public TokenInfoTO setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
        return this;
    }

    public Object getPrincipal()
    {
        return principal;
    }

    public TokenInfoTO setPrincipal(Object principal)
    {
        this.principal = principal;
        return this;
    }

    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public TokenInfoTO setAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
        this.authorities = authorities;
        return this;
    }
}
