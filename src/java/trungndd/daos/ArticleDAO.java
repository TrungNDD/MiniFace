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
import java.util.ArrayList;
import java.util.List;
import trungndd.db.MyConnection;
import trungndd.dtos.ArticleDTO;

/**
 *
 * @author Admin
 */
public class ArticleDAO implements Serializable {

    private static Connection con;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() throws Exception {
        MyConnection.closeConnection(con, preStm, rs);
    }

    public static int getLatestId() throws Exception {
        int latesttId = -1;

        try {
            String sql = "SELECT TOP 1 IdArticle FROM dbo.Article ORDER BY IdArticle DESC";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();

            if (rs.next()) {
                String id = rs.getString("IdArticle");
                latesttId = Integer.parseInt(id.substring(1));
            }
        } finally {
            closeConnection();
        }

        return latesttId + 1;
    }

    public static boolean add(ArticleDTO dto) throws Exception {
        boolean check = false;

        try {
            String sql = "INSERT INTO dbo.Article(IdPoster,Title,Content,Image) VALUES(?,?,?,?)";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getIdPoster());
            preStm.setString(2, dto.getTitle());
            preStm.setString(3, dto.getContent());
            preStm.setString(4, dto.getImage());

            check = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }

        return check;
    }

    public static List<ArticleDTO> getArticleByPage(int pageNo, int pageSize) throws Exception {
        List<ArticleDTO> dtos = null;

        try {
            String sql = "SELECT IdArticle, Username, Title, Content, Date, Image FROM dbo.Article, dbo.Account\n"
                    + "WHERE dbo.Article.Status NOT LIKE 'Deleted' "
                    + "AND IdPoster = Email\n"
                    + "ORDER BY Date DESC\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, (pageNo - 1) * pageSize);
            preStm.setInt(2, pageSize);

            rs = preStm.executeQuery();

            while (rs.next()) {
                int idArticle = rs.getInt("IdArticle");
                String poster = rs.getString("Username");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String image = rs.getString("Image");
                Date date = rs.getDate("Date");
                ArticleDTO dto = new ArticleDTO(idArticle, poster, title, content, image, "", date);

                if (dtos == null) {
                    dtos = new ArrayList<>();
                }
                dtos.add(dto);
            }
        } finally {
            closeConnection();
        }

        return dtos;
    }

    public static int getTotalRecords() throws Exception {
        int totalRecords = -1;

        try {
            String sql = "SELECT COUNT(IdArticle) AS TotalRecords\n"
                    + "FROM dbo.Article\n"
                    + "WHERE Status NOT LIKE 'Deleted'";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();

            if (rs.next()) {
                totalRecords = rs.getInt("TotalRecords");
            }
        } finally {
            closeConnection();
        }

        return totalRecords;
    }

    public static ArticleDTO getArticleById(int idArticle) throws Exception {
        ArticleDTO dto = null;

        try {
            String sql = "SELECT IdArticle, Username, Title, Content, Date, Image, Email FROM dbo.Article, dbo.Account\n"
                    + "WHERE IdPoster = Email AND Article.Status NOT LIKE 'Deleted' AND IdArticle = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, idArticle);

            rs = preStm.executeQuery();

            if (rs.next()) {
                String idPoster = rs.getString("Email");
                String poster = rs.getString("Username");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String image = rs.getString("Image");
                Date date = rs.getDate("Date");
                dto = new ArticleDTO(idArticle, poster, title, content, image, "", date);
                dto.setIdPoster(idPoster);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }

    public static List<ArticleDTO> search(String txtSearch, int pageNo, int pageSize) throws Exception {
        List<ArticleDTO> dtos = new ArrayList<>();

        try {
            String sql = "SELECT IdArticle, Username, Title, Content, Date, Image, dbo.Article.Status\n"
                    + "FROM dbo.Article, dbo.Account\n"
                    + "WHERE Content LIKE ? AND dbo.Article.Status NOT LIKE 'Deleted' AND IdPoster = Email\n"
                    + "ORDER BY Date DESC\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, '%' + txtSearch + '%');
            preStm.setInt(2, (pageNo - 1) * pageSize);
            preStm.setInt(3, pageSize);

            rs = preStm.executeQuery();

            while (rs.next()) {
                int idArticle = rs.getInt("IdArticle");
                String poster = rs.getString("Username");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String image = rs.getString("Image");
                Date date = rs.getDate("Date");
                ArticleDTO dto = new ArticleDTO(idArticle, poster, title, content, image, "", date);

                dtos.add(dto);
            }
        } finally {
            closeConnection();
        }

        return dtos;
    }

    public static int getTotalSearchRecords(String txtSearch) throws Exception {
        int totalRecords = -1;

        try {
            String sql = "SELECT COUNT(IdArticle) AS TotalRecords\n"
                    + "FROM dbo.Article\n"
                    + "WHERE Status NOT LIKE 'Deleted' AND Content LIKE ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, '%' + txtSearch + '%');

            rs = preStm.executeQuery();

            if (rs.next()) {
                totalRecords = rs.getInt("TotalRecords");
            }
        } finally {
            closeConnection();
        }

        return totalRecords;
    }

    public static int getReactionCount(int idArticle, String emotion) throws Exception {
        int count = 0;

        try {
            String sql = "SELECT COUNT(IdReactor) AS Count\n"
                    + "FROM dbo.Article, dbo.Reaction\n"
                    + "WHERE Article.IdArticle = Reaction.IdArticle AND Emotion = ? AND Article.IdArticle = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, emotion);
            preStm.setInt(2, idArticle);

            rs = preStm.executeQuery();

            if (rs.next()) {
                count = rs.getInt("Count");
            }
        } finally {
            closeConnection();
        }

        return count;
    }

    public static boolean delete(int idArticle) throws Exception {
        boolean check = false;

        try {
            String sql = "UPDATE dbo.Article SET Status = 'Deleted' WHERE IdArticle = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, idArticle);

            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }
}
