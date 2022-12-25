package com.myfp.fp.controller.tariffs;

import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeleteTariffsServlet", value = "/tariffs/delete")
public class DeleteTariffsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
                tariffService.deleteById((long) Integer.parseInt(id));
            } catch (FactoryException | ServiceException e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect("/tariffs");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
