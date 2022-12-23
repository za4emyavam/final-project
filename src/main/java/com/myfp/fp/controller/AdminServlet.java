package com.myfp.fp.controller;

import com.myfp.fp.entities.Check;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.CheckService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CheckService checkService = MainServiceFactoryImpl.getInstance().getCheckService();
            Check check = checkService.readLast();
            request.setAttribute("check", check);
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
