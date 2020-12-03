/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.dtos;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class CommentDTO implements Serializable{
    private int idComment, idArticle;
    private String idAccount, content;
    private Date date;

    public CommentDTO() {
    }

    public CommentDTO(int idComment, int idArticle, String idAccount, String content, Date date) {
        this.idComment = idComment;
        this.idArticle = idArticle;
        this.idAccount = idAccount;
        this.content = content;
        this.date = date;
    }

    public CommentDTO(String idAccount, String content, Date date) {
        this.idAccount = idAccount;
        this.content = content;
        this.date = date;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
