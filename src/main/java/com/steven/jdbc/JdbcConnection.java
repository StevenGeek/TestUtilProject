package com.steven.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhangyu.chen.o on 2017/5/11.
 */
public class JdbcConnection {
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";
        Connection conn = null;
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url,username,password);
        return conn;
    }
}
