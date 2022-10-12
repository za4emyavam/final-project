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
        HttpSession session = req.getSession(false);
        if(session != null) {
            User user = (User) session.getAttribute("currentUser");
            if(user != null) {
                switch (user.getRole()) {
                    /*case ADMINISTRATOR:
                        return new Forward("/user/list.html");
                    case MANAGER:
                    case USER:
                        return new Forward("/account/list.html");*/
                    default:
                        //return new Forward("/index.html");
                        return null;
                }
            } else {
                //return new Forward("/login.html");
                return null;
            }
        } else {
            //return new Forward("/login.html");
            //return null;
            return new Forward("/index", false);
        }
    }
}
