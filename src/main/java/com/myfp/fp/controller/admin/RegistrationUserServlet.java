package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Forward;
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

@WebServlet(name = "RegistrationUserServlet", value = "/admin/registration")
public class RegistrationUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = fillUser(req);
        try {
            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            //userService.isUserExist(email, login) -> true --> new Forward("/registration.html?message=user with this email/login exist");
            userService.create(user);
            resp.sendRedirect("/admin/users");
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

    private User fillUser(HttpServletRequest request) {
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setFirstname(request.getParameter("firstname"));
        user.setMiddleName(request.getParameter("middle_name"));
        user.setSurname(request.getParameter("surname"));
        user.setTelephoneNumber(request.getParameter("telephone_number"));
        user.setPass(request.getParameter("password"));
        return user;
    }

    private boolean isGood(String s) {
        return s != null && !s.equals("");
    }
}
