package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CheckPaymentCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserTariffsService userTariffsService = getServiceFactory().getUserTariffsService();
            userTariffsService.checkPaymentOfAllUsers();
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return new Forward("admin.html");
    }
}
