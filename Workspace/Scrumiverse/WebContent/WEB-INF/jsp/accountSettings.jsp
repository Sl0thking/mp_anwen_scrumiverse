<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>
$(document).ready(function(){
	/* Append the dismis-button for the error-container */
	$(".error").append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>")
})
</script>
<%-- error-container of accountsettings --%>
<div class="error-container">
	<c:if test="${param.error eq 1}"><div class="alert alert-danger alert-dismissible error" role="alert">unknown error occoured</div></c:if>
	<c:if test="${param.error eq 2}"><div class="alert alert-danger alert-dismissible error" role="alert">cannot upload file (empty, wrong format or bigger than 4MB)</div></c:if>
</div>
<%-- error-container end --%>
<%-- AccountSettings main page --%>
<div id="accountpage">
	<%-- Userpicture --%>
    <div id="account-img">
    	<form method="POST" action="changeUserPic.htm" enctype="multipart/form-data" >
        	<img src="${user.profileImagePath}"/>
        	<input name="image" type="file"/>
        	<button class="btn btn-default" type="submit">Upload</button>
        </form>
    </div>
    <%-- Userpicture end --%>
    <%-- Userdetail form --%>
    <div id="editable">
        <form:form action="changeUser.htm" commandName="user">
           <div class="input-group">
               <span class="input-group-addon" id="basic-addon2">Username</span>
               <form:input path="name" type="text" class="form-control input-control" placeholder="${user.name }" aria-describedby="basic-addon1" />
            </div>            
            <div class="input-group">
               <span class="input-group-addon" id="basic-addon2">Password</span>
               <form:password path="password" type="password" class="form-control input-control" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group">
               <span class="input-group-addon" id="basic-addon2">Password</span>
               <input type="password" class="form-control input-control" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group">
               <span class="input-group-addon" id="basic-addon2">E-Mail</span>
               <form:input path="email" type="text" class="form-control input-control" placeholder="${user.email }" aria-describedby="basic-addon1"/>
            </div>
            <form:hidden path="userID"/>
            <form:hidden path="profileImagePath"/>
            <button class="btn btn-default" type="submit">save</button>
        </form:form>
    </div>
    <%-- Userdetail end --%>
</div>