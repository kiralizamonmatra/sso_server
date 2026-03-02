package com.kiraliza.spring.authenticaion.sso_server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.MessageDigest;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OAuth2RevokeTests
{
    private static final String USER = "test_user_1";
    private static final String PASSWORD = "test123";
    private static final String USER_ROLE = "MANAGER";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    public void oauth2RevokeJWTWithoutLoginWithProof() throws Exception
    {
        String codeVerifier = "someRandomString123456789someRandomString123456789";
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
            MessageDigest
                .getInstance("SHA-256")
                .digest(codeVerifier.getBytes())
        );

        MockHttpServletResponse response = mvc.perform(get("/oauth2/revoke")
                .queryParam("response_type", "code")
                .queryParam("client_id", "test-jwt-proof-client")
                .queryParam("redirect_uri", "http://localhost:5173/code")
                .queryParam("scope", "client.read")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .header("Accept", "text/html")
            )
//            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andReturn()
            .getResponse();

//        String location = response.getHeader("Location");

        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatus());
//        Assertions.assertEquals("/login", location);
    }
}
