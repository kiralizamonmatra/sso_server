package com.kiraliza.spring.authenticaion.sso_server;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OAuth2IntrospectTests
{
    private static final String USER = "test_user_1";
    private static final String PASSWORD = "test123";
    private static final String USER_ROLE = "MANAGER";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    public void oauth2IntrospectWithProof() throws Exception
    {
        final String clientId = "test-jwt-proof-client";
        final String secretId = "test-jwt-proof-client";
        final String scopes = "client.read";

        String response = mvc.perform(post("/oauth2/token")
                .param("grant_type", "custom_password")
                .param("username", USER)
                .param("password", PASSWORD)
                .header("Authorization", generateBasicAuthHeader(clientId, secretId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").isNotEmpty())
            .andExpect(jsonPath("$.refresh_token").isNotEmpty())
            .andExpect(jsonPath("$.token_type").value("Bearer"))
            .andReturn()
            .getResponse()
            .getContentAsString();

        JSONObject jsonObject = new JSONObject(response);

        Jwt jwt = jwtDecoder.decode(jsonObject.getString(OAuth2ParameterNames.ACCESS_TOKEN));

        MockHttpServletResponse response2 = mvc.perform(post("/oauth2/introspect")
                .header("Authorization", generateBasicAuthHeader(clientId, secretId))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("token", jwt.getTokenValue()))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").isBoolean())
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
            .andDo(print())
            .andReturn()
            .getResponse();

        JSONObject introspectedJsonObject = new JSONObject(response2.getContentAsString());

        Assertions.assertNotNull(response2);
        Assertions.assertEquals(HttpStatus.OK.value(), response2.getStatus());
        Assertions.assertNotNull(introspectedJsonObject.get("active"));
        Assertions.assertEquals(introspectedJsonObject.getBoolean("active"), true);

        Assertions.assertEquals(clientId, jwt.getClaim("sub").toString());
        Assertions.assertEquals(clientId, introspectedJsonObject.getString("sub"));
        Assertions.assertEquals(USER, introspectedJsonObject.getString("user"));
        Assertions.assertEquals("Bearer", introspectedJsonObject.getString("token_type"));
        Assertions.assertEquals("Test Access Token", introspectedJsonObject.getString("Test"));
    }

    private String generateBasicAuthHeader(String clientId, String secretId)
    {
        return "Basic %s".formatted(Base64.getEncoder().encodeToString(("%s:%s".formatted(clientId, secretId)).getBytes()));
    }

}
