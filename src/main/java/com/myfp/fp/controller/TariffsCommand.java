package com.myfp.fp.controller;

import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.service.impl.TariffServiceImpl;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class TariffsCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TariffService tariffService = getServiceFactory().getTariffService();
            List<Tariff> tariffs = tariffService.findAll();
            req.setAttribute("tariffs", tariffs);
        } catch (FactoryException | ServiceException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
