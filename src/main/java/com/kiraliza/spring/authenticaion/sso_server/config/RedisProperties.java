package com.kiraliza.spring.authenticaion.sso_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.spring.redis")
public class RedisProperties
{
    private String clientType;
    private Integer port;

    private String host;

    private String password;

    public String getClientType()
    {
        return clientType;
    }

    public RedisProperties setClientType(String clientType)
    {
        this.clientType = clientType;
        return this;
    }

    public Integer getPort()
    {
        return port;
    }

    public RedisProperties setPort(Integer port)
    {
        this.port = port;
        return this;
    }

    public String getHost()
    {
        return host;
    }

    public RedisProperties setHost(String host)
    {
        this.host = host;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public RedisProperties setPassword(String password)
    {
        this.password = password;
        return this;
    }
}
