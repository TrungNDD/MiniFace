<%-- 
    Document   : showroom
    Created on : Sep 17, 2020, 7:18:58 PM
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
        <title>Showroom - MiniFace</title>
        <meta name="description" content="A responsive, magazine-like website layout with a grid item animation effect when opening the content" />
        <meta name="keywords" content="grid, layout, effect, animated, responsive, magazine, template, web design" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico">
        <link rel="stylesheet" type="text/css" href="public/css/normalize.css" />
        <link rel="stylesheet" type="text/css" href="public/fonts/font-awesome-4.7.0/css/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" href="public/css/style2.css" />
        <link rel="stylesheet" type="text/css" href="public/css/main.css" />
        <script src="public/js/modernizr.custom.js"></script>
        <script src="public/vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="public/js/pagination.js"></script>
    </head>

    <body>
        <div class="container">
            <button id="menu-toggle" class="menu-toggle"><span>Menu</span></button>
            <div id="theSidebar" class="sidebar">
                <button class="close-button fa fa-fw fa-close"></button>
                <h1>MiniFace</h1>
                <h5 id="idAccount">${sessionScope.EMAIL}</h5>
                <div class="search-container" style="margin-top: 1em">
                    <div id="searchForm">
                        <input type="text" class="has-border" placeholder="Search.." name="search" id="txtSearch">
                        <button id="btnSearch"><i class="fa fa-search"></i></button>
                    </div>
                </div>
                <div class="button-post-container">
                    <%--<a href="postArticlePage">Post something</a>--%>
                </div>
                <c:if test="${sessionScope.ROLE ne 'admin'}">
                    <div>
                        <%@include file="postArticle.jsp" %>
                    </div>
                </c:if>
                <div style="position: fixed; bottom: 5%; left: 123px"><a href="logout" style="">Logout</a></div>
            </div>
            <div id="theGrid" class="main">
                <section class="grid">
                    <header class="top-bar">
                        <h2 class="top-bar__headline">Latest articles</h2>
                    </header>
                </section>
                <section class="content">
                    <div class="scroll-wrap">
                    </div>
                    <button class="close-button"><i class="fa fa-close"></i><span>Close</span></button>
                </section>

                <div class="dropdown">
                    <button class="dropbtn">Notification</button>
                    <div class="dropdown-content">

                    </div>
                </div>
            </div>
        </div>
        <!-- /container -->
        <script src="public/js/classie.js"></script>
        <script src="public/js/main.js"></script>
    </body>

</html>
