package com.myfp.fp.controller.admin;

import com.myfp.fp.entities.*;
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
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "UserInfoServlet", value = "/admin/users/user_info")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getId(req);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getId(req);
        if (id == -1)
            resp.sendRedirect("/index");
        String command = req.getParameter("command");
        switch(command) {
            case "change_status":
                String value = req.getParameter("status");
                if (Arrays.stream(UserStatus.values()).anyMatch(v -> v.getName().equals(value))) {
                    try {
                        UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
                        User user = userService.findById((long) id);
                        user.setUserStatus(UserStatus.fromString(value));
                        userService.update(user);
                        resp.sendRedirect("/admin/users/user_info?id=" + id);
                    } catch (FactoryException | ServiceException e) {
                        throw new ServletException(e);
                    }
                }
                break;
            case "change_role":
                String role = req.getParameter("role");
                if (Arrays.stream(Role.values()).anyMatch(v -> v.getName().equals(role))) {
                    try {
                        UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
                        User user = userService.findById((long) id);
                        user.setUserRole(Role.fromString(role));
                        userService.update(user);
                        resp.sendRedirect("/admin/users/user_info?id=" + id);
                    } catch (FactoryException | ServiceException e) {
                        throw new ServletException(e);
                    }
                }
                break;
            case "unsubscribe":
                int tariffId = Integer.parseInt(req.getParameter("tariff_id"));
                try{
                    UserTariffsService userTariffsService = MainServiceFactoryImpl.getInstance().
                            getUserTariffsService();
                    userTariffsService.deleteByUserIdTariffId((long) id, (long) tariffId);
                    resp.sendRedirect("/admin/users/user_info?id=" + id);
                } catch (FactoryException | ServiceException e) {
                    throw new ServletException(e);
                }
                break;
            default:
                resp.sendRedirect("/admin/users/user_info?id=" + id);
                break;
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
