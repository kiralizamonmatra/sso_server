package com.kiraliza.spring.authenticaion.sso_server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.Base64;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OAuth2AuthorizeTests
{
    private static final String USER = "test_user_1";
    private static final String PASSWORD = "test123";
    private static final String USER_ROLE = "MANAGER";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    public void oauth2AuthorizeJWTWithoutLoginWithProof() throws Exception
    {
        String codeVerifier = "someRandomString123456789someRandomString123456789";
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );

        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-jwt-proof-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .header("Accept", "text/html")
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertEquals("/login", location);
    }

    @Test
    public void oauth2AuthorizeJWTWithLoginWithProof() throws Exception
    {
        String codeVerifier = "someRandomString123456789someRandomString123456789";
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );

        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-jwt-proof-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .with(user(USER).password(PASSWORD).roles(USER_ROLE))
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertTrue(StringUtils.hasText(location));
        Assertions.assertTrue(location.startsWith("http://localhost:5173/code?code="));
    }

    @Test
    public void oauth2AuthorizeJWTWithoutLoginWithoutProof() throws Exception
    {
        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-jwt-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .header("Accept", "text/html")
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertEquals("/login", location);
    }

    @Test
    public void oauth2AuthorizeOpaqueWithoutLoginWithProof() throws Exception
    {
        String codeVerifier = "someRandomString123456789someRandomString123456789";
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );

        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-opaque-proof-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .header("Accept", "text/html")
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertEquals("/login", location);
    }

    @Test
    public void oauth2AuthorizeOpaqueWithLoginWithProof() throws Exception
    {
        String codeVerifier = "someRandomString123456789someRandomString123456789";
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );

        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-opaque-proof-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .with(user(USER).password(PASSWORD).roles(USER_ROLE))
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertTrue(StringUtils.hasText(location));
        Assertions.assertTrue(location.startsWith("http://localhost:5173/code?code="));
    }

    @Test
    public void oauth2AuthorizeOpaqueWithoutLoginWithoutProof() throws Exception
    {
        MockHttpServletResponse response = mvc.perform(get("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-opaque-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .header("Accept", "text/html")
            )
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        Assertions.assertEquals("/login", location);
    }
}
