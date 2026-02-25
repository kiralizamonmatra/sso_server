package com.kiraliza.spring.authenticaion.sso_server.model;

import com.kiraliza.spring.authenticaion.sso_server.type.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LoggedInUser extends org.springframework.security.core.userdetails.User implements OAuth2User
{
    private String id;
    private PersonName personName;
    private String email;
    private Instant birthOfDate;
    private Instant registrationDate;
    private Set<Role> roles;
    private Map<String, Object> oauthAttributes;

    public LoggedInUser(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);
    }

    public LoggedInUser(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities, User user)
    {
        super(username, password, authorities);
        if (user != null)
        {
            this.id = user.getId();
            this.personName = user.getPersonName();
            this.email = user.getEmail();
            this.birthOfDate = user.getBirthOfDate();
            this.registrationDate = user.getRegistrationDate();
            this.setRoles(user.getRoles());
        }
    }

    public LoggedInUser(String username, @Nullable String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getId()
    {
        return id;
    }

    public LoggedInUser setId(String id)
    {
        this.id = id;
        return this;
    }

    public PersonName getPersonName()
    {
        return personName;
    }

    public LoggedInUser setPersonName(PersonName personName)
    {
        this.personName = personName;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public LoggedInUser setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public Instant getBirthOfDate()
    {
        return birthOfDate;
    }

    public LoggedInUser setBirthOfDate(Instant birthOfDate)
    {
        this.birthOfDate = birthOfDate;
        return this;
    }

    public Instant getRegistrationDate()
    {
        return registrationDate;
    }

    public LoggedInUser setRegistrationDate(Instant registrationDate)
    {
        this.registrationDate = registrationDate;
        return this;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public LoggedInUser setRoles(Set<Role> roles)
    {
        this.roles = roles;
        return this;
    }

    public Map<String, Object> getOauthAttributes()
    {
        return oauthAttributes;
    }

    public LoggedInUser setOauthAttributes(Map<String, Object> oauthAttributes)
    {
        this.oauthAttributes = oauthAttributes;
        return this;
    }

    @Override
    public Map<String, Object> getAttributes()
    {
        return oauthAttributes;
    }

    @Override
    public String getName()
    {
        return this.getUsername();
    }
}
