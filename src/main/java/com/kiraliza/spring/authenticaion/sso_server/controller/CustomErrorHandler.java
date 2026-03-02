package com.kiraliza.spring.authenticaion.sso_server.controller;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import com.kiraliza.spring.authenticaion.sso_server.type.Routes;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class CustomErrorHandler implements ErrorController
{
    @Autowired
    private Routes routes;

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Authentication authentication)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (authentication != null)
        {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            authorities.forEach(a -> LogHelper.info("ROLE:" + a.getAuthority()));
        }

        if (status != null)
        {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value())
            {
                return "error/404";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
            {
                String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
                Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                model.addAttribute("errorMessage", StringUtils.hasText(message) ? message : (exception != null ? exception.getMessage() : ""));
                return "error/500";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value())
            {
                return "error/403";
            }
        }

        return "error/error";
    }
}