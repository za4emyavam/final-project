package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.TariffStatus;
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

@WebServlet(name = "UpdateTariffsServlet", value = "/tariffs/update")
public class UpdateTariffsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request, response);
        if (id == -1)
            response.sendRedirect("/index");
        try {
            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            Tariff currentTariff = tariffService.findById((long) id);
            if (currentTariff != null) {
                request.setAttribute("currentTariff", currentTariff);
            } else {
                response.sendRedirect("/index");
            }
            ServiceService serviceService = MainServiceFactoryImpl.getInstance().getServiceService();
            List<Service> services = serviceService.findAll();
            if (services != null) {
                request.setAttribute("services", services);
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request, response);
        if (id == -1)
            response.sendRedirect("/index");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String type = request.getParameter("service");
        String status = request.getParameter("status");
        int cost = Integer.parseInt(request.getParameter("cost"));
        int frequencyOfPayment = Integer.parseInt(request.getParameter("frequency_of_payment"));

        try {
            ServiceService serviceService = MainServiceFactoryImpl.getInstance().getServiceService();
            Service service = serviceService.findByType(type);
            System.out.println("service type ->" + service.getServiceType());

            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            Tariff oldTariff = tariffService.findById((long) id);
            Tariff newTariff = new Tariff();
            newTariff.setId(oldTariff.getId());
            newTariff.setName(name);
            newTariff.setDescription(description);
            newTariff.setCost(cost);
            newTariff.setFrequencyOfPayment(frequencyOfPayment);
            newTariff.setService(service);
            System.out.println(status);
            newTariff.setTariffStatus(TariffStatus.fromString(status));
            System.out.println(newTariff.getTariffStatus().getName());
            tariffService.update(newTariff);
            response.sendRedirect("/tariffs");
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

    private int getId(HttpServletRequest request, HttpServletResponse response) {
        String sId = request.getParameter("id");
        int id = -1;
        if (sId != null && !sId.equals("")) {
            id = Integer.parseInt(sId);
            request.setAttribute("id", id);
        }
        return id;
    }
}