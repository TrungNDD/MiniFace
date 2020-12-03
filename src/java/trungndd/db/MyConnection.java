/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class MyConnection implements Serializable {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=MiniFace", "sa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    public static void closeConnection(Connection con, PreparedStatement preStm, ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }

        if (preStm != null) {
            preStm.close();
        }

        if (con != null) {
            con.close();
        }
    }
}
