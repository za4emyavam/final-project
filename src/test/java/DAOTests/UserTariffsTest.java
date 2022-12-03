package DAOTests;

import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.User;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.UserTariffsService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserTariffsTest {
    @Test
    public void readAllById() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("user_tariffs_id")).thenReturn(5).thenReturn(9);
        when(rs.getLong("user_id")).thenReturn(3L).thenReturn(3L);
        when(rs.getString("email"))
                .thenReturn("blabla@mail")
                .thenReturn("blabla@mail");

        when(rs.getLong("tariff_id")).thenReturn(2L).thenReturn(4L);
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

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "SELECT * FROM user_tariffs ut, \"user\" u, tariff t, service s " +
                        "WHERE u.user_id=(?) AND ut.user_id=u.user_id AND u.user_id=ut.user_id\n" +
                        "           AND t.tariff_id=ut.tariff_id AND s.service_id=t.service"
        )).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            List<UserTariffs> expected = new ArrayList<>();
            UserTariffs firstUT = new UserTariffs();
            firstUT.setId(5L);
            User user = new User();
            user.setId(3L);
            user.setEmail("blabla@mail");
            firstUT.setUser(user);
            Tariff firstTariff = new Tariff();
            firstTariff.setId(2L);
            firstTariff.setName(new String[]{"Base IP-TV", "Звичайний IP-TV"});
            firstTariff.setDescription(new String[]{"base ip-tv", "звичайний ip-tv"});
            firstUT.setTariff(firstTariff);
            expected.add(firstUT);

            UserTariffs secondUT = new UserTariffs();
            secondUT.setId(9L);
            secondUT.setUser(user);
            Tariff secondTariff = new Tariff();
            secondTariff.setId(4L);
            secondUT.setTariff(secondTariff);
            secondTariff.setName(new String[]{"Base Internet", "Звичайний Internet"});
            secondTariff.setDescription(new String[]{"base internet", "звичайний internet"});
            secondUT.setTariff(secondTariff);
            expected.add(secondUT);


            UserTariffsService service = MainServiceFactoryImpl.getInstance().getUserTariffsService();
            List<UserTariffs> actual = service.readAllByUserId(3L);

            //first UserTariffs
            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(0),
                            "tariff", "user", "date_of_start", "date_of_last_payment")
                            .matches(actual.get(0)));

            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(0).getUser(),
                            "pass", "registrationDate", "userRole",
                            "userStatus", "userBalance", "firstname",
                            "middleName", "surname", "telephoneNumber")
                            .matches(actual.get(0).getUser())
            );
            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(0).getTariff(),
                            "service", "cost", "frequency_of_payment")
                            .matches(actual.get(0).getTariff())
            );

            //second UserTariffs
            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(1),
                            "tariff", "user", "date_of_start", "date_of_last_payment")
                            .matches(actual.get(1)));

            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(1).getUser(),
                            "pass", "registrationDate", "userRole",
                            "userStatus", "userBalance", "firstname",
                            "middleName", "surname", "telephoneNumber")
                            .matches(actual.get(1).getUser())
            );
            Assertions.assertTrue(
                    new ReflectionEquals(expected.get(1).getTariff(),
                            "service", "cost", "frequency_of_payment")
                            .matches(actual.get(1).getTariff())
            );
        }
    }

    @Test
    public void getAllIdTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("user_tariffs_id"))
                .thenReturn(3)
                .thenReturn(4);

        Statement statement = mock(Statement.class);
        when(statement.executeQuery("SELECT user_tariffs_id FROM user_tariffs"))
                .thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            List<Integer> expected = new ArrayList<>();
            expected.add(3);
            expected.add(4);

            UserTariffsService service = MainServiceFactoryImpl.getInstance().getUserTariffsService();
            List<Integer> actual = service.getAllId();

            Assertions.assertIterableEquals(expected, actual);
        }
    }
}
