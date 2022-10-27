package com.myfp.fp.controller;

import com.myfp.fp.entities.Role;
import com.myfp.fp.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Logger LOG4J = LogManager.getLogger(SecurityFilter.class);
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> user = new HashSet<>();
        user.addAll(Arrays.asList(Role.values()));
        Set<Role> manager = new HashSet<>();
        manager.add(Role.ADMIN);
        manager.add(Role.MAIN_ADMIN);
        Set<Role> admin = new HashSet<>();
        admin.add(Role.MAIN_ADMIN);
        permissions.put("/cabinet", user);
        permissions.put("/cabinet/replenish", user);
        permissions.put("/cabinet/history", user);
        permissions.put("/tariffs/request", user);
        permissions.put("/tariffs/add", admin);
        permissions.put("/tariffs/update", admin);
        permissions.put("/tariffs/delete", admin);
        permissions.put("/admin", manager);
        permissions.put("/admin/requests", manager);
        permissions.put("/admin/check_payment", manager);
        permissions.put("/admin/requests/update", manager);
        permissions.put("/admin/users", manager);
        permissions.put("/admin/users/user_info", manager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG4J.info("SecurityFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        Set<Role> role = permissions.get(url);
        if (role != null) {
            HttpSession session = httpServletRequest.getSession(false);
            if(session != null) {
                User user = (User) session.getAttribute("currentUser");
                if(user != null && role.contains(user.getUserRole())) {
                    LOG4J.info("SecurityFilter: has permission to AdminPage");
                    chain.doFilter(request, response);
                    return;
                }
            }
        } else {
            LOG4J.info("SecurityFilter: has permission");
            chain.doFilter(request, response);
            return;
        }
        LOG4J.info("SecurityFilter: user has no permission");
        httpServletResponse.sendRedirect("/index");
    }

    @Override
    public void destroy() {
    }
}
