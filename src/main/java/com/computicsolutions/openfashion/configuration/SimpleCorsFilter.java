package com.computicsolutions.openfashion.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cors Filter
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String HTTP_METHOD_OPTIONS = "OPTIONS";
    private final String allowedOrigins;
    private final String allowedMethods;
    private final String maxAge;
    private final String allowedHeaders;

    public SimpleCorsFilter(@Value("${accessControl.allowedOrigin}") String allowedOrigins,
                            @Value("${accessControl.allowedMethods}") String allowedMethods,
                            @Value("${accessControl.maxAge}") String maxAge,
                            @Value("${accessControl.allowedHeaders}") String allowedHeaders) {
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.maxAge = maxAge;
        this.allowedHeaders = allowedHeaders;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, allowedMethods);
        response.setHeader(ACCESS_CONTROL_MAX_AGE, maxAge);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowedHeaders);

        if (HTTP_METHOD_OPTIONS.equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(req, res);
        }
    }
}
