package com.kiraliza.spring.authenticaion.sso_server.controller;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import com.kiraliza.spring.authenticaion.sso_server.type.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController
{
    @GetMapping(Routes.LOGIN)
    public String login()
    {
        LogHelper.info("============ DO LOGIN");
        return "login";
    }

//    @PostMapping
//    public String login()
//    {
//        return Routes.LOGIN;
//    }
}
