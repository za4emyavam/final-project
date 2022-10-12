package com.myfp.fp.dao.mysql;

import com.myfp.fp.dao.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class BaseDAOImpl {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /*public void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }*/

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
