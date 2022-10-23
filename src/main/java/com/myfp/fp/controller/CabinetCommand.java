package com.myfp.fp.controller;

import com.myfp.fp.entities.User;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CabinetCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserService userService = getServiceFactory().getUserService();
            User updatedUser = userService.findById(((User) req.getSession(false).getAttribute("currentUser")).getId());
            req.getSession(false).setAttribute("currentUser", updatedUser);
            UserTariffsService userTariffsService = getServiceFactory().getUserTariffsService();
            List<UserTariffs> userTariffs = userTariffsService.readAllByUserId(updatedUser.getId());
            if (userTariffs != null && !userTariffs.isEmpty())
                req.setAttribute("userTariffs", userTariffs);
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
