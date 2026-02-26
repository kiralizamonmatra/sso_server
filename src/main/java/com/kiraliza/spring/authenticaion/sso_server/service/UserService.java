package com.kiraliza.spring.authenticaion.sso_server.service;

import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.repository.UserRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void save(User user)
    {
        userRepository.save(user);
    }
}
