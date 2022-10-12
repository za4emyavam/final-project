package com.myfp.fp.controller;

import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(isGood(email) && isGood(login) && isGood(password)) {
            try {
                UserService userService = getServiceFactory().getUserService();
                //userService.isUserExist(email, login) -> true --> new Forward("/registration.html?message=user with this email/login exist");
                userService.addNewUser(email, login, password);
                req.getSession().setAttribute("currentUser", userService.findByLoginAndPassword(login, password));
                return new Forward("/index.html");
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        return null;
    }

    private boolean isGood(String s) {
        return s != null && !s.equals("");
    }
}
