package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TransactionDAO;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOPstSQL extends BaseDAOImpl implements TransactionDAO {
    @Override
    public Transaction read(Long id) throws DAOException {
        return null;
    }

    @Override
    public Long create(Transaction entity) throws DAOException {
        String sql = "INSERT INTO transaction(balance_id, type, transaction_amount, transaction_date) VALUES (?, ?::transaction_type, ?, DEFAULT);";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        long resultId = -1;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, entity.getBalanceId());
            preparedStatement.setString(k++, entity.getType().getValue());
            preparedStatement.setInt(k, entity.getTransactionAmount());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(resultId = rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return resultId;
    }

    @Override
    public void update(Transaction entity) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    @Override
    public List<Transaction> readAllByUserBalanceId(Long id) throws DAOException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction t WHERE t.balance_id=(?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getLong("transaction_id"));
                    transaction.setBalanceId(rs.getInt("balance_id"));
                    transaction.setType(TransactionType.fromString(rs.getString("type")));
                    transaction.setTransactionAmount(rs.getInt("transaction_amount"));
                    transaction.setTransactionDate(rs.getDate("transaction_date"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return transactions;
    }
}
