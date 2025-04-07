package com.thy.route_calculator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${api.key}")
    private String validApiKey;

    private static final String API_KEY_HEADER = "X-API-KEY";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) { // CORS preflight istekleri için
            chain.doFilter(request, response);
            return;
        }

        String apiKey = httpReq.getHeader(API_KEY_HEADER);

        if (validApiKey.equals(apiKey)) {
            chain.doFilter(request, response);
        } else {
            httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
        }
    }
}
