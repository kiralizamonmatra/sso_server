package com.kiraliza.spring.authenticaion.sso_server.config;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import java.util.UUID;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig
{
    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Value("${auth.server.repository.client.name}")
    private String clientName;

    @Value("${auth.server.repository.client.id}")
    private String clientId;

    @Value("${auth.server.repository.client.secret}")
    private String clientSecret;

    @Value("${auth.server.repository.redirect.uri}")
    private String redirectUri;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http, RegisteredClientRepository registeredClientRepository) throws Exception
    {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http
//            .oauth2AuthorizationServer(authServer ->
//                authServer.oidc(Customizer.withDefaults())
//            )
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, auth -> auth
                .registeredClientRepository(registeredClientRepository)
            )
//            .with(authorizationServerConfigurer, Customizer.withDefaults())
            .exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            )
//            .authorizeHttpRequests(auth -> auth
//                .anyRequest().authenticated()
//            )
//            .formLogin(Customizer.withDefaults())
        ;
        return http.build();
    }

//    @Bean
//    public ApplicationRunner dataLoader(RegisteredClientRepository repository)
//    {
//        LogHelper.info("====clientId: " + clientId);
//        LogHelper.info("====clientName: " + clientName);
//        LogHelper.info("====clientSecret: " + clientSecret);
//        LogHelper.info("====redirectUri: " + redirectUri);
//        return args -> {
//            RegisteredClient client = RegisteredClient
//                .withId(UUID.randomUUID().toString())
//                .clientId(clientId)
//                .clientSecret(clientName)
//                .clientSecret(clientSecret)
//                .redirectUri(redirectUri)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//    //                .scope("client.create")
//    //                .scope("client.read")
//                .build();
//            repository.save(client);
//            // Build and save your RegisteredClient instance here
//        };
//    }

//    @Bean
//    public RegisteredClientRepository registeredClientRepository()
//    {
//        return new InMemoryRegisteredClientRepository(
//            RegisteredClient
//                .withId(UUID.randomUUID().toString())
//                .clientId(clientId)
//                .clientSecret(clientName)
//                .clientSecret(clientSecret)
//                .redirectUri(redirectUri)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                .scope("client.create")
////                .scope("client.read")
//                .build()
//        );
//    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate)
    {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository)
    {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JdbcOAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository)
    {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
    {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
//        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() throws NullPointerException
    {
        return AuthorizationServerSettings.builder()
            .issuer(Objects.requireNonNull(authorizationServerProperties.getIssuer()))
            .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource)
    {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private static KeyPair generateRsaKey()
    {
        KeyPair keyPair;
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
}
