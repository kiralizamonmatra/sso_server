package com.kiraliza.spring.authenticaion.sso_server;

import com.kiraliza.spring.authenticaion.sso_server.model.PersonName;
import com.kiraliza.spring.authenticaion.sso_server.model.User;
import com.kiraliza.spring.authenticaion.sso_server.service.RegisteredClientService;
import com.kiraliza.spring.authenticaion.sso_server.service.UserService;
import com.kiraliza.spring.authenticaion.sso_server.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
//@Profile("test")
public class ApplicationSeed implements CommandLineRunner
{
    @Autowired
    private RegisteredClientService registeredClientService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args)
    {
        if (!registeredClientService.isExists("test-jwt-client"))
        {
            RegisteredClient client = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("test-jwt-client")
                .clientName("Test JWT Client")
                .clientSecret(passwordEncoder.encode("test-jwt-client"))
                .redirectUri("http://localhost:5173/code")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scopes(scopes -> scopes.addAll(Set.of("client.read", "client.write")))
                .clientSettings(ClientSettings.builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(false)
                    .build()
                )
                .tokenSettings(TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                    .refreshTokenTimeToLive(Duration.of(300, ChronoUnit.MINUTES))
                    .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                    .reuseRefreshTokens(false)
                    .build()
                )
                .build();
            registeredClientService.save(client);
        }

        if (!registeredClientService.isExists("test-jwt-proof-client"))
        {
            RegisteredClient client = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("test-jwt-proof-client")
                .clientName("Test JWT Proof Client")
                .clientSecret(passwordEncoder.encode("test-jwt-proof-client"))
                .redirectUri("http://localhost:5173/code")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scopes(scopes -> scopes.addAll(Set.of("client.read", "client.write")))
                .clientSettings(ClientSettings.builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(true)
                    .build()
                )
                .tokenSettings(TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                    .refreshTokenTimeToLive(Duration.of(300, ChronoUnit.MINUTES))
                    .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                    .reuseRefreshTokens(false)
                    .build()
                )
                .build();
            registeredClientService.save(client);
        }

        if (!registeredClientService.isExists("test-opaque-client"))
        {
            RegisteredClient client = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("test-opaque-client")
                .clientName("Test Opaque Client")
                .clientSecret(passwordEncoder.encode("test-opaque-client"))
                .redirectUri("http://localhost:5173/code")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scopes(scopes -> scopes.addAll(Set.of("client.read", "client.write")))
                .clientSettings(ClientSettings.builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(false)
                    .build()
                )
                .tokenSettings(TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                    .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                    .refreshTokenTimeToLive(Duration.of(300, ChronoUnit.MINUTES))
                    .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                    .reuseRefreshTokens(false)
                    .build()
                )
                .build();
            registeredClientService.save(client);
        }

        if (!registeredClientService.isExists("test-opaque-proof-client"))
        {
            RegisteredClient client = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("test-opaque-proof-client")
                .clientName("Test Opaque Proof Client")
                .clientSecret(passwordEncoder.encode("test-opaque-proof-client"))
                .redirectUri("http://localhost:5173/code")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scopes(scopes -> scopes.addAll(Set.of("client.read", "client.write")))
                .clientSettings(ClientSettings.builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(true)
                    .build()
                )
                .tokenSettings(TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                    .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                    .refreshTokenTimeToLive(Duration.of(300, ChronoUnit.MINUTES))
                    .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                    .reuseRefreshTokens(false)
                    .build()
                )
                .build();
            registeredClientService.save(client);
        }

        Optional<User> existUser = userService.findByUsername("test_user_1");
        if (existUser.isEmpty())
        {
            User user = new User()
                .setUsername("test_user_1")
                .setPassword(passwordEncoder.encode("test123"))
                .setEmail("test_user_1@kiraliza.ru")
                .setPersonName(new PersonName().setFirstName("Albert").setLastName("Einstein"))
                .setBirthOfDate(Instant.parse("1879-03-14T00:00:00Z"))
                .setRoles(Set.of(Role.MANAGER, Role.ADMIN))
                .setRegistrationDate(Instant.now())
                .setActive(true)
            ;
            userService.save(user);
        }
    }
}