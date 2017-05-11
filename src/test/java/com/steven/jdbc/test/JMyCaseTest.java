package com.steven.jdbc.test;

import com.steven.jdbc.JdbcInsert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by zhangyu.chen.o on 2017/5/11.
 */
public class JMyCaseTest {
    @Test
    public void insertTest() throws SQLException, ClassNotFoundException {
        JdbcInsert.insert();
    }
}
