package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class BaseDAOImpl {

    public Connection getConnection() throws DAOException {
        //return connection;
        try {
            return ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void closeConnection(Connection con) throws DAOException {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    protected void rollback(Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            System.out.println("Exception in rollback");
            throw new DAOException(ex);
        }
    }

    protected void closeStat(AutoCloseable con) throws DAOException {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
