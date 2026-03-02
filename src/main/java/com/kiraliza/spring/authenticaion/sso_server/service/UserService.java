package com.kiraliza.spring.authenticaion.sso_server.service;

import com.kiraliza.spring.authenticaion.sso_server.exception.AuthenticationAttributeNotFoundException;
import com.kiraliza.spring.authenticaion.sso_server.model.PersonName;
import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.repository.UserRepository;
import com.kiraliza.spring.authenticaion.sso_server.type.AuthAttribute;
import com.kiraliza.spring.authenticaion.sso_server.type.AuthProvider;
import com.kiraliza.spring.authenticaion.sso_server.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public User save(OAuth2User user, AuthProvider provider)
    {
        return switch(provider)
        {
            case GITHUB -> saveFromGithab(user);
            case GOOGLE -> saveFromGoogle(user);
        };
    }

    private User saveFromGithab(OAuth2User oauth2User)
    {
        String email = oauth2User.getAttribute("email");
        if (!StringUtils.hasText(email))
        {
            throw new AuthenticationAttributeNotFoundException(AuthAttribute.EMAIL);
        }

        User user = this.userRepository.findByEmail(email);
        if (user == null)
        {
            user = new User()
                .setEmail(email)
                .setUsername(email)
                .setActive(true)
                .setRoles(Set.of(Role.USER))
            ;
        }

        if (oauth2User.getAttribute("name") != null)
        {
            user.setPersonName(PersonName.parse(oauth2User.getAttribute("name")));
        }

        return userRepository.save(user);
    }

    private User saveFromGoogle(OAuth2User oauth2User)
    {
        String email = oauth2User.getAttribute("email");
        if (!StringUtils.hasText(email))
        {
            throw new AuthenticationAttributeNotFoundException(AuthAttribute.EMAIL);
        }

        User user = this.userRepository.findByEmail(email);
        if (user == null)
        {
            user = new User()
                .setEmail(email)
                .setUsername(email)
                .setRoles(Set.of(Role.USER))
                .setActive(true);
        }

        if (oauth2User.getAttribute("given_name") != null)
        {
            user.getPersonName().setFirstName(oauth2User.getAttribute("given_name"));
        }

        if (oauth2User.getAttribute("family_name") != null)
        {
            user.getPersonName().setLastName(oauth2User.getAttribute("family_name"));
        }

        return userRepository.save(user);
    }
}
