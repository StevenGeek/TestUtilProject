package com.steven.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zhangyu.chen.o on 2017/5/11.
 */
public class JdbcInsert {
    public static int insert() throws SQLException, ClassNotFoundException {
        Connection conn = JdbcConnection.getConn();
        int i = 0;
        String sql = "insert into aaa (aaa) values (\"aaa\")";
        PreparedStatement pstms;
        pstms = conn.prepareStatement(sql);
        i=pstms.executeUpdate();
        pstms.close();
        conn.close();
        return i;
    }
}
