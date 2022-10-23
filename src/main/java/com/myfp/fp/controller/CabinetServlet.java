package com.myfp.fp.controller;

import com.myfp.fp.entities.User;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CabinetServlet", value = "/cabinet")
public class CabinetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            User updatedUser = userService.findById(((User) request.getSession(false).getAttribute("currentUser")).getId());
            request.getSession(false).setAttribute("currentUser", updatedUser);
            UserTariffsService userTariffsService = MainServiceFactoryImpl.getInstance().getUserTariffsService();
            List<UserTariffs> userTariffs = userTariffsService.readAllByUserId(updatedUser.getId());
            if (userTariffs != null && !userTariffs.isEmpty())
                request.setAttribute("userTariffs", userTariffs);
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("cabinet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
