package DAOTests;

import com.myfp.fp.dao.postgres.BaseDAOImpl;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.UserService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class UserDAOTest {
    @Test
    public void findByIdTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("user_id")).thenReturn(1L);
        when(rs.getString("email")).thenReturn("example@gmail.com");
        when(rs.getString("pass")).thenReturn("12345");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        when(pstmt.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement("SELECT * FROM \"user\" u WHERE u.user_id=(?)")).thenReturn(pstmt);

        try (MockedStatic<ConnectionPool> conPool = Mockito.mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);
            User expectedUser = new User();
            expectedUser.setId(1L);
            expectedUser.setEmail("example@gmail.com");
            expectedUser.setPass("12345");

            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            User actualUser = userService.findById(1L);

            Assertions.assertTrue(
                    new ReflectionEquals(expectedUser, "registrationDate", "userRole",
                            "userStatus", "userBalance", "firstname",
                            "middleName", "surname", "telephoneNumber")
                            .matches(actualUser));
        }
    }

    @Test
    public void createTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt(1)).thenReturn(13);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(
                "INSERT INTO \"user\"" +
                        "(email, pass, firstname, middle_name, surname, telephone_number) " +
                "VALUES(?, ?, ?, ?, ?, ?)",
                1)).thenReturn(statement);
        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            User example = fillExample();

            long expected = 13L;


            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            long actual = userService.create(example);

            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void findByEmailAndPass() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("user_id")).thenReturn(13L);
        when(rs.getString("email")).thenReturn("blabla@mail");
        when(rs.getString("pass")).thenReturn("example");
        when(rs.getString("firstname")).thenReturn("Sergey");
        when(rs.getString("middle_name")).thenReturn("Sergeyevich");
        when(rs.getString("surname")).thenReturn("Sergeev");
        when(rs.getString("telephone_number")).thenReturn("380636278404");

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement("SELECT * FROM \"user\" u WHERE u.email = (?) AND u.pass=(?)")).thenReturn(statement);

        try(MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            User expectedUser = fillExample();

            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();
            User actualUser = userService.findByLoginAndPassword(expectedUser.getEmail(), expectedUser.getPass());

            Assertions.assertTrue(
                    new ReflectionEquals(expectedUser, "registrationDate", "userRole",
                            "userStatus", "userBalance")
                            .matches(actualUser));
        }
    }

    @Test
    public void testGetNoOfRecords() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(1)).thenReturn(5);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        Statement statement = mock(Statement.class);
        when(statement.executeQuery("SELECT count(*) AS count FROM \"user\" ")).thenReturn(resultSet);

        Connection connection = mock(Connection.class);
        when(connection.createStatement()).thenReturn(statement);

        try (MockedStatic<ConnectionPool> conPool = Mockito.mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(connection);
            UserService userService = MainServiceFactoryImpl.getInstance().getUserService();

            assertEquals(new Integer(5), userService.getNoOfRecords());
        }
    }

    private User fillExample() {
        User example = new User();
        example.setId(13L);
        example.setEmail("blabla@mail");
        example.setPass("example");
        example.setFirstname("Sergey");
        example.setMiddleName("Sergeyevich");
        example.setSurname("Sergeev");
        example.setTelephoneNumber("380636278404");
        return example;
    }

}
