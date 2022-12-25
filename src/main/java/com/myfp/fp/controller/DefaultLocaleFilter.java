package com.myfp.fp.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "DefaultLocaleFilter", urlPatterns = {"/*"})
public class DefaultLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("language") != null) {
            switch(req.getParameter("language")) {
                case "en":
                case "ua":
                    req.getSession().setAttribute("language", req.getParameter("language"));
                    break;
                default:
                    req.getSession().setAttribute("language", "ua");
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}
