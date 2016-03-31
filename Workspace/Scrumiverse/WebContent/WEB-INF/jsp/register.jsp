<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
//Vergleicht die Inputs "email" und "name", auf Änderungen vom Default.
function checkInputs(){
	var email = $("#email").val();
	var name = $("#name").val();
	if(name=="Firstname Lastname" || name =="" || email=="" || email=="example@mail.com"){
		return false;
	} else {
		return true;
	}
}

$(document).ready(function(){
	$(".error").append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>")
	$(".form-group").keyup(function(){
		var password = $("#password").val();
		var check = $("#passwordcheck").val();
		if($("button").is(":disabled")){
			if(password!="" && password == check && password != "password" && checkInputs()){
				$("button").prop('disabled', false);
			}
		} else {
			if(password != check || password == "password" || !checkInputs()){
				$("button").prop('disabled', true);
			}
		}
	});
}); 
</script>

<div class="background">
	<div class="login">
        <form:form role="form" action="registerUser.htm" commandName="user">
            <div class="form-group">
            	E-Mail:
                <form:input path="email" type="email" class="form-control" id="email" placeholder="example@mail.com" value=""/>
            </div>
            <div class="form-group">
            	Name:
                <form:input path="name" type="text" class="form-control" id="name" placeholder="Surname Name" value=""/>
            </div>
            <div class="form-group">
            	Password:
                <form:input path="password" type="password" class="form-control" id="password" placeholder="password"/>
            	<input type="password" class="form-control" id="passwordcheck" placeholder="password"/>
            </div>
            <div class="error-container">
            	<c:if test="${regError}">
            		<div class="alert alert-danger alert-dismissible error">email already used.</div>
            	</c:if>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="email" element="div"/>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="name" element="div"/>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="password" element="div"/>
            </div>
            <button type="submit" name="register" class="btn btn-default reg-btn" disabled>register</button>
            <a href="./login.htm" class="btn btn-default reg-btn">go back</a>
        </form:form>
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
    <p class="center">2016 - Scrumiverse - Team Scrum Mid</p>
</div>