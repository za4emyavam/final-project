package com.myfp.fp.controller.cabinet;

import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.TransactionStatus;
import com.myfp.fp.entities.TransactionType;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MailReport;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ReplenishServlet", value = "/cabinet/replenish")
public class ReplenishServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("replenish.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amount = request.getParameter("amount");
        if (amount != null && !amount.equals("")) {
            try {
                User user = (User) request.getSession(false).getAttribute("currentUser");

                TransactionService transactionService = MainServiceFactoryImpl.getInstance().getTransactionService();
                Transaction transaction = new Transaction();
                transaction.setBalanceId(Math.toIntExact(user.getId()));
                transaction.setType(TransactionType.REFILL);
                transaction.setTransactionAmount(Integer.parseInt(amount));
                transaction.setStatus(TransactionStatus.SUCCESSFUL);

                long id = transactionService.insertTransaction(transaction);
                if (id != 0) {
                    sendEmail(request);
                    UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
                    request.getSession().setAttribute("currentUser", userService.findById(user.getId()));
                    response.sendRedirect("/cabinet");
                }
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
    }

    private void sendEmail(HttpServletRequest request) {
        User currentUser = (User) request.getSession(false).getAttribute("currentUser");
        String email = currentUser.getEmail();
        String firstname = currentUser.getFirstname();
        String amount = request.getParameter("amount");
        MailReport.getInstance().replenishMail(email, firstname, amount);
    }
}
