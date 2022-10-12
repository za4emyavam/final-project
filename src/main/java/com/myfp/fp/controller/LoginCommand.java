package com.myfp.fp.controller;

import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login != null && password != null) {
            try {
                UserService userService = getServiceFactory().getUserService();
                User user = userService.findByLoginAndPassword(login, password);
                if(user != null) {
                    req.getSession().setAttribute("currentUser", user);
                    return new Forward("/index.html");
                } else {
                    return new Forward("/login.html?message=login.message.incorrect.password");
                }
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        return null;
    }
}
