package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.ConnectionRequestDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.*;
import com.myfp.fp.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionRequestDAOPstSQL extends BaseDAOImpl implements ConnectionRequestDAO {

    @Override
    public ConnectionRequest read(Long id) throws DAOException {
        ConnectionRequest connectionRequest = null;
        String sql = "SELECT * FROM connection_request cr, \"user\" u, tariff t, service s " +
                "WHERE cr.connection_request_id=(?) AND u.user_id=cr.subscriber " +
                "AND t.tariff_id=cr.tariff AND s.service_id=t.service";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    connectionRequest = fillConnectionRequest(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return connectionRequest;
    }

    @Override
    public Long create(ConnectionRequest entity) throws DAOException {
        String sql = "INSERT INTO connection_request(subscriber, city, address, tariff, date_of_change, status)" +
                " VALUES(?, ?, ?, ?, DEFAULT, DEFAULT)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        long resId = -1;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, Math.toIntExact(entity.getSubscriber().getId()));
            preparedStatement.setString(k++, entity.getCity());
            preparedStatement.setString(k++, entity.getAddress());
            preparedStatement.setInt(k, Math.toIntExact(entity.getTariff().getId()));
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
            closeStat(preparedStatement);
        }
        return resId;
    }

    @Override
    public void update(ConnectionRequest entity) throws DAOException {
        String sql = "UPDATE connection_request cr SET status=?::request_status_type WHERE cr.connection_request_id=" + entity.getId();
        //Connection con = getConnection();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, entity.getStatus().getValue());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
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
    public List<ConnectionRequest> readAll() throws DAOException {
        List<ConnectionRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM connection_request cr, \"user\" u, tariff t, service s " +
                "WHERE u.user_id=cr.subscriber AND t.tariff_id=cr.tariff AND t.service=s.service_id";
        Connection con = getConnection();
        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                requests.add(fillConnectionRequest(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return requests;
    }

    private ConnectionRequest fillConnectionRequest(ResultSet rs) throws DAOException {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        try {
            connectionRequest.setId((long) rs.getInt("connection_request_id"));

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
            connectionRequest.setSubscriber(user);

            connectionRequest.setCity(rs.getString("city"));
            connectionRequest.setAddress(rs.getString("address"));

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

            connectionRequest.setTariff(tariff);
            connectionRequest.setDateOfChange(rs.getDate("date_of_change"));
            connectionRequest.setStatus(RequestStatus.fromString(rs.getString("status")));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return connectionRequest;
    }
}
