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
        if(isGood(name) && isGood(description)) {
            int cost = Integer.parseInt(req.getParameter("cost"));
            int frequencyOfPayment = Integer.parseInt(req.getParameter("frequency_of_payment"));
            try {
                TariffService tariffService = getServiceFactory().getTariffService();
                Tariff oldTariff = tariffService.findById((long) id);
                Tariff newTariff = new Tariff();
                newTariff.setId(oldTariff.getId());
                newTariff.setName(name);
                newTariff.setDescription(description);
                newTariff.setCost(cost);
                newTariff.setFrequencyOfPayment(frequencyOfPayment);
                newTariff.setType(oldTariff.getType());
                tariffService.update(newTariff);
                return new Forward("/tariffs.html");
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
