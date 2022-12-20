package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Check;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.CheckService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.service.impl.CheckServiceImpl;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckPaymentServlet", value = "/admin/check_payment")
public class CheckPaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            if (currentUser != null) {
                UserTariffsService userTariffsService = MainServiceFactoryImpl.getInstance().getUserTariffsService();
                userTariffsService.checkPaymentOfAllUsers(currentUser.getId());
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("/admin");
    }
}
