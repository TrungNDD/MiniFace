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

/**
 *
 * @author Admin
 */
public class ReactionDAO implements Serializable {

    private static Connection con;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() throws Exception {
        MyConnection.closeConnection(con, preStm, rs);
    }

    public static boolean makeReaction(String idArticle, String idReactor, String emotion) throws Exception {
        boolean check = false;

        try {
            String sql = "EXEC MakeReaction @IdArticle = ?, @IdReactor = ?, @Emotion = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, idArticle);
            preStm.setString(2, idReactor);
            preStm.setString(3, emotion);

            int tmp = preStm.executeUpdate();
            check = tmp > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public static String checkReaction(int idArticle, String idReactor) throws Exception {
        String emotion = null;

        try {
            String sql = "SELECT Emotion FROM dbo.Reaction WHERE IdArticle = ? AND IdReactor = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, idArticle);
            preStm.setString(2, idReactor);
            rs = preStm.executeQuery();
            
            if (rs.next()) {
                emotion = rs.getString("Emotion");
            }
        } finally {
            closeConnection();
        }

        return emotion;
    }
}
