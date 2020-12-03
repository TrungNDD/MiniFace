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
import java.util.List;
import java.util.Vector;
import trungndd.db.MyConnection;
import trungndd.dtos.CommentDTO;

/**
 *
 * @author Admin
 */
public class CommentDAO implements Serializable {

    private static Connection con;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() throws Exception {
        MyConnection.closeConnection(con, preStm, rs);
    }

    public static Vector<CommentDTO> getAllCommentsById(int idArticle) throws Exception {
        Vector<CommentDTO> dtos = null;

        try {
            String sql = "SELECT IdComment, IdCommenter, Comment.Content, Comment.Date\n"
                    + "FROM dbo.Article, dbo.Comment\n"
                    + "WHERE Article.IdArticle = Comment.IdArticle AND "
                    + "Comment.Status NOT LIKE 'Deleted' AND Article.IdArticle = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, idArticle);

            rs = preStm.executeQuery();

            while (rs.next()) {
                int idComment = rs.getInt("IdComment");
                String idAccount = rs.getString("IdCommenter");
                String content = rs.getString("Content");
                Date date = rs.getDate("Date");

                CommentDTO dto = new CommentDTO(idAccount, content, date);
                dto.setIdComment(idComment);

                if (dtos == null) {
                    dtos = new Vector<>();
                }

                dtos.add(dto);
            }
        } finally {
            closeConnection();
        }

        return dtos;
    }

    public static boolean makeComment(int idArticle, String idCommenter, String content) throws Exception {
        boolean check = false;

        try {
            String sql = "EXEC MakeComment @IdArticle = ?, @IdCommenter = ?, @Comment = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);

            preStm.setInt(1, idArticle);
            preStm.setString(2, idCommenter);
            preStm.setString(3, content);

            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public static int getLatestCommentId() throws Exception {
        int latestId = -1;

        try {
            String sql = "SELECT TOP 1 IdComment FROM dbo.Comment ORDER BY IdComment DESC";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);

            rs = preStm.executeQuery();

            if (rs.next()) {
                latestId = rs.getInt("IdComment");
            }
        } finally {
            closeConnection();
        }

        return latestId;
    }

    public static boolean delete(int idComment) throws Exception {
        boolean check = false;

        try {
            String sql = "UPDATE dbo.Comment SET Status = 'Deleted' WHERE IdComment = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, idComment);

            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }
}
