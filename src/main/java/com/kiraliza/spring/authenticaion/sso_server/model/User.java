package com.kiraliza.spring.authenticaion.sso_server.model;

import com.kiraliza.spring.authenticaion.sso_server.type.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Embedded
    private PersonName personName;
    private String email;
    private String username;
    private String password;
    @Column(nullable = false)
    private boolean active;
    @Column(name = "birt_of_date")
    private Instant birthOfDate;
    @Column(name = "registration_date")
    private Instant registrationDate;
    @Column(name = "role")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Role> roles;
    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private Instant createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", updatable = true, nullable = true)
    private Instant updatedDate;
    @Transient
    private Map<String, Object> oauthAttributes;

    public String getId()
    {
        return id;
    }

    public User setId(String id)
    {
        this.id = id;
        return this;
    }

    public PersonName getPersonName()
    {
        return personName;
    }

    public User setPersonName(PersonName personName)
    {
        this.personName = personName;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public User setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public Instant getBirthOfDate()
    {
        return birthOfDate;
    }

    public User setBirthOfDate(Instant birthOfDate)
    {
        this.birthOfDate = birthOfDate;
        return this;
    }

    public Instant getRegistrationDate()
    {
        return registrationDate;
    }

    public User setRegistrationDate(Instant registrationDate)
    {
        this.registrationDate = registrationDate;
        return this;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public User setRoles(Set<Role> roles)
    {
        this.roles = roles;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    public User setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public boolean isActive()
    {
        return active;
    }

    public User setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public User setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public User setCreatedDate(Instant createdDate)
    {
        this.createdDate = createdDate;
        return this;
    }

    public Instant getUpdatedDate()
    {
        return updatedDate;
    }

    public User setUpdatedDate(Instant updatedDate)
    {
        this.updatedDate = updatedDate;
        return this;
    }

    public Map<String, Object> getOauthAttributes()
    {
        return oauthAttributes;
    }

    public User setOauthAttributes(Map<String, Object> oauthAttributes)
    {
        this.oauthAttributes = oauthAttributes;
        return this;
    }

    public Map<String, Object> getAttributes()
    {
        return oauthAttributes;
    }
}
