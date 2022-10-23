package com.myfp.fp.dao.mysql;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.Role;
import com.myfp.fp.entities.User;

import java.sql.*;
import java.util.List;

public class UserDAOMySQL extends BaseDAOImpl implements UserDAO {
    @Override
    public User read(Long id) throws DAOException {
        /*String sql = "SELECT * FROM 'user' WHERE 'id' = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.values()[resultSet.getInt("role")]);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }*/
        return null;
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
        System.out.println("readByLogin");
        return null;
    }

    @Override
    public User readByLoginAndPassword(String login, String password) throws DAOException {
        /*System.out.println("readByLoginAndPassword from DB");
        String sql = "SELECT u.id, u.email, u.role FROM user u WHERE u.login = ? AND u.password = (?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.values()[resultSet.getInt("role")]);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStat(resultSet);
            closeStat(statement);

        }*/
        return null;
    }

    @Override
    public void addNewUser(String email, String login, String password) throws DAOException {
       /* System.out.println("addNewUser");
        String sql = "INSERT user(email, login, password, role) values(?, ?, ?, ?)";
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
}
