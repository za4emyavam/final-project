package com.myfp.fp.controller.admin;

import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RequestsServlet", value = "/admin/requests")
public class RequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ConnectionRequest> requests = null;
        try {
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            requests = connectionRequestService.readAll();
            if (requests != null) {
                System.out.println("requests set attribute");
                request.setAttribute("requests", requests);
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("requests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
