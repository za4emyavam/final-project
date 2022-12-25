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

/**
 * The SecurityFilter is a filter class that is used to implement access control in a JakartaEE web application.
 * <p>
 * It checks if a user has the required role to access a particular URL in the application. If the user does not have
 * <p>
 * the required role, they are redirected to the index page.
 */
public class SecurityFilter implements Filter {
    private static final Logger LOG4J = LogManager.getLogger(SecurityFilter.class);
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    /*
     Static block to initialize the permissions map with the required roles for each URL in the application.
     */
    static {
        Set<Role> user = new HashSet<>(Arrays.asList(Role.values()));
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
        permissions.put("/admin/registration", manager);
    }

    /**

     Initializes the filter.
     @param filterConfig the filter configuration
     @throws ServletException if an exception occurs while initializing the filter
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Filters incoming requests to check if the user has permission to access the requested URL.
     * If the user has permission, the request is passed through the filter chain.
     * If the user does not have permission, they are redirected to the index page.
     * @param request the servlet request
     * @param response the servlet response
     * @param chain the filter chain
     * @throws IOException if there is an I/O error
     * @throws ServletException if there is a servlet error
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG4J.info("SecurityFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        Set<Role> role = permissions.get(url);
        if (role != null) {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("currentUser");
                if (user != null && role.contains(user.getUserRole())) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        } else {
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
