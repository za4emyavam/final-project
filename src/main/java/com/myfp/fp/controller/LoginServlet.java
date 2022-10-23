package com.myfp.fp.controller;

import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login != null && password != null) {
            try {
                UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
                User user = userService.findByLoginAndPassword(login, password);
                if(user != null) {
                    req.getSession().setAttribute("currentUser", user);
                    resp.sendRedirect("/index");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login?message=login.message.incorrect.password");
                }
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
    }
}
