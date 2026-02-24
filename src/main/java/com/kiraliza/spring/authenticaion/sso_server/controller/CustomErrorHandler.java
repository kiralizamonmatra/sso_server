package com.kiraliza.spring.authenticaion.sso_server.controller;

import com.kiraliza.spring.authenticaion.sso_server.helper.LogHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Authentication authentication)
    {
        LogHelper.error("=================ERROR:::");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (authentication != null)
        {
            LogHelper.info("==name:" + authentication.getName());
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            authorities.forEach(a -> LogHelper.info("ROLE:" + a.getAuthority()));
        }

        if (status != null)
        {
            String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
            Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            LogHelper.error("\t\t:" + message);
            if (exception != null)
            {
                LogHelper.error(exception);
            }
            LogHelper.error("=================STATUS:::" + status);
            int statusCode = Integer.parseInt(status.toString());
            LogHelper.error("=================ERROR_MESSAGE:::" + request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

            if (statusCode == HttpStatus.NOT_FOUND.value())
            {
                LogHelper.error("=================NOT_FOUND");
                return "error/404";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
            {
                LogHelper.error("=================INTERNAL_SERVER_ERROR");
                model.addAttribute("errorMessage", StringUtils.hasText(message) ? message : (exception != null ? exception.getMessage() : ""));
                return "error/500";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value())
            {
                LogHelper.error("=================FORBIDDEN");
                return "error/403";
            }
        }

        LogHelper.error("=================NOT_FOUND");
        return "error/error";
    }
}