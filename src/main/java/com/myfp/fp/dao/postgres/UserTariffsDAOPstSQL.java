package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserTariffsDAO;
import com.myfp.fp.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTariffsDAOPstSQL extends BaseDAOImpl implements UserTariffsDAO {
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

    @Override
    public List<UserTariffs> readAllByUser(Long id) throws DAOException {
        List<UserTariffs> list = new ArrayList<>();
        String sql = "SELECT * FROM user_tariffs ut, \"user\" u, tariff t, service s " +
                "WHERE u.user_id=(?) AND t.tariff_id=ut.user_id AND s.service_id=t.service";
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
            throw new DAOException(e);
        }
        return list;
    }

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
            throw new DAOException(e);
        }
        return list;
    }

    @Override
    public void checkPaymentOfAllUsers() throws DAOException {
        String sql = "SELECT f_check_payment()";
        Connection con = getConnection();
        try (Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {

        } catch (SQLException e) {
            throw new DAOException(e);
        }
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
            tariff.setName(rs.getString("name"));
            tariff.setDescription(rs.getString("description"));
            tariff.setCost(rs.getInt("cost"));
            tariff.setFrequencyOfPayment(rs.getInt("frequency_of_payment"));
            tariff.setTariffStatus(TariffStatus.fromString(rs.getString("status")));

            Service service = new Service();
            service.setId(rs.getLong("service_id"));
            service.setServiceType(rs.getString("service_type"));
            tariff.setService(service);

            userTariff.setTariff(tariff);
            userTariff.setDateOfStart(rs.getDate("date_of_start"));
            userTariff.setDateOfLastPayment(rs.getDate("date_of_last_payment"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return userTariff;
    }
}