/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import trungndd.db.MyConnection;
import trungndd.dtos.AccountDTO;
import trungndd.resources.MyEncryption;

/**
 *
 * @author Admin
 */
public class AccountDAO implements Serializable{
    private static Connection con;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() throws Exception {
        MyConnection.closeConnection(con, preStm, rs);
    }
    
    public static AccountDTO checkLogin(String email, String password) throws Exception{
        AccountDTO dto = null;
        
        try {
            String sql = "Select Role, Status From Account Where Email = ? and Password = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, MyEncryption.encode(password));
            rs = preStm.executeQuery();
            
            if (rs.next()) {
                dto = new AccountDTO();
                dto.setEmail(email);
                dto.setRole(rs.getString("Role"));
                dto.setStatus(rs.getString("Status"));
            }
        } finally {
            closeConnection();
        }
        
        return dto;
    }
    
    public static boolean add(AccountDTO dto) throws Exception{
        boolean check = false;
        
        try {
            String sql = "Insert into Account Values(?,?,?,?,?)";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getEmail());
            preStm.setString(2, dto.getName());
            String passwordHash = MyEncryption.encode(dto.getPassword());
            preStm.setString(3, passwordHash);
            preStm.setString(4, dto.getRole());
            preStm.setString(5, dto.getStatus());
            
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        
        return check;
    }
    
    public static boolean activate(String email) throws Exception{
        boolean check = false;
        
        try {
            String sql = "UPDATE dbo.Account SET Status = 'Active' WHERE Email = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, email);
            
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        
        return check;
    }
}
