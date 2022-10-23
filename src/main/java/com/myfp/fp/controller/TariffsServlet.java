package com.myfp.fp.controller;

import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TariffsServlet", value = "/tariffs")
public class TariffsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            List<Tariff> tariffs = tariffService.findAll();
            request.setAttribute("tariffs", tariffs);
            request.getRequestDispatcher("tariffs.jsp").forward(request, response);
        } catch (FactoryException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
