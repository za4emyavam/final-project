package com.myfp.fp.controller.admin;

import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.RequestStatus;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateRequestServlet", value = "/admin/requests/update")
public class UpdateRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if (id == -1)
            response.sendRedirect("/index");
        try {
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            ConnectionRequest connectionRequest = connectionRequestService.read((long) id);
            if (connectionRequest != null) {
                request.setAttribute("connectionRequest", connectionRequest);
            } else {
                response.sendRedirect("/index");
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if (id == -1)
            response.sendRedirect("/index");
        try {
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            ConnectionRequest connectionRequest = connectionRequestService.read((long) id);
            String status = request.getParameter("status");
            if (status != null && !status.equals("")) {
                switch (status) {
                    case "approved":
                        connectionRequest.setStatus(RequestStatus.APPROVED);
                        connectionRequestService.update(connectionRequest);
                        response.sendRedirect("/admin/requests");
                        break;
                    case "reject":
                        connectionRequest.setStatus(RequestStatus.REJECTED);
                        connectionRequestService.update(connectionRequest);
                        response.sendRedirect("/admin/requests");
                        break;
                    default:
                        break;
                }
            }
            if (connectionRequest != null) {
                request.setAttribute("connectionRequest", connectionRequest);
            } else {
                response.sendRedirect("/index");
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

    private int getId(HttpServletRequest request) {
        String sId = request.getParameter("id");
        int id = -1;
        if (sId != null && !sId.equals("")) {
            id = Integer.parseInt(sId);
            request.setAttribute("id", id);
        }
        return id;
    }
}
