package com.myfp.fp.controller.cabinet;

import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HistoryServlet", value = "/cabinet/history")
public class HistoryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");
        try {
            TransactionService transactionService = MainServiceFactoryImpl.getInstance().getTransactionService();
            List<Transaction> transactions = transactionService.readAllTransactionsByUserBalance(user.getId());
            request.setAttribute("transactions", transactions);
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}
