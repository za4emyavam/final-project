package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.RequestStatus;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UpdateRequestCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UpdateRequestCommand");
        String sId = req.getParameter("id");
        int id = -1;
        if (sId == null || sId.equals("")) {
            return new Forward("/admin.html");
        } else {
            id = Integer.parseInt(sId);
            req.setAttribute("id", id);
        }
        try {
            ConnectionRequestService connectionRequestService = getServiceFactory().getConnectionRequestService();
            ConnectionRequest connectionRequest = connectionRequestService.read((long) id);
            String status = req.getParameter("status");
            if (status != null && !status.equals("")) {
                switch (status) {
                    case "approved":
                        System.out.println(status);
                        connectionRequest.setStatus(RequestStatus.APPROVED);
                        connectionRequestService.update(connectionRequest);
                        return new Forward("/admin.html");
                    case "reject":
                    default:
                        break;
                }
            }
            if (connectionRequest != null) {
                req.setAttribute("connectionRequest", connectionRequest);
            } else {
                return new Forward("/");
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
