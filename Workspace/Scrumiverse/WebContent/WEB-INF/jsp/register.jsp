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
<script type="text/javascript">
//Vergleicht die Inputs "email" und "name", auf Änderungen vom Default.
function checkInputs(){
	var email = $("#email").val();
	var name = $("#name").val();
	if(name=="Firstname Lastname" || email=="example@mail.com"){
		return false;
	} else {
		return true;
	}
}
$(document).ready(function(){
	$(".form-group").keyup(function(){
		var password = $("#password").val();
		var check = $("#passwordcheck").val();
		if($("button").is(":disabled")){
			if(password == check && password != "password" && checkInputs()){
				$("button").prop('disabled', false);
			}
		} else {
			if(password == check || password != "password" || checkInputs()){
				$("button").prop('disabled', true);
			}
		}
	});
});
</script>
</head>
<body class="bg">
<div class="background">
	<div class="login">
        <form role="form">
            <div class="form-group">
            	E-Mail:
                <input type="text" class="form-control" id="email" value="example@mail.com" onblur="if (this.value==' '){this.vlaue = 'example@mail.com';}" onfocus="if (this.value == 'example@mail.com') {this.value = ''}">
            </div>
            <div class="form-group">
            	Name:
                <input type="text" class="form-control" id="name" value="Fristname Lastname" onblur="if (this.value==' '){this.vlaue = 'Fristname Lastname';}" onfocus="if (this.value == 'Fristname Lastname') {this.value = ''}">
            </div>
            <div class="form-group">
            	Password:
                <input type="password" class="form-control" id="password" value="password" onblur="if (this.value==''){this.vlaue = 'password';}" onfocus="if (this.value == 'password') {this.value = ''}">
            	<input type="password" class="form-control" id="passwordcheck" value="password" onblur="if (this.value==''){this.vlaue = 'password';}" onfocus="if (this.value == 'password') {this.value = ''}">
            </div>
            <button type="button" name="register" class="btn btn-default" id="largebutton" disabled>register</button>
        </form>
    </div>
</div>
<div class="information">
	Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
	At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
	Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
	At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
	Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
	At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   
	Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.
	Lorem ipsum dolor sit amet,
</div>
<div class="navbar-inverse navbar-fixed-bottom">
    <p class="center">2016 - Scrumiverse - Team Atlas</p>
</div>
</body>
</html>