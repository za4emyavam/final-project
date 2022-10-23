package com.myfp.fp.controller;

import com.myfp.fp.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class MainCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in index execute");
        String locale = req.getParameter("cookieLocale");
        if (locale != null && !locale.equals("")) {
            switch(locale) {
                case "en":
                case "ua":
                    System.out.println("switch to " + locale);
                    return new Forward("/index.html");
                default:
                    return new Forward("/index", false);
            }
        }
        return new Forward("/index", false);
        /*HttpSession session = req.getSession(false);
        if(session != null) {
            User user = (User) session.getAttribute("currentUser");
            if(user != null) {
                *//*switch (user.getUserRole()) {
                    case MAIN_ADMIN:
                    case ADMIN:
                        System.out.println("admin");
                        return new Forward("/admin.html");
                    case USER:
                    default:
                        return new Forward("index", false);*//*

                        //return new Forward("/index.html");
                        *//*return null;*//*
                    return new Forward("/index", false);
                }
            } else {
                //return new Forward("/login.html");
                return new Forward("/index", false);
            }
        } else {
            //return new Forward("/login.html");
            //return null;
            return new Forward("/index", false);
        }*/
    }
}
