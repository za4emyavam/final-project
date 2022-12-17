package com.myfp.fp.controller.tariffs;

import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MailReport;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RequestToConnectServlet", value = "/tariffs/request")
public class RequestToConnectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if (id == -1)
            response.sendRedirect("/index");
        request.getRequestDispatcher("request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        int id = getId(request);
        if (id == -1)
            response.sendRedirect("/index");
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest();
            User user = (User) request.getSession().getAttribute("currentUser");
            connectionRequest.setSubscriber(user);
            connectionRequest.setCity(city);
            connectionRequest.setAddress(address);
            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            Tariff tariff = tariffService.findById((long) id);
            connectionRequest.setTariff(tariff);
            ConnectionRequestService connectionRequestService = MainServiceFactoryImpl.getInstance().
                    getConnectionRequestService();
            connectionRequestService.insertRequest(connectionRequest);
            sendEmail(user, tariff.getName()[1]);
            response.sendRedirect("/index");
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

    private void sendEmail(User user, String tariffName) {
        String email = user.getEmail();
        String firstname = user.getFirstname();
        MailReport.requestMail(email, firstname, tariffName);
    }
}
