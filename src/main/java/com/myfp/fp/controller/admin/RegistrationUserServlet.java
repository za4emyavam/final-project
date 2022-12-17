package com.myfp.fp.controller.admin;

import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MailReport;
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
            //check is email already in use
            if (userService.isUserExist(req.getParameter("email")) == 0) {
                userService.create(user);
                sendEmail(req);
                resp.sendRedirect("/admin/users");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/registration?message=admin.registration.incorrect.email");
            }
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

    private void sendEmail(HttpServletRequest request) {
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String pass = request.getParameter("password");
        MailReport.registrationMail(email, firstname, pass);
    }
}
