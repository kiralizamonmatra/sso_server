package com.kiraliza.spring.authenticaion.sso_server.service;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import com.kiraliza.spring.authenticaion.sso_server.model.LoggedInUser;
import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        LogHelper.info("======= LOGIN[" + username + "] =======");
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
        {
            LogHelper.info("======= NOT FOUND =======");
            throw new UsernameNotFoundException(String.format("Username '%s' not found", username));
        }

        List<SimpleGrantedAuthority> authorities = user.get().getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
        return new LoggedInUser(username, user.get().getPassword(), authorities, user.get());
    }
}
