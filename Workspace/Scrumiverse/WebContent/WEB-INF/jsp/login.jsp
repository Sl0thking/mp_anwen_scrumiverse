<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Scrumiverse / WIP</title>
<link rel="shortcut icon" type="image/x-icon"  href="<c:url value="/resources/images/scrumiverse_fave_icon.png"/>">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" type="text/css">
</head>
<body class="bg">
<div class="background">
	<div class="login">
        <form action="login_check.htm">
            <div class="form-group">
            	E-Mail:
                <input type="text" class="form-control" id="email" value="example@mail.com" onblur="if (this.value==' '){this.vlaue = 'example@mail.com';}" onfocus="if (this.value == 'example@mail.com') {this.value = ''}">
            </div>
            <div class="form-group">
            	Password:
                <input type="password" class="form-control" id="password" value="password" onblur="if (this.value==''){this.vlaue = 'password';}" onfocus="if (this.value == 'password') {this.value = ''}">
            </div>
            <div class="checkbox">
                <label><input type="checkbox"> Remember me</label>
            </div>
            <button type="submit" id="largebutton" class="btn btn-default">login</button>
            <p>Haven't an account yet? <a href="./register.htm">Register</a> now!</p>
        </form>
    </div>
</div>
<div class="navbar-inverse navbar-fixed-bottom">
    <p class="center">2016 - Scrumiverse - Team Atlas</p>
</div>
</body>
</html>