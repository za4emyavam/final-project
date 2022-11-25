package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddTariffsServlet", value = "/tariffs/add")
public class AddTariffsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServiceService serviceService = MainServiceFactoryImpl.getInstance().getServiceService();
            List<Service> services = serviceService.findAll();
            if (services != null) {
                request.setAttribute("services", services);
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] names = new String[]{request.getParameter("nameUA"), request.getParameter("nameEN")};
        String[] descriptions = new String[]{request.getParameter("descriptionUA"), request.getParameter("descriptionEN")};
        String type = request.getParameter("service");
        int cost = Integer.parseInt(request.getParameter("cost"));
        int frequencyOfPayment = Integer.parseInt(request.getParameter("frequency_of_payment"));
        try {
            ServiceService serviceService = MainServiceFactoryImpl.getInstance().getServiceService();
            Service service = serviceService.findByType(type);

            Tariff tariff = new Tariff();
            tariff.setName(names);
            tariff.setDescription(descriptions);
            tariff.setCost(cost);
            tariff.setFrequencyOfPayment(frequencyOfPayment);
            tariff.setService(service);

            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            tariffService.insertTariff(tariff);
            response.sendRedirect("/tariffs");
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }
}
