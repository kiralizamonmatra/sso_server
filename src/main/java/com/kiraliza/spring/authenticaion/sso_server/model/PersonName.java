package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PersonName
{
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "middle_name", length = 100)
    private String middleName;
    @Column(name = "prefix", length = 100)
    private String prefix;
    @Column(name = "suffix", length = 100)
    private String suffix;

    public String getFirstName()
    {
        return firstName;
    }

    public PersonName setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public PersonName setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public PersonName setMiddleName(String middleName)
    {
        this.middleName = middleName;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public PersonName setPrefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public PersonName setSuffix(String suffix)
    {
        this.suffix = suffix;
        return this;
    }
}
