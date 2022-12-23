package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserTariffsDAO;
import com.myfp.fp.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTariffsDAOPstSQL extends BaseDAOImpl implements UserTariffsDAO {
    private static final Logger LOG4J = LogManager.getLogger(UserTariffsDAOPstSQL.class);
    @Override
    public UserTariffs read(Long id) throws DAOException {
        return null;
    }

    @Override
    public Long create(UserTariffs entity) throws DAOException {
        return null;
    }

    @Override
    public void update(UserTariffs entity) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    @Override
    public List<UserTariffs> readAll() throws DAOException {
        return null;
    }

    /**

     Retrieves a list of all tariffs for a given user.
     @param id the id of the user to retrieve tariffs for
     @return a list of user tariffs
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<UserTariffs> readAllByUser(Long id) throws DAOException {
        List<UserTariffs> list = new ArrayList<>();
        String sql = "SELECT * FROM user_tariffs ut, \"user\" u, tariff t, service s " +
                "WHERE u.user_id=(?) AND ut.user_id=u.user_id AND u.user_id=ut.user_id\n" +
                "           AND t.tariff_id=ut.tariff_id AND s.service_id=t.service";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    list.add(fillUserTariffs(rs));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return list;
    }

    /**

     Retrieves a list of all user tariff ids from the database.
     @return a list of user tariff ids
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Integer> getAllId() throws DAOException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT user_tariffs_id FROM user_tariffs";
        Connection con = getConnection();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                list.add(resultSet.getInt("user_tariffs_id"));
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return list;
    }

    /**

     Deletes a user tariff from the database by user id and tariff id.
     @param userId the id of the user to delete the tariff for
     @param tariffId the id of the tariff to delete
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public void deleteByUserIdTariffId(Long userId, Long tariffId) throws DAOException {
        String sql = "DELETE FROM user_tariffs ut WHERE user_id=? AND tariff_id=?";
        Connection con = getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, Math.toIntExact(userId));
            preparedStatement.setInt(2, Math.toIntExact(tariffId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
    }


    /**
     * Checks each user who is subscribed to the rates whether the frequencyOfPayment has passed since the last payment.
     * If yes, if possible, debits funds from the account or changes the user's status to blocked.
     * After the check, a new tuple is added to the check table, in which the id of the user who launched the check,
     * the number of users from whom funds were debited, the amount of funds debited, and the date of the check.
     *
     * @param userId the user id of the person requesting the payment status of all users
     * @return the number of users from whom funds were debited and the amount of funds debited
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Integer> checkPaymentOfAllUsers(Long userId) throws DAOException {
        List<Integer> res = new ArrayList<>();
        String sql = "SELECT * FROM f_check_payment(?);";
        Connection con = getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, Math.toIntExact(userId));
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    res.add(rs.getInt(1));
                    res.add(rs.getInt(2));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return res;
    }

    private UserTariffs fillUserTariffs(ResultSet rs) throws DAOException {
        UserTariffs userTariff = new UserTariffs();
        try {
            userTariff.setId((long) rs.getInt("user_tariffs_id"));

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
            userTariff.setUser(user);

            Tariff tariff = new Tariff();
            tariff.setId(rs.getLong("tariff_id"));
            tariff.setName((String[]) rs.getArray("name").getArray());
            tariff.setDescription((String[]) rs.getArray("description").getArray());
            tariff.setCost(rs.getInt("cost"));
            tariff.setFrequencyOfPayment(rs.getInt("frequency_of_payment"));

            Service service = new Service();
            service.setId(rs.getLong("service_id"));
            service.setServiceType(rs.getString("service_type"));
            tariff.setService(service);

            userTariff.setTariff(tariff);
            userTariff.setDateOfStart(rs.getDate("date_of_start"));
            userTariff.setDateOfLastPayment(rs.getDate("date_of_last_payment"));
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return userTariff;
    }
}
