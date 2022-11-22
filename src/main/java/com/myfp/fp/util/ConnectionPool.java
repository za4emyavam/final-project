package com.myfp.fp.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public static Connection getConnection() throws SQLException{
        Context cxt;
        Connection c = null;
        try {
            /*cxt = new InitialContext();
            DataSource ds = (DataSource)cxt.lookup("java:comp/env/jdbc/mysql");
            c = ds.getConnection();*/
            cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );
            c = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return c;

    }
}
