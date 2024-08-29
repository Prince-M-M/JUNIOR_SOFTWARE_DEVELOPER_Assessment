package com.enviro.assessment.grad001.PrinceMataruse.assessment.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Incoming Request: Method: {}, URI: {}, Params: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("Response Status: {}", response.getStatus());

        if (ex != null) {
            logger.error("Request resulted in an exception", ex);
        }
    }





    
}
