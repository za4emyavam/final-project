package com.myfp.fp.controller.cabinet;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class HistoryCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        try {
            TransactionService transactionService = getServiceFactory().getTransactionService();
            List<Transaction> transactions = transactionService.readAllTransactionsByUserBalance(user.getId());
            req.setAttribute("transactions", transactions);
        } catch (FactoryException |ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
