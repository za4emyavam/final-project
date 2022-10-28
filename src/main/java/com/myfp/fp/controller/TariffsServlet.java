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
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        try {
            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            List<Tariff> tariffs = tariffService.findAll(recordsPerPage, (page - 1) * recordsPerPage);
            int noOfRecords = tariffService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("tariffs", tariffs);

            request.getRequestDispatcher("tariffs.jsp").forward(request, response);
        } catch (FactoryException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
