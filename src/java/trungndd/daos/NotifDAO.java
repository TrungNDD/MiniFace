/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import trungndd.db.MyConnection;
import trungndd.dtos.NotifDTO;

/**
 *
 * @author Admin
 */
public class NotifDAO implements Serializable {

    private static Connection con;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() throws Exception {
        MyConnection.closeConnection(con, preStm, rs);
    }

    public static Vector<NotifDTO> getAllNotifByEmail(String email) throws Exception {
        Vector<NotifDTO> dtos = new Vector<>();

        try {
            String sql = "SELECT Message, DateNotif, Notification.IdArticle\n"
                    + "FROM dbo.Notification, dbo.Article, dbo.Account\n"
                    + "WHERE Email = IdPoster AND Article.IdArticle = Notification.IdArticle\n"
                    + "AND Email = ? AND Notification.IdAccount NOT LIKE ?\n"
                    + "ORDER by DateNotif DESC";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, email);
            
            rs = preStm.executeQuery();
            
            while(rs.next()){
                int idArticle = rs.getInt("IdArticle");
                String message = rs.getString("Message");
                Date date = rs.getDate("DateNotif");
                
                NotifDTO dto = new NotifDTO(idArticle, message, date);
                
                dtos.add(dto);
            }
        } finally {
            closeConnection();
        }

        return dtos;
    }
}
