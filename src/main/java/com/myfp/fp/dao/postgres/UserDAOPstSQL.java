package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class UserDAOPstSQL extends BaseDAOImpl implements UserDAO {
    @Override
    public User read(Long id) throws DAOException {
        System.out.println("read by id from DB");
        String sql = "SELECT * FROM \"user\" u WHERE u.user_id=(?)";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = fillUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStat(resultSet);
            closeStat(preparedStatement);
        }
    }

    @Override
    public Long create(User entity) throws DAOException {
        return null;
    }

    @Override
    public void update(User entity) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    @Override
    public List<User> readAll() throws DAOException {
        return null;
    }

    @Override
    public User readByLogin(String login) throws DAOException {
        return null;
    }

    @Override
    public User readByLoginAndPassword(String login, String password) throws DAOException {
        System.out.println("readByLoginAndPassword from DB");
        String sql = "SELECT * FROM \"user\" u WHERE u.email = (?) AND u.pass=(?)";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = read(resultSet.getLong("user_id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStat(resultSet);
            closeStat(preparedStatement);
        }
    }

    @Override
    public void addNewUser(String email, String login, String password) throws DAOException {
        /*System.out.println("addNewUser");
        String sql = "INSERT fp_schema.user(email, password, role) values(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, Role.USER.ordinal());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            closeStat(preparedStatement);*/
            /*try {
                System.out.println("Returning con to the pool");
                con.close();
            } catch (SQLException e) {
                System.out.println("Returning con to the pool");
                throw new DAOException();
            }
        }*/
    }

    private User fillUser(ResultSet rs) throws SQLException {
        User user = new User();
        try {
            user.setId(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPass(rs.getString("pass"));
            user.setUserBalance(rs.getInt("user_balance"));

            user.setFirstname(rs.getString("firstname"));
            user.setMiddleName(rs.getString("middle_name"));
            user.setSurname(rs.getString("surname"));
            user.setTelephoneNumber(rs.getString("telephone_number"));

            user.setRegistrationDate(rs.getDate("registration_date"));
            //user.setRole(Enum.valueOf(Role.class, rs.getString("user_role").toUpperCase()));
            user.setUserRole(Role.fromString(rs.getString("user_role")));
            String status = rs.getString("user_status");
            /*if (status != null)
                user.setUserStatus(Enum.valueOf(UserStatus.class, status.toUpperCase()));
            else
                user.setUserStatus(null);*/
            user.setUserStatus(UserStatus.fromString(rs.getString("user_status")));


        } catch (SQLException e) {
            throw e;
        }
        return user;
    }
}