<%-- 
    Document   : signup
    Created on : Sep 17, 2020, 10:19:15 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Signup - MiniFace</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
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
        <%-- auto redirect --%>
        <c:if test="${requestScope.VERIFICATION != null}">
            <meta http-equiv="Refresh" content="1;url=verify?email=${requestScope.DTO.email}&role=${requestScope.DTO.role}">
        </c:if>
    </head>
    <body style="background-color: #999999;">

        <div class="limiter">
            <div class="container-login100">
                <div class="login100-more" style="background-image: url('public/images/MiniFace.png');"></div>

                <div class="wrap-login100 p-l-50 p-r-50 p-t-72 p-b-50">
                    <form class="login100-form validate-form" method="POST" id="sign-up-form" action="signup">
                        <span class="login100-form-title p-b-59">
                            Sign Up
                        </span>

                        <div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
                            <span class="label-input100">Email</span>
                            <input class="input100" type="text" name="email" value="${requestScope.DTO.email}" placeholder="Email addess...">
                            <span class="focus-input100"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Username is required">
                            <span class="label-input100">Username</span>
                            <input class="input100" type="text" name="username" value="${requestScope.DTO.email}" placeholder="Username...">
                            <span class="focus-input100"></span>
                        </div>

                        <div class="wrap-input100 validate-input" id="password-field" data-validate = "Password is required">
                            <span class="label-input100">Password</span>
                            <input class="input100" id="password" type="password" name="pass" placeholder="*************">
                            <span class="focus-input100"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate = "Repeat Password is required">
                            <span class="label-input100">Confirm Password</span>
                            <input class="input100" id="repeat-pass" type="password" name="repeat-pass" placeholder="*************">
                            <span class="focus-input100"></span>
                        </div>

                        <c:if test="${requestScope.MESSAGE != null}">
                            <div style="margin-bottom: 20px">
                                <span class="label-input100" style="color: red">${requestScope.MESSAGE}</span>
                            </div>
                        </c:if>

                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn" type="submit" form="sign-up-form">
                                    Sign Up
                                </button>
                            </div>

                            <a href="loginPage" class="dis-block txt3 hov1 p-r-30 p-t-10 p-b-10 p-l-30">
                                Login
                                <i class="fa fa-long-arrow-right m-l-5"></i>
                            </a>
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
