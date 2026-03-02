package com.kiraliza.spring.authenticaion.sso_server;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OAuth2TokenTests
{
    private static final String REDIRECT_URI = "http://localhost:5173/code";
    private static final String USER = "test_user_1";
    private static final String PASSWORD = "test123";
    private static final String USER_ROLE = "MANAGER";

    private static String codeChallenge;
    private static final String codeVerifier = "someRandomString123456789someRandomString123456789";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtDecoder jwtDecoder;

    @BeforeAll
    public static void initCodeChallenge() throws NoSuchAlgorithmException
    {
        codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );
    }

    @Test
    public void oauth2TokenClientCredentialsJWTWithProof() throws Exception
    {
        final String clientId = "test-jwt-proof-client";
        final String secretId = "test-jwt-proof-client";
        final String scope = "client.read";

        String response = mvc.perform(post("/oauth2/token")
                .param("grant_type", "client_credentials")
                .param("scope", scope)
                .header("Authorization", generateBasicAuthHeader(clientId, secretId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").isNotEmpty())
            .andExpect(jsonPath("$.scope").value(scope))
            .andExpect(jsonPath("$.token_type").value("Bearer"))
            .andReturn()
            .getResponse()
            .getContentAsString();

        JSONObject jsonObject = new JSONObject(response);

        Jwt jwt = jwtDecoder.decode(jsonObject.getString(OAuth2ParameterNames.ACCESS_TOKEN));

        Assertions.assertEquals(clientId, jwt.getClaim("sub").toString());
        Assertions.assertEquals(scope, String.join(" ", Optional
            .ofNullable(jwt.getClaim("scope"))
            .filter(List.class::isInstance)
            .map(obj -> ((List<?>) obj).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList())
            .orElse(Collections.emptyList())));
    }

    @Test
    public void oauth2TokenAuthorizationCodeJWTWithProofBadRequest() throws Exception
    {
        String authorizationCode = "valid-auth-code";
        String expectedResponseContent = "{\"error\":\"invalid_grant\"}";
        final String clientId = "test-jwt-proof-client";
        final String secretId = "test-jwt-proof-client";

        MockHttpServletResponse response = mvc.perform(post("/oauth2/token")
                .header("Authorization", generateBasicAuthHeader(clientId, secretId))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("grant_type", "authorization_code")
                .param("code", authorizationCode)
                .param("redirect_uri", REDIRECT_URI))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andDo(print())
            .andReturn()
            .getResponse();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getContentAsString());
        Assertions.assertEquals(expectedResponseContent, response.getContentAsString());
    }

    @Test
    public void oauth2TokenAuthorizationCodeJWTWithProofOk() throws Exception
    {
        final String clientId = "test-jwt-proof-client";
        final String secretId = "test-jwt-proof-client";
        final String scopes = "client.read";
        String authorizationCode = generateAuthorizationCode(clientId, scopes);

        MockHttpServletResponse authorizationCodeResponse = mvc.perform(post("/oauth2/token")
                .header("Authorization", generateBasicAuthHeader(clientId, secretId))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("grant_type", "authorization_code")
                .param("code", authorizationCode)
                .param("redirect_uri", REDIRECT_URI)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.access_token").exists())
            .andExpect(jsonPath("$.token_type").value("Bearer"))
            .andDo(print())
            .andReturn()
            .getResponse();

        JSONObject jsonResponse = new JSONObject(authorizationCodeResponse.getContentAsString());
        Jwt jwt = jwtDecoder.decode(jsonResponse.getString(OAuth2ParameterNames.ACCESS_TOKEN));

        Assertions.assertNotNull(jsonResponse.getString("access_token"));
        Assertions.assertNotNull(authorizationCodeResponse);
        Assertions.assertNotNull(authorizationCodeResponse.getContentAsString());
        Assertions.assertTrue(jwt.getClaim("sub").toString().contains(USER));
    }

    private String generateBasicAuthHeader(String clientId, String secretId)
    {
        return "Basic %s".formatted(Base64.getEncoder().encodeToString(("%s:%s".formatted(clientId, secretId)).getBytes()));
    }

    private String generateAuthorizationCode(String clientId, String scopes) throws Exception
    {
        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .with(csrf())
                .with(user(USER).password(PASSWORD).roles(USER_ROLE)) // Add authentication
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .queryParam("scope", scopes))
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertTrue(StringUtils.hasText(location));
        Assertions.assertTrue(location.contains("code="));

        return location.split("code=")[1];
    }
}
