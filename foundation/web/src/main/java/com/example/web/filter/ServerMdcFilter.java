package com.example.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class ServerMdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("MDC", UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        chain.doFilter(request, response);
    }
}
