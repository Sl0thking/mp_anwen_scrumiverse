<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/backlog.css" />" rel="stylesheet" type="text/css">
<script>
$(document).ready(function(){
    $(".create-entity-btn").mouseenter(function(){
        $(this).children(".user-btn").animate({width: "toggle"});
    });
    $(".user-btn").mouseleave(function(){
        $(this).animate({width: "toggle"});
    });
});
</script>
</head>
<body class="bg">
<div class="userstory-backlog">
TEST
</div>
    
<div class="button-container">
    <a class="create-entity-btn" href="#">
        <div class="head-btn">P</div>
        <div class="user-btn">new Story</div>
    </a>
    <a class="create-entity-btn" href="#">
        <div class="head-btn">S</div>
        <div class="user-btn">new Story</div>
    </a> 
    <a class="create-entity-btn" href="#">
        <div class="head-btn">T</div>
        <div class="user-btn">new Story</div>
    </a> 
    <a class="create-entity-btn" href="#">
        <div class="head-btn">US</div>
        <div class="user-btn">new Story</div>
    </a> 
</div>
</body>
</html>