package com.kzq.advance.common.utils;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VisitorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //请求的页面
        String uri = request.getRequestURI();

        filterChain.doFilter(request, response);

        if (uri.indexOf("/customer") != -1) {
            filterChain.doFilter(request, response);

        }else {
            filterChain.doFilter(request, response);

        }

    }

}
