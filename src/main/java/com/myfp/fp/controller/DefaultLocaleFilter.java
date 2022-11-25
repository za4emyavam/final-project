package com.myfp.fp.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "DefaultLocaleFilter", urlPatterns = {"/*"})
public class DefaultLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
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
            System.out.println(req.getParameter("id"));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private Map<String, String> getParams(String params) {
        List<String> keyValue = Arrays.asList(params.split("&"));
        Map<String, String> paramsMap = new HashMap<>();
        for (String param :
                keyValue) {
            paramsMap.put(param.split("=")[0], param.split("=")[1]);
        }
        return paramsMap;
    }
}
