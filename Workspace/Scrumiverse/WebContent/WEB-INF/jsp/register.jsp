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

/* checks input and enable/disable the register-button */
$(document).ready(function(){
	/* Append the dismis-button for the error-container */
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
<%-- Register main page --%>
<div class="background">
	<%-- Register form --%>
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
            <%-- Error-container, shown when an error occurred --%>
            <div class="error-container">
            	<c:if test="${regError}">
            		<div class="alert alert-danger alert-dismissible error">email already used.</div>
            	</c:if>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="email" element="div"/>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="name" element="div"/>
            	<form:errors cssClass="alert alert-danger alert-dismissible error" path="password" element="div"/>
            </div>
            <%-- Error-container end --%>
            <button type="submit" name="register" class="btn btn-default reg-btn" disabled>register</button>
            <a href="./login.htm" class="btn btn-default reg-btn">go back</a>
        </form:form>
    </div>
    <%-- Register form end --%>
</div>
<%-- Information for the user --%>
<div class="information">
	For your username please first enter your surname, followed by whitespace, followed by your name.
	For your password, you must enter at least 6 characters, no spaces, at least 1 digit, at least 1 uppercase and 1 lowercase letter.
</div>
<%-- Information end --%>
<%-- "navbar" at the bottom --%>
<div class="navbar-inverse navbar-fixed-bottom">
    <p class="center">2016 - Scrumiverse - Team Scrum Mid</p>
</div>
<%-- "navbar" end ---%>