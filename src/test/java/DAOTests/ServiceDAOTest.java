package DAOTests;

import com.myfp.fp.entities.Service;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServiceDAOTest {
    @Test
    public void readByType() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("service_id")).thenReturn(3L);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "SELECT s.service_id FROM service s WHERE s.service_type=?"
        )).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            Service expected = fillExample();

            ServiceService service = MainServiceFactoryImpl.getInstance().getServiceService();
            Service actual = service.findByType(expected.getServiceType());

            Assertions.assertTrue(new ReflectionEquals(expected)
                    .matches(actual));
        }
    }

    @Test
    public void readAllTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("service_id")).thenReturn(1L).thenReturn(2L);
        when(rs.getString("service_type")).thenReturn("Internet").thenReturn("IP-TV");

        Statement statement = mock(Statement.class);
        when(statement.executeQuery("SELECT * FROM service")).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            List<Service> expected = new ArrayList<>();
            Service firstService = new Service();
            firstService.setId(1L);
            firstService.setServiceType("Internet");
            expected.add(firstService);

            Service secondService = new Service();
            secondService.setId(2L);
            secondService.setServiceType("IP-TV");
            expected.add(secondService);

            ServiceService service = MainServiceFactoryImpl.getInstance().getServiceService();
            List<Service> actual = service.findAll();

            //first service
            Assertions.assertTrue(new ReflectionEquals(actual.get(0)).matches(expected.get(0)));

            //second service
            Assertions.assertTrue(new ReflectionEquals(actual.get(1)).matches(expected.get(1)));
        }
    }

    private Service fillExample() {
        Service service = new Service();
        service.setId(3L);
        service.setServiceType("IP-TV");
        return service;
    }
}
