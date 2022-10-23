package com.myfp.fp.controller.tariffs;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestToConnectCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        String address = req.getParameter("address");
        String sId = req.getParameter("id");
        int id = -1;
        if (sId == null || sId.equals("")) {
            return new Forward("/");
        } else {
            id = Integer.parseInt(sId);
            req.setAttribute("id", id);
        }
        if (isGood(city) && isGood(address)) {
            try {
                ConnectionRequest connectionRequest = new ConnectionRequest();

                User user = (User) req.getSession().getAttribute("currentUser");
                connectionRequest.setSubscriber(user);
                connectionRequest.setCity(city);
                connectionRequest.setAddress(address);

                TariffService tariffService = getServiceFactory().getTariffService();
                Tariff tariff = tariffService.findById((long) id);
                connectionRequest.setTariff(tariff);

                ConnectionRequestService connectionRequestService = getServiceFactory().getConnectionRequestService();
                connectionRequestService.insertRequest(connectionRequest);
                return new Forward("/");
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
