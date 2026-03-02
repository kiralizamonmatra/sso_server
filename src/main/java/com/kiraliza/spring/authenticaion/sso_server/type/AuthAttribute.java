package com.kiraliza.spring.authenticaion.sso_server.type;

import java.util.Optional;

public enum AuthAttribute
{
    EMAIL("email");

    private final String data;

    AuthAttribute(String c)
    {
        data = c;
    }

    public String value()
    {
        return data;
    }

    public boolean equals(String t)
    {
        return data.equals(t);
    }

    public static Optional<AuthAttribute> find(String data)
    {
        AuthAttribute item = null;
        try
        {
            item = AuthAttribute.valueOf(data);
        }
        catch(Exception ignored)
        {
        }
        return Optional.ofNullable(item);
    }

    public static String findValue(String type)
    {
        return find(type).map(AuthAttribute::value).orElse(null);
    }

    public static AuthAttribute get(String value)
    {
        for (AuthAttribute item : AuthAttribute.values())
        {
            if (item.value().equals(value))
            {
                return item;
            }
        }
        return null;
    }
}
