package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOPstSQL extends BaseDAOImpl implements UserDAO {
    @Override
    public User read(Long id) throws DAOException {
        System.out.println("read by id from DB");
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
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(resultSet);
            closeStat(preparedStatement);
        }
    }

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
                try(ResultSet set = preparedStatement.getGeneratedKeys()) {
                    if(set.next()) {
                        entity.setId(resId = set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return resId;
    }

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
            rollback(con);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    @Override
    public List<User> readAll() throws DAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" u";
        Connection con = getConnection();
        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                users.add(fillUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return users;
    }

    @Override
    public List<User> readAll(int limit, int offset) throws DAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" u LIMIT ? OFFSET ?";
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
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return users;
    }

    @Override
    public User readByLogin(String login) throws DAOException {
        return null;
    }

    @Override
    public User readByLoginAndPassword(String login, String password) throws DAOException {
        System.out.println("readByLoginAndPassword from DB");
        String sql = "SELECT * FROM \"user\" u WHERE u.email = (?) AND u.pass=(?)";
        Connection con = null;  //sadasdasd
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
                user = read(resultSet.getLong("user_id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
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

    @Override
    public Integer getNoOfRecords() throws DAOException {
        String sql = "SELECT count(*) AS count FROM \"user\" ";
        Connection con = getConnection();
        int res = -1;
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next())
                res = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return res;
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
