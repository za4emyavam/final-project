package com.myfp.fp.controller.admin;

import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.User;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserInfoServlet", value = "/admin/users/user_info")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getId(req, resp);
        if (id == -1)
            resp.sendRedirect("/index");
        try {
            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            User user = userService.findById((long) id);
            if (user != null) {
                req.setAttribute("user", user);
                UserTariffsService userTariffsService = MainServiceFactoryImpl.getInstance().getUserTariffsService();
                List<UserTariffs> userTariffs = userTariffsService.readAllByUserId((long) id);
                if (userTariffs != null && !userTariffs.isEmpty())
                    req.setAttribute("userTariffs", userTariffs);
            }
            else {
                resp.sendRedirect("/index");
            }
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("user_info.jsp").forward(req, resp);
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
