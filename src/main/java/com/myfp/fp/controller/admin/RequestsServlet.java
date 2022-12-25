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
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<ConnectionRequest> requests;
        try {
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            requests = connectionRequestService.readAll(recordsPerPage,(page - 1) * recordsPerPage);
            int noOfRecords = connectionRequestService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("requests", requests);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("requests.jsp").forward(request, response);
    }
}
