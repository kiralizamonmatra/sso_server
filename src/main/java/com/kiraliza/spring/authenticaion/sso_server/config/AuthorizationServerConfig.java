package com.kiraliza.spring.authenticaion.sso_server.config;

import com.kiraliza.spring.authenticaion.sso_server.controller.CustomAccessDeniedHandler;
import com.kiraliza.spring.authenticaion.sso_server.controller.CustomAuthenticationFailureHandler;
import com.kiraliza.spring.authenticaion.sso_server.controller.CustomAuthenticationSuccessHandler;
import com.kiraliza.spring.authenticaion.sso_server.to.TokenInfoTO;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CorsFilter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig
{
    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Autowired
    private FilterRegistrationBean<CorsFilter> corsFilter;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private OAuth2AuthorizationService oauth2AuthorizationService;

    @Autowired
    private JsonMapper jsonMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception
    {
//        http
//            .oauth2AuthorizationServer(authorizationServer -> authorizationServer
//                .oidc(Customizer.withDefaults())
//            )
//            .authorizeHttpRequests((authorize) -> authorize
////                .requestMatchers("/oauth2/**", "/login", "/error").permitAll()
//                .anyRequest().authenticated()
//            )
//            .formLogin(Customizer.withDefaults())
//            .exceptionHandling(exceptions ->
//                {
//                    exceptions.accessDeniedHandler(accessDeniedHandler());
////                    exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
//                }
//            )
//        ;

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.tokenIntrospectionEndpoint((config) -> {
            config.introspectionResponseHandler(this::introspectionResponse);
        });

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http
            .securityMatcher(endpointsMatcher)
            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/error").permitAll()
//                .requestMatchers("/oauth2/**", "/login/**").authenticated()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
            .apply(authorizationServerConfigurer);

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler()
    {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler()
    {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler()
    {
        return new CustomAccessDeniedHandler();
    }

//    private Consumer<List<AuthenticationProvider>> configureJwtClientAssertionValidator()
//    {
//        return (authenticationProviders) ->
//            authenticationProviders.forEach((authenticationProvider) -> {
//                if (authenticationProvider instanceof JwtClientAssertionAuthenticationProvider)
//                {
//                    JwtClientAssertionDecoderFactory jwtDecoderFactory = new JwtClientAssertionDecoderFactory();
//                    Function<RegisteredClient, OAuth2TokenValidator<Jwt>> jwtValidatorFactory = (registeredClient) ->
//                        new DelegatingOAuth2TokenValidator<>(JwtClientAssertionDecoderFactory.DEFAULT_JWT_VALIDATOR_FACTORY.apply(registeredClient), new JwtClaimValidator<>("claim", "value"::equals));
//                    jwtDecoderFactory.setJwtValidatorFactory(jwtValidatorFactory);
//
//                    ((JwtClientAssertionAuthenticationProvider) authenticationProvider).setJwtDecoderFactory(jwtDecoderFactory);
//                }
//            });
//    }

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

    private void introspectionResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException
    {
        OAuth2TokenIntrospectionAuthenticationToken introspectionAuthenticationToken = (OAuth2TokenIntrospectionAuthenticationToken) authentication;
        TokenInfoTO tokenInfo = new TokenInfoTO().setActive(false);

        if (introspectionAuthenticationToken.getTokenClaims().isActive())
        {
            OAuth2TokenIntrospection claims = introspectionAuthenticationToken.getTokenClaims();
            tokenInfo.init(claims);

            String token = introspectionAuthenticationToken.getToken();
            OAuth2Authorization tokenAuth = oauth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
            if (tokenAuth != null)
            {
                Authentication attributeAuth = tokenAuth.getAttribute("java.security.Principal");
                if (attributeAuth != null)
                {
                    tokenInfo
                        .setPrincipal(attributeAuth.getPrincipal())
                        .setAuthorities(authentication.getAuthorities());
                }
            }
        }

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response))
        {
            jsonMapper.writeValue(httpResponse.getServletResponse()
                .getWriter(), tokenInfo);
        }
    }
}
