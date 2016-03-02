<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="accountpage">
    <div id="img">
        <img src="${user.profileImagePath}"/>
    </div>
    <div id="editable">
        <form:form action="changeUser.htm" commandName="user">
           <div class="input-group">
               <span class="input-group-addon input-group-addon-fix" id="basic-addon2">Username</span>
               <form:input path="name" type="text" class="form-control input-control" placeholder="${user.name }" aria-describedby="basic-addon1" />
            </div>            
            <div class="input-group">
               <span class="input-group-addon input-group-addon-fix" id="basic-addon2">Password</span>
               <form:password path="password" type="password" class="form-control input-control" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group">
               <span class="input-group-addon input-group-addon-fix" id="basic-addon2">Password</span>
               <input type="password" class="form-control input-control" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group">
               <span class="input-group-addon input-group-addon-fix" id="basic-addon2">E-Mail</span>
               <form:input path="email" type="text" class="form-control input-control" placeholder="${user.email }" aria-describedby="basic-addon1"/>
            </div>
            <form:hidden path="userID"/>
            <button type="submit">save</button>
        </form:form>
    </div>
</div>