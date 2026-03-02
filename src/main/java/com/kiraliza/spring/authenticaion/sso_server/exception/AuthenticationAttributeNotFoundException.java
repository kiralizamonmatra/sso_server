package com.kiraliza.spring.authenticaion.sso_server.exception;

import com.kiraliza.spring.authenticaion.sso_server.type.AuthAttribute;

public class AuthenticationAttributeNotFoundException extends RuntimeException
{
    private String attributeName;

    public AuthenticationAttributeNotFoundException(String message)
    {
        super(message);
    }

    public AuthenticationAttributeNotFoundException(AuthAttribute attribute)
    {
        super("Authentication failed. Attribute[%s] not found".formatted(attribute.name()));
    }

    public String getAttributeName()
    {
        return attributeName;
    }
}
