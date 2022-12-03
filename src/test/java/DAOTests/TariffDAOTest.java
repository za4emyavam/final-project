package DAOTests;

import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TariffDAOTest {
    @Test
    public void findByIdTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong("tariff_id")).thenReturn(3L);

        Array arrName = mock(Array.class);
        when(rs.getArray("name")).thenReturn(arrName);
        when(arrName.getArray()).thenReturn(new String[]{"Base IP-TV", "Звичайний IP-TV"});

        Array arrDescr = mock(Array.class);
        when(rs.getArray("description")).thenReturn(arrDescr);
        when(arrDescr.getArray()).thenReturn(new String[]{"base ip-tv", "звичайний ip-tv"});

        when(rs.getLong("service_id")).thenReturn(1L);
        when(rs.getString("service_type")).thenReturn("IP-TV");
        when(rs.getInt("cost")).thenReturn(120);
        when(rs.getInt("frequency_of_payment")).thenReturn(28);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "SELECT * FROM tariff t, service s WHERE t.tariff_id=? AND t.service=s.service_id"))
                .thenReturn(statement);

        try (MockedStatic<ConnectionPool> conPool = Mockito.mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            Tariff expected = fillExample();

            TariffService service = MainServiceFactoryImpl.getInstance().getTariffService();
            Tariff actual = service.findById(3L);

            Assertions.assertTrue(
                    new ReflectionEquals(expected, "service")
                            .matches(actual)
            );
            Assertions.assertTrue(
                    new ReflectionEquals(expected.getService())
                            .matches(actual.getService())
            );
        }
    }

    @Test
    public void createTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt(1)).thenReturn(3);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "INSERT INTO tariff(name, description, service, cost, frequency_of_payment) VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )).thenReturn(statement);

        try (MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            Tariff tariff = fillExample();

            long expected = 3L;

            TariffService service = MainServiceFactoryImpl.getInstance().getTariffService();
            long actual = service.insertTariff(tariff);

            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void readAllTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("tariff_id")).thenReturn(3L).thenReturn(4L);

        Array arrName = mock(Array.class);
        when(rs.getArray("name")).thenReturn(arrName).thenReturn(arrName);
        when(arrName.getArray())
                .thenReturn(new String[]{"Base IP-TV", "Звичайний IP-TV"})
                .thenReturn(new String[]{"Base Internet", "Звичайний Internet"});

        Array arrDescr = mock(Array.class);
        when(rs.getArray("description")).thenReturn(arrDescr).thenReturn(arrDescr);
        when(arrDescr.getArray())
                .thenReturn(new String[]{"base ip-tv", "звичайний ip-tv"})
                .thenReturn(new String[]{"base internet", "звичайний internet"});

        when(rs.getLong("service_id")).thenReturn(1L).thenReturn(2L);
        when(rs.getString("service_type")).thenReturn("IP-TV").thenReturn("Internet");
        when(rs.getInt("cost")).thenReturn(120).thenReturn(140);
        when(rs.getInt("frequency_of_payment")).thenReturn(28).thenReturn(28);

        Statement statement = mock(Statement.class);
        when(statement.executeQuery(
                "SELECT * FROM tariff t, service s WHERE t.service=s.service_id"))
                .thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.createStatement())
                .thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            List<Tariff> expected = new ArrayList<>();

            Tariff firstTariff = fillExample();
            expected.add(firstTariff);

            Tariff secondTariff = new Tariff();
            secondTariff.setId(4L);
            secondTariff.setName(new String[]{"Base Internet", "Звичайний Internet"});
            secondTariff.setDescription(new String[]{"base internet", "звичайний internet"});

            Service service = new Service();
            service.setId(2L);
            service.setServiceType("Internet");
            secondTariff.setService(service);

            secondTariff.setCost(140);
            secondTariff.setFrequencyOfPayment(28);

            expected.add(secondTariff);

            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            List<Tariff> actual = tariffService.findAll();

            //first tariff and its service
            Assertions.assertTrue(
                    new ReflectionEquals(actual.get(0), "service")
                            .matches(expected.get(0)));
            Assertions.assertTrue(
                    new ReflectionEquals(actual.get(0).getService())
                            .matches(expected.get(0).getService()));

            //second service and its service
            Assertions.assertTrue(
                    new ReflectionEquals(actual.get(1), "service")
                            .matches(expected.get(1)));
            Assertions.assertTrue(
                    new ReflectionEquals(actual.get(1).getService())
                            .matches(expected.get(1).getService()));
        }

    }

    private Tariff fillExample() {
        Tariff tariff = new Tariff();
        tariff.setId(3L);
        tariff.setName(new String[]{"Base IP-TV", "Звичайний IP-TV"});
        tariff.setDescription(new String[]{"base ip-tv", "звичайний ip-tv"});

        Service service = new Service();
        service.setId(1L);
        service.setServiceType("IP-TV");
        tariff.setService(service);

        tariff.setCost(120);
        tariff.setFrequencyOfPayment(28);
        return tariff;
    }
}
