package com.myfp.fp.controller.admin;

import com.myfp.fp.controller.Forward;
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
        int id = getId(request, response);
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
        System.out.println("UpdateRequestCommand");
        int id = getId(request, response);
        if (id == -1)
            response.sendRedirect("/index");
        try {
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            ConnectionRequest connectionRequest = connectionRequestService.read((long) id);
            String status = request.getParameter("status");
            if (status != null && !status.equals("")) {
                System.out.println("is here?");
                switch (status) {
                    case "approved":
                        System.out.println(status);
                        connectionRequest.setStatus(RequestStatus.APPROVED);
                        connectionRequestService.update(connectionRequest);
                        System.out.println("??");
                        response.sendRedirect("/admin/requests");
                    case "reject":
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

    private int getId(HttpServletRequest request, HttpServletResponse response) {
        String sId = request.getParameter("id");
        int id = -1;
        if (sId != null && !sId.equals("")) {
            id = Integer.parseInt(sId);
            request.setAttribute("id", id);
        }
        return id;
    }
}