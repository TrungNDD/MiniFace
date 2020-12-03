<%-- 
    Document   : postAritcle
    Created on : Sep 19, 2020, 11:00:03 AM
    Author     : Admin
--%>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Article - MiniFace</title>
    </head>
    <body>--%>
        <c:set var="DTO" value="${requestScope.DTO}"/>
        <h3>Post Article</h3>
        <form name="post-form" action="postArticle" id="post-form"
              enctype="multipart/form-data" method="POST">
            <input class="has-border" type="text" name="title" value="${DTO.title}" required minlength="2" maxlength="50" placeholder="Title..." style="width: 100%;"/><br>
            <textarea class="has-border"  name="content" value="${DTO.content}" required
                      placeholder="Content..." rows="5" style="width: 100%"></textarea><br>
            <label for="file-upload" id="file-upload-label">Choose a image</label>
            <input type="file" name="img" accept="image/jpg,image/png,image/jpeg,image/gif" id="file-upload" style="display: none"/><br>
            <input type="submit" value="Post" name="action"/>
        </form>
        <font color="red">${requestScope.MESSAGE}</font>
    <%--</body>
</html>--%>
