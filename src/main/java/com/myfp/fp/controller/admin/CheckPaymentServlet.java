package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Forward;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CheckPaymentServlet", value = "/admin/check_payment")
public class CheckPaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserTariffsService userTariffsService = MainServiceFactoryImpl.getInstance().getUserTariffsService();
            userTariffsService.checkPaymentOfAllUsers();
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("/admin");
    }
}
