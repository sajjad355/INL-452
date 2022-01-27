package com.internal.service.somruinternal.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  /**
   * Always returns a 401 error code to the client.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExp)
    throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please login again");
  }
}
