/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class ArticleDTO implements Serializable{
    private String idPoster, poster, title, content, image, status;
    private int idArticle, likeCount, dislikeCount;
    private Vector<CommentDTO> comments;
    private Date date;

    public ArticleDTO() {
    }

    public ArticleDTO(String idPoster, String title, String content, String image) {
        this.idPoster = idPoster;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public ArticleDTO(int idArticle, String poster, String title, String content, String image, String status, Date date) {
        this.idArticle = idArticle;
        this.poster = poster;
        this.title = title;
        this.content = content;
        this.image = image;
        this.status = status;
        this.date = date;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(String idPoster) {
        this.idPoster = idPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Vector<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Vector<CommentDTO> comments) {
        this.comments = comments;
    }
    
    
}
