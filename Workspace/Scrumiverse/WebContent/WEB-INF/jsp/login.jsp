<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
$(document).ready(function(){
	/* Append the dismis-button for the error-container */
	$(".error").append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>")
})
</script>
<%-- error-container of login --%>
<div id="error-login" class="error-container">
	<c:if test="${loginError}">
		<div class="alert alert-danger alert-dismissible error" role="alert">Login failed. Check e-mail and/or password.</div>
	</c:if>
	<form:errors cssClass="alert alert-danger alert-dismissible error" role="alert" path="email" element="div"></form:errors>
	<form:errors cssClass="alert alert-danger alert-dismissible error" role="alert" path="name" element="div"></form:errors>
	<form:errors cssClass="alert alert-danger alert-dismissible error" role="alert" path="password" element="div"></form:errors>
</div>
<%-- error-container end --%>
<%-- Login main page --%>
<div class="background">
	<%-- Login form --%>
	<div class="login">
        <form:form action="loginCheck.htm" commandName="user">
            <div class="form-group">
            	E-Mail:
                <form:input path="email" type="email" class="form-control" id="email" placeholder="example@mail.com"/>
            </div>
            <div class="form-group">
            	Password:
                <form:input path="password" type="password" class="form-control" id="password" placeholder="Password" />
            </div>
            <button type="submit" id="largebutton" class="btn btn-default">login</button>
            <p>Don't have an account yet? <a href="./register.htm">Register</a> now!</p>
        </form:form>
    </div>
    <%-- Login form end--%>
</div>
<%-- Fixed "navbar" at the bottom --%>
<div class="navbar-inverse navbar-fixed-bottom">
    <p class="center">2016 - Scrumiverse - Team Scrum Mid</p>
</div>
<%-- "navbar" end --%>