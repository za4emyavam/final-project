package com.myfp.fp.controller.cabinet;

import com.myfp.fp.controller.Command;
import com.myfp.fp.controller.Forward;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.TransactionType;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReplenishCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String amount = req.getParameter("amount");
        if (amount != null && !amount.equals("")) {
            try {
                User user = (User) req.getSession(false).getAttribute("currentUser");

                TransactionService transactionService = getServiceFactory().getTransactionService();
                Transaction transaction = new Transaction();
                transaction.setBalanceId(Math.toIntExact(user.getId()));
                transaction.setType(TransactionType.REFILL);
                transaction.setTransactionAmount(Integer.parseInt(amount));

                long id = transactionService.insertTransaction(transaction);
                if (id != 0) {

                    UserService userService = getServiceFactory().getUserService();
                    req.getSession().setAttribute("currentUser", userService.findById(user.getId()));
                    return new Forward("/cabinet.html");
                }
            } catch (FactoryException e) {
                throw new ServletException(e);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
        }
        return null;
    }
}
