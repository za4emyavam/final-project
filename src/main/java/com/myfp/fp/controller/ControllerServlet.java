package com.myfp.fp.controller;

import com.myfp.fp.util.MainServiceFactoryImpl;
import com.myfp.fp.util.ServiceFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public ServiceFactory getServiceFactory() {
        return new MainServiceFactoryImpl();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        System.out.println("URL ==> " + url);
        String context = req.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if(postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        if(url.equals("/"))
            url = "/index";
        System.out.println("TEMP URL ==> " + url);
        Command command = CommandContainer.getCommand(url);
        Forward forward = null;
        if (command != null) {
            try(ServiceFactory serviceFactory = getServiceFactory()) {
                command.setServiceFactory(serviceFactory);
                forward = command.execute(req, resp);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        if(forward != null && forward.isRedirect()) {
            System.out.println("NEW URL ==>" + forward.getUrl());
            resp.sendRedirect(context + forward.getUrl());
        } else {
            if(forward != null && forward.getUrl() != null) {
                url = forward.getUrl();
            }
            System.out.println("NEW NOT REDIRECTED URL ==>" + url);
            req.getRequestDispatcher("/WEB-INF/jsp" + url + ".jsp").forward(req, resp);
        }
    }
}
