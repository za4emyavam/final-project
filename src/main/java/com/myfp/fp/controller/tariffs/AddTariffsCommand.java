package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AddTariffsCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("service");
        if(isGood(name) && isGood(description) && isGood(type)) {
            int cost = Integer.parseInt(req.getParameter("cost"));
            int frequencyOfPayment = Integer.parseInt(req.getParameter("frequency_of_payment"));
            try {
                ServiceService serviceService = getServiceFactory().getServiceService();
                Service service = serviceService.findByType(type);
                System.out.println("service type ->" + service.getServiceType());

                Tariff tariff = new Tariff();
                tariff.setName(name);
                tariff.setDescription(description);
                tariff.setCost(cost);
                tariff.setFrequencyOfPayment(frequencyOfPayment);
                tariff.setService(service);

                TariffService tariffService = getServiceFactory().getTariffService();
                tariffService.insertTariff(tariff);
                return new Forward("/tariffs.html");
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            try {
                ServiceService serviceService = getServiceFactory().getServiceService();
                List<Service> services = serviceService.findAll();
                if (services != null) {
                    req.setAttribute("services", services);
                }
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        return null;
        //return new Forward("/tariffs");
    }

    private boolean isGood(String str) {
        return str != null && !str.equals("");
    }
}
