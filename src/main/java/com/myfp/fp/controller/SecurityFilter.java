package com.myfp.fp.controller;

import com.myfp.fp.entities.Role;
import com.myfp.fp.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> user = new HashSet<>();
        user.addAll(Arrays.asList(Role.values()));
        Set<Role> manager = new HashSet<>();
        manager.add(Role.USER);
        manager.add(Role.MANAGER);
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        /*permissions.put("/", user);
        permissions.put("/index", user);*/
        /*permissions.put("/login", user);
        permissions.put("/logout", user);*/
        permissions.put("/tariffs/add", admin);
        permissions.put("/tariffs/update", admin);
        permissions.put("/tariffs/delete", admin);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecurityFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        String context = httpServletRequest.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if(postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        Set<Role> role = permissions.get(url);
        if (role != null) {
            HttpSession session = httpServletRequest.getSession(false);
            if(session != null) {
                User user = (User) session.getAttribute("currentUser");
                if(user != null && role.contains(user.getRole())) {
                    System.out.println("Filter: AdminPage");
                    //httpServletResponse.sendRedirect(context + url + ".html");
                    chain.doFilter(request, response);
                    return;
                }
            }
        } else {
            System.out.println("Filter: didn't find command");
            chain.doFilter(request, response);
            return;
        }
        System.out.println("Filter: to index.html");
        httpServletResponse.sendRedirect(context + "/index.html");
    }

    @Override
    public void destroy() {
    }
}
