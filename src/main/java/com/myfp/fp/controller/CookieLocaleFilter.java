package com.myfp.fp.controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = { "/*" })
public class CookieLocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("Locale Filter");
        HttpServletRequest req = (HttpServletRequest) request;

        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getParameter("cookieLocale") != null) {
            System.out.println("setCookieWithLocale -> " + req.getParameter("cookieLocale"));
            Cookie cookie = new Cookie("lang", req.getParameter("cookieLocale"));
            res.addCookie(cookie);
            res.sendRedirect(req.getContextPath() + req.getRequestURI());
        } else {
            chain.doFilter(request, response);
        }

    }
}
