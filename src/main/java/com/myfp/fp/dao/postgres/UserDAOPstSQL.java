package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for interaction User entity with postgresql database
 */
public class UserDAOPstSQL extends BaseDAOImpl implements UserDAO {
    private static final Logger LOG4J = LogManager.getLogger(UserDAOPstSQL.class);

    /**

     Reads the information for a specific user from the database.
     @param id The ID of user to be read.
     @return The {@link User} object from the database.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public User read(Long id) throws DAOException {
        String sql = "SELECT * FROM \"user\" u WHERE u.user_id=(?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = fillUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeStat(resultSet);
            closeStat(preparedStatement);
            closeConnection(con);
        }
    }

    /**

     Creates a new user in the database.
     @param entity The {@link User} object to be created.
     @return The ID of the newly created user.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Long create(User entity) throws DAOException {
        String sql = "INSERT INTO \"user\"(email, pass, firstname, middle_name, surname, telephone_number) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        Connection con = getConnection();
        long resId = -1;
        try (PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            preparedStatement.setString(k++, entity.getEmail());
            preparedStatement.setString(k++, entity.getPass());
            preparedStatement.setString(k++, entity.getFirstname());
            preparedStatement.setString(k++, entity.getMiddleName());
            preparedStatement.setString(k++, entity.getSurname());
            preparedStatement.setString(k, entity.getTelephoneNumber());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                try (ResultSet set = preparedStatement.getGeneratedKeys()) {
                    if (set.next()) {
                        entity.setId(resId = set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return resId;
    }

    /**

     Updates the information for a specific user in the database.
     @param entity The {@link User} object containing the updated information.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public void update(User entity) throws DAOException {
        String sql = "UPDATE \"user\" u SET email=?, registration_date=?," +
                " user_role=?::role_type, user_status=?::user_status_type," +
                "user_balance=?, firstname=?, middle_name=?, surname=?, telephone_number=?" +
                " WHERE user_id=" + entity.getId();
        Connection con = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(sql);
            int k = 1;
            preparedStatement.setString(k++, entity.getEmail());
            preparedStatement.setDate(k++, entity.getRegistrationDate());
            preparedStatement.setString(k++, entity.getUserRole().getName());
            preparedStatement.setString(k++, entity.getUserStatus().getName());
            preparedStatement.setInt(k++, entity.getUserBalance());
            preparedStatement.setString(k++, entity.getFirstname());
            preparedStatement.setString(k++, entity.getMiddleName());
            preparedStatement.setString(k++, entity.getSurname());
            preparedStatement.setString(k, entity.getTelephoneNumber());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            rollback(con);
            throw new DAOException(e);
        } finally {
            closeStat(preparedStatement);
            closeConnection(con);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    /**

     Reads a list of all users from the database.
     @return A list of all users in the database.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<User> readAll() throws DAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" u";
        Connection con = getConnection();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                users.add(fillUser(resultSet));
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return users;
    }

    /**

     Reads a list of users from the database.
     @param limit The maximum number of users to return.
     @param offset The number of users to skip before returning the results.
     @return A list of users.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<User> readAll(int limit, int offset) throws DAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" u ORDER BY u.user_id LIMIT ? OFFSET ?";
        Connection con = getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(fillUser(rs));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return users;
    }

    /**
     * Determines if a user with the given login exists in the database.
     *
     * @param login the login of the user to check for
     * @return 1 if a user with the given login exists in the database, 0 otherwise
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public int isUserExist(String login) throws DAOException {
        String sql = "SELECT count(*) FROM \"user\" WHERE email=(?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            int res = 0;
            if (rs.next()) {
                res = rs.getInt(1);
            }
            return res;
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeStat(rs);
            closeStat(preparedStatement);
            closeConnection(con);
        }
    }

    /**
     * Reads a user from the database by login and password.
     *
     * @param login the user's login
     * @param password the user's password
     * @return the user with the specified login and password, or null if no such user exists
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public User readByLoginAndPassword(String login, String password) throws DAOException {
        String sql = "SELECT * FROM \"user\" u WHERE u.email = (?) AND u.pass=(?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = fillUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeStat(resultSet);
            closeStat(preparedStatement);
            closeConnection(con);
        }
    }

    /**
     * Returns the number of users in the database.
     *
     * @return the number of users in the database
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Integer getNoOfRecords() throws DAOException {
        String sql = "SELECT count(*) AS count FROM \"user\" ";
        int res = -1;
        Connection con = getConnection();
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next())
                res = rs.getInt(1);
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return res;
    }

    /**

     Fills a {@link User} object with information from a ResultSet.
     @param rs The ResultSet to get the information from.
     @return The user object with the information from the ResultSet.
     @throws SQLException If there is an error getting the information from the ResultSet.
     */
    private User fillUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPass(rs.getString("pass"));
        user.setUserBalance(rs.getInt("user_balance"));

        user.setFirstname(rs.getString("firstname"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setSurname(rs.getString("surname"));
        user.setTelephoneNumber(rs.getString("telephone_number"));

        user.setRegistrationDate(rs.getDate("registration_date"));
        user.setUserRole(Role.fromString(rs.getString("user_role")));
        user.setUserStatus(UserStatus.fromString(rs.getString("user_status")));
        return user;
    }
}
