package com.kiraliza.spring.authenticaion.sso_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegisteredClientService
{
    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Transactional
    public void save(RegisteredClient client)
    {
        registeredClientRepository.save(client);
    }

    @Transactional
    public void findByClientId(String clientId)
    {
        registeredClientRepository.findByClientId(clientId);
    }

    @Transactional
    public boolean isExists(String clientId)
    {
        return registeredClientRepository.findByClientId(clientId) != null;
    }
}
