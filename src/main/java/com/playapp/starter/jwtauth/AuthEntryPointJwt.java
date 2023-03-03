package com.playapp.starter.jwtauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * we create AuthEntryPointJwt class that implements AuthenticationEntryPoint interface. Then we override the commence() method.
 * This method will be triggerd anytime unauthenticated User requests a secured HTTP resource and an AuthenticationException is thrown.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

    private static final Logger AuthEntryPointJwtLogger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        AuthEntryPointJwtLogger.error("Unauthorized " + authException.getMessage());
        // HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code. It indicates that the request requires HTTP authentication.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
	
}