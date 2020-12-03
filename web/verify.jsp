<%-- 
    Document   : verify
    Created on : Sep 17, 2020, 9:06:51 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify - MiniFace</title>
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="public/images/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="public/vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="public/vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="public/css/util.css">
        <link rel="stylesheet" type="text/css" href="public/css/main.css">
        <!--===============================================================================================-->
        <%-- when verified successfull this will auto redirect --%>
        <c:if test="${fn:contains(requestScope.MESSAGE, 'successfully')}">
            <meta http-equiv="Refresh" content="1;url=showroomPage">
        </c:if>
    </head>
    <%-- <body>
        <h1>Verify Page</h1>
        We've send you a verification code. Refresh page to resent.
        <form action="verify?action=validate" method="POST">
            <input type="text" name="verify-code"/>
            <input type="submit" value="Submit"/>
        </form>
        <%-- consider doing animation dot dot dot --%>
        <%--<font color="red">${requestScope.MESSAGE}. Redirecting...</font>
    </body>--%>
    
    <body style="background-color: #999999;">

        <div class="limiter">
            <div class="container-login100">
                <div class="login100-more" style="background-image: url('public/images/MiniFace.png');"></div>

                <div class="wrap-login100 p-l-50 p-r-50 p-t-72 p-b-50">
                    <form class="login100-form validate-form" method="POST" id="verify-form" action="verify?action=validate">
                        <span class="login100-form-title p-b-59">
                            Verify
                        </span>

                        <div class="wrap-input100 validate-input">
                            <span class="label-input100">Verify code</span>
                            <input class="input100" type="text" name="verify-code" placeholder="Code">
                            <span class="focus-input100"></span>
                        </div>

                        <div style="margin-bottom: 20px">
                            <span class="label-input100" style="color: red">Check your email for verification code</span>
                            <span class="label-input100" style="color: red">${requestScope.MESSAGE}</span>
                        </div>

                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn" type="submit" form="verify-form">
                                    Verify
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!--===============================================================================================-->
        <script src="public/vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="public/vendor/animsition/js/animsition.min.js"></script>
        <!--===============================================================================================-->
        <script src="public/vendor/bootstrap/js/popper.js"></script>
        <script src="public/vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="public/vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        <script src="public/vendor/daterangepicker/moment.min.js"></script>
        <script src="public/vendor/daterangepicker/daterangepicker.js"></script>
        <!--===============================================================================================-->
        <script src="public/vendor/countdowntime/countdowntime.js"></script>
        <!--===============================================================================================-->
        <script src="public/js/main.js"></script>

    </body>
</html>
