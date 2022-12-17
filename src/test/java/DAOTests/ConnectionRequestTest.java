package DAOTests;

import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.entities.RequestStatus;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;

import static org.mockito.Mockito.*;

public class ConnectionRequestTest {
    @Test
    public void readById() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("connection_request_id")).thenReturn(5);
        when(rs.getLong("user_id")).thenReturn(3L);
        when(rs.getString("email")).thenReturn("blabla@mail");

        when(rs.getLong("tariff_id")).thenReturn(2L);
        Array arrName = mock(Array.class);
        when(rs.getArray("name")).thenReturn(arrName);
        when(arrName.getArray())
                .thenReturn(new String[]{"Base IP-TV", "Звичайний IP-TV"});

        Array arrDescr = mock(Array.class);
        when(rs.getArray("description")).thenReturn(arrDescr);
        when(arrDescr.getArray())
                .thenReturn(new String[]{"base ip-tv", "звичайний ip-tv"});

        when(rs.getString("city")).thenReturn("Odessa");
        when(rs.getString("address")).thenReturn("Ak. Saharova 47 24");

        when(rs.getString("status")).thenReturn("in processing");

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "SELECT * FROM connection_request cr, \"user\" u, tariff t, service s " +
                        "WHERE cr.connection_request_id=(?) AND u.user_id=cr.subscriber " +
                        "AND t.tariff_id=cr.tariff AND s.service_id=t.service"
        )).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            ConnectionRequest expected = fillExample();

            ConnectionRequestService service = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
            ConnectionRequest actual = service.read(5L);

            Assertions.assertTrue(
                    new ReflectionEquals(expected, "subscriber", "tariff")
                            .matches(actual)
            );

            Assertions.assertTrue(
                    new ReflectionEquals(expected.getSubscriber(),
                            "pass", "registrationDate", "userRole",
                            "userStatus", "userBalance", "firstname",
                            "middleName", "surname", "telephoneNumber")
                            .matches(actual.getSubscriber())
            );

            Assertions.assertTrue(
                    new ReflectionEquals(expected.getTariff(),
                            "service", "cost", "frequency_of_payment")
                            .matches(actual.getTariff())
            );
        }
    }

    @Test
    public void createTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt(1)).thenReturn(5);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "INSERT INTO connection_request(subscriber, city, address, tariff, date_of_change, status)" +
                        " VALUES(?, ?, ?, ?, DEFAULT, DEFAULT)",
                Statement.RETURN_GENERATED_KEYS
        )).thenReturn(statement);

       try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
                conPool.when(ConnectionPool::getConnection).thenReturn(con);

                ConnectionRequest request = fillExample();

                long expected = 5L;

                ConnectionRequestService service = MainServiceFactoryImpl.getInstance().getConnectionRequestService();
                long actual = service.insertRequest(request);

                Assertions.assertEquals(expected, actual);
        }
    }

    private ConnectionRequest fillExample() {
        ConnectionRequest request = new ConnectionRequest();
        request.setId(5L);
        request.setCity("Odessa");
        request.setAddress("Ak. Saharova 47 24");

        User user = new User();
        user.setId(3L);
        user.setEmail("blabla@mail");
        request.setSubscriber(user);

        Tariff tariff = new Tariff();
        tariff.setId(2L);
        tariff.setName(new String[]{"Base IP-TV", "Звичайний IP-TV"});
        tariff.setDescription(new String[]{"base ip-tv", "звичайний ip-tv"});
        request.setTariff(tariff);

        request.setStatus(RequestStatus.IN_PROCESSING);

        return request;
    }
}
