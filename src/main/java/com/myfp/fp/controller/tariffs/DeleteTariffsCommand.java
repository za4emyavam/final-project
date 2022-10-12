package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.service.impl.TariffServiceImpl;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteTariffsCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                TariffService tariffService = getServiceFactory().getTariffService();
                tariffService.deleteById((long) Integer.parseInt(id));
            } catch (FactoryException | ServiceException e) {
                throw new RuntimeException(e);
            }
        }
        return new Forward("/tariffs.html");
    }
}
