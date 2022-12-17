package ControllerTests;

import com.myfp.fp.controller.LoginServlet;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class LoginServletTest {
    @Test
    public void validData() throws Exception {
        LoginServlet servlet = new LoginServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);

        when(req.getParameter("login")).thenReturn("blabla@mail");
        when(req.getParameter("password")).thenReturn("example123");

        MainServiceFactoryImpl mainServiceFactory = mock(MainServiceFactoryImpl.class);

        try (MockedStatic<MainServiceFactoryImpl> serviceFactory = mockStatic(MainServiceFactoryImpl.class)) {
            serviceFactory.when(MainServiceFactoryImpl::getInstance).thenReturn(mainServiceFactory);

            UserService userService = mock(UserService.class);
            when(mainServiceFactory.getUserService()).thenReturn(userService);

            User user = mock(User.class);
            when(userService.findByLoginAndPassword("blabla@mail", "example123")).thenReturn(user);

            HttpServletResponse resp = mock(HttpServletResponse.class);

            HttpSession session = mock(HttpSession.class);
            when(req.getSession()).thenReturn(session);

            doNothing().when(session).setAttribute(anyString(), anyString());

            servlet.doPost(req, resp);

            verify(resp, times(1)).sendRedirect("/index");
            verifyNoMoreInteractions(resp);
        }
    }

    @Test
    public void incorrectUserData() throws Exception {
        LoginServlet servlet = new LoginServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);

        when(req.getParameter("login")).thenReturn("blabla@mail");
        when(req.getParameter("password")).thenReturn("example123");

        MainServiceFactoryImpl mainServiceFactory = mock(MainServiceFactoryImpl.class);

        try (MockedStatic<MainServiceFactoryImpl> serviceFactory = mockStatic(MainServiceFactoryImpl.class)) {
            serviceFactory.when(MainServiceFactoryImpl::getInstance).thenReturn(mainServiceFactory);

            UserService userService = mock(UserService.class);
            when(mainServiceFactory.getUserService()).thenReturn(userService);

            when(userService.findByLoginAndPassword("blabla@mail", "bad_example")).thenReturn(null);

            when(req.getContextPath()).thenReturn("");

            HttpServletResponse resp = mock(HttpServletResponse.class);

            servlet.doPost(req, resp);

            verify(resp, times(1)).sendRedirect("/login?message=login.message.incorrect.password");
            verifyNoMoreInteractions(resp);
        }
    }
}
