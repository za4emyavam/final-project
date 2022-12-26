package ControllerTests;

import com.myfp.fp.controller.cabinet.HistoryServlet;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private TransactionService transactionService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void testDoGet() throws Exception {
        User user = new User();
        user.setId(1L);
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(transactionService.readAllTransactionsByUserID(1L)).thenReturn(transactions);
        when(request.getRequestDispatcher("history.jsp")).thenReturn(requestDispatcher);

        MainServiceFactoryImpl mainServiceFactory = mock(MainServiceFactoryImpl.class);

        try (MockedStatic<MainServiceFactoryImpl> serviceFactory = mockStatic(MainServiceFactoryImpl.class)) {
            serviceFactory.when(MainServiceFactoryImpl::getInstance).thenReturn(mainServiceFactory);

            when(mainServiceFactory.getTransactionService()).thenReturn(transactionService);

            HistoryServlet servlet = new HistoryServlet();
            servlet.doGet(request, response);

            verify(request).setAttribute("transactions", transactions);
            verify(requestDispatcher).forward(request, response);
        }
    }
}
