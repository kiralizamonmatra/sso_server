package com.kiraliza.spring.authenticaion.sso_server.type;

import java.util.Optional;

public enum AuthProvider
{
    GITHUB("github"),
    GOOGLE("google");

    private final String data;

    AuthProvider(String c)
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

    public static Optional<AuthProvider> find(String data)
    {
        AuthProvider item = null;
        try
        {
            item = AuthProvider.valueOf(data);
        }
        catch(Exception ignored)
        {
        }
        return Optional.ofNullable(item);
    }

    public static String findValue(String type)
    {
        return find(type).map(AuthProvider::value).orElse(null);
    }

    public static AuthProvider get(String value)
    {
        for (AuthProvider item : AuthProvider.values())
        {
            if (item.value().equals(value))
            {
                return item;
            }
        }
        return null;
    }
}
