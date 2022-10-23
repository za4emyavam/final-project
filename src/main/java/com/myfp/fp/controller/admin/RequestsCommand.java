package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class RequestsCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ConnectionRequest> requests = null;
        try {
            ConnectionRequestService connectionRequestService = getServiceFactory().getConnectionRequestService();
            requests = connectionRequestService.readAll();
            if (requests != null) {
                System.out.println("requests set attribute");
                req.setAttribute("requests", requests);
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }

        return null;
    }


}
