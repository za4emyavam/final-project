package com.myfp.fp.controller;

import com.myfp.fp.util.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

abstract public class Command {
    private ServiceFactory serviceFactory;

    public final ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public final void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    abstract public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
