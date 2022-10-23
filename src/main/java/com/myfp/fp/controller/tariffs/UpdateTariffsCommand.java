package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.TariffStatus;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UpdateTariffsCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String sId = req.getParameter("id");
        int id = -1;
        if (sId == null || sId.equals("")) {
            return new Forward("/");
        } else {
            id = Integer.parseInt(sId);
            req.setAttribute("id", id);
        }
        String type = req.getParameter("service");
        String status = req.getParameter("status");
        if(isGood(name) && isGood(description)) {
            int cost = Integer.parseInt(req.getParameter("cost"));
            int frequencyOfPayment = Integer.parseInt(req.getParameter("frequency_of_payment"));
            try {
                ServiceService serviceService = getServiceFactory().getServiceService();
                Service service = serviceService.findByType(type);
                System.out.println("service type ->" + service.getServiceType());

                TariffService tariffService = getServiceFactory().getTariffService();
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
                return new Forward("/tariffs.html");
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            try {
                TariffService tariffService = getServiceFactory().getTariffService();
                Tariff currentTariff = tariffService.findById((long) id);
                if (currentTariff != null) {
                    req.setAttribute("currentTariff", currentTariff);
                } else {
                    return new Forward("/");
                }
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
    }

    private boolean isGood(String str) {
        return str != null && !str.equals("");
    }
}
