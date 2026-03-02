package com.kiraliza.spring.authenticaion.sso_server.service;

import com.kiraliza.spring.authenticaion.sso_server.model.LoggedInUser;
import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.type.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService
{
    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Optional<AuthProvider> provider = AuthProvider.find(userRequest.getClientRegistration().getRegistrationId());

        if (provider.isEmpty())
        {
            throw new OAuth2AuthenticationException("OAuth2 Provider not found");
        }

        User newUser = userService.save(oauth2User, provider.get());

        return new LoggedInUser(newUser.getUsername(), newUser.getPassword(), newUser.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toSet()), newUser);
    }
}
