package com.kiraliza.spring.authenticaion.sso_server;

import com.kiraliza.spring.authenticaion.sso_server.model.PersonName;
import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.repository.UserRepository;
import com.kiraliza.spring.authenticaion.sso_server.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class ApplicationSeed implements CommandLineRunner
{
    @Value("${auth.server.repository.client.name}")
    private String clientName;

    @Value("${auth.server.repository.client.id}")
    private String clientId;

    @Value("${auth.server.repository.client.secret}")
    private String clientSecret;

    @Value("${auth.server.repository.redirect.uri}")
    private String redirectUri;

    @Autowired
    private RegisteredClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args)
    {
        RegisteredClient existClient = clientRepository.findByClientId(clientId);
        if (existClient == null)
        {
            RegisteredClient client = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(clientName)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
    //                .scope("client.create")
    //                .scope("client.read")
                .clientSettings(ClientSettings
                    .builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(false)
                    .build())
                .build();
            clientRepository.save(client);
        }

        Optional<User> existUser = userRepository.findByUsername("test_user_1");
        if (existUser.isEmpty())
        {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.MANAGER.name()), new SimpleGrantedAuthority(Role.ADMIN.name()));
            User user = new User()
                .setUsername("test_user_1")
                .setPassword(passwordEncoder.encode("test123"))
                .setEmail("test_user_1@kiraliza.ru")
                .setPersonName(new PersonName().setFirstName("Albert").setLastName("Einstein"))
                .setBirthOfDate(Instant.parse("1879-03-14T00:00:00Z"))
                .setRoles(Set.of(Role.MANAGER, Role.ADMIN))
                .setRegistrationDate(Instant.now())
            ;
            userRepository.save(user);
        }
    }
}