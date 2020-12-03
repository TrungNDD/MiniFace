<%-- 
    Document   : article.jsp
    Created on : Sep 21, 2020, 4:09:13 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${DTO.title} - MiniFace</title>
        <meta name="description" content="A responsive, magazine-like website layout with a grid item animation effect when opening the content" />
        <meta name="keywords" content="grid, layout, effect, animated, responsive, magazine, template, web design" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="public/css/normalize.css" />
        <link rel="stylesheet" type="text/css" href="public/fonts/font-awesome-4.7.0/css/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" href="public/css/style1.css" />
        <link rel="stylesheet" type="text/css" href="public/css/main.css" />
        <script src="public/js/modernizr.custom.js"></script>
        <script src="public/vendor/jquery/jquery-3.2.1.min.js"></script>
    </head>

    <body style="font-family: Poppins-Regular">
        <c:set value="${requestScope.DTO}" var="DTO"/>
        <div hidden id="idArticle">${DTO.idArticle}</div>
        <div class="container">
            <button id="menu-toggle" class="menu-toggle"><span>Menu</span></button>
            <div id="theSidebar" class="sidebar">
                <button class="close-button fa fa-fw fa-close"></button>
                <h1 style="font-family: Poppins-Bold">MiniFace</h1>
                <a href="showroomPage" class="fa fa-chevron-circle-left" style="font-size: 140%; margin-top: 5px"></a>
                <div style="position: fixed; bottom: 5%; left: 123px"><a href="logout" style="">Logout</a></div>
            </div>
            <div id="theGrid" class="main">
                <section class="content content--show" style="top: 0px;">
                    <div class="scroll-wrap">
                        <c:if test="${sessionScope.EMAIL eq DTO.idPoster or sessionScope.ROLE eq 'admin'}">
                            <a href="delete?action=article&idArticle=${DTO.idArticle}" class="close-button close-button--show delete"><i class="fa fa-trash-o"></i></a>
                            </c:if>
                        <article class="content__item content__item--show">
                            <h2 class="title title--full" style="font-family: Poppins-Bold">${DTO.title}</h2>
                            <div class="meta meta--full">
                                <div class="meta__author">${DTO.poster}</div>
                                <c:if test="${DTO.image != null}">
                                    <div  style="display: flex; justify-content: center;">
                                        <img src="${DTO.image}" alt="${DTO.poster}" style="max-width: 100%; height: auto; "/>
                                    </div>
                                </c:if>
                                <div class="meta__date" style="max-width: 100%"><i class="fa fa-calendar-o"></i>${DTO.date}</div>
                            </div>
                            <p>${DTO.content}</p>

                            <div hidden id="emotion">${requestScope.EMOTION}</div>
                            <div hidden id="email">${sessionScope.EMAIL}</div>

                            <%-- only show this reaction section for member --%>
                            <c:if test="${sessionScope.ROLE eq 'member'}">
                                <span class="reaction-container">
                                    <span class="label-input100">Do you like this? </span>
                                    <span id="likeCount" class="reaction-counter">${DTO.likeCount}</span>
                                    <i class="fa 
                                       <c:if test="${requestScope.EMOTION eq 'like'}" var="isLiked">
                                           fa-thumbs-up disable
                                       </c:if>
                                       <c:if test="${not isLiked}">
                                           fa-thumbs-o-up
                                       </c:if>" id="like" aria-hidden="true" style="font-size: 150%">
                                    </i>
                                    <span id="dislikeCount" class="reaction-counter">${DTO.dislikeCount}</span>
                                    <i class="fa 
                                       <c:if test="${requestScope.EMOTION eq 'dislike'}" var="isLiked">
                                           fa-thumbs-down disable
                                       </c:if>
                                       <c:if test="${not isLiked}">
                                           fa-thumbs-o-down
                                       </c:if>" id="dislike" aria-hidden="true" style="font-size: 150%">
                                    </i>
                                </span>
                            </c:if>

                            <%-- display reaction count for admin --%>
                            <c:if test="${sessionScope.ROLE eq 'admin'}">
                                <span class="reaction-container">
                                    <span id="likeCount" class="reaction-counter">${DTO.likeCount}</span>
                                    <i class="fa fa-thumbs-up disable" id="like" aria-hidden="true" style="font-size: 150%">
                                    </i>
                                    <span id="dislikeCount" class="reaction-counter">${DTO.dislikeCount}</span>
                                    <i class="fa fa-thumbs-down disable" id="dislike" aria-hidden="true" style="font-size: 150%">
                                    </i>
                                </span>
                            </c:if>

                            <div class="comments-container">Comments
                                <c:forEach var="comment" items="${DTO.comments}">
                                    <div class="comment">
                                        <div class="commenter">
                                            <i class="fa fa-user-circle-o"></i>
                                            ${comment.idAccount} - (${comment.date})
                                            <c:if test="${sessionScope.EMAIL eq comment.idAccount  or sessionScope.ROLE eq 'admin'}">
                                                <a href="delete?action=comment&idArticle=${DTO.idArticle}&idComment=${comment.idComment}" class="fa fa-ban delete" style="float: right"></a>
                                            </c:if>
                                        </div>
                                        ${comment.content}
                                    </div>
                                </c:forEach>
                            </div>

                            <c:if test="${sessionScope.ROLE eq 'member'}">
                                <div class="wrap-input100 p-l-50">
                                    <input id="input-comment" class="input100" type="text" name="comment" placeholder="Say something...">
                                    <span class="focus-input100"></span>
                                </div>
                            </c:if> 
                        </article>
                    </div>
                </section>
            </div>
        </div>
        <!-- /container -->
        <script src="public/js/classie.js"></script>
        <script src="public/js/article.js"></script>
    </body>

</html>
