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
<div class="backlog">
    <div class="userstory">
        <div class="userstory-state-aktive"></div>
        <div class="userstory-titel">[US0001] Titel der User Story zur Testansicht des Backlogs made by GUI (Lasse Nein nicht Lasse) made by me me is me ahhahahahahahaah</div>
        <div class="userstory-sandclock"></div>
        <div class="userstory-time">TIME</div>
        <div class="userstory-memberbox">
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
        </div>
        <div class="userstory-category">CATEGORY FOR THE USER STORY FOR THE USER STORY</div>
        <div class="userstory-sprint">SPRINT FOR THE CURRENT USER STORY</div>
        <div class="userstory-timestats">### / ### / ###</div>
        <div class="userstory-moscow">MO</div>
        <div class="userstory-value">100</div>
        <div class="userstory-risk">100</div>
        <div class="userstory-effort">100</div>
        <div class="userstory-settings-aktive"></div>
    </div>
    <div class="userstory">
    <div class="userstory-state-aktive"></div>
        <div class="userstory-titel">[US0002] Titel der User Story zur Testansicht des Backlogs made by GUI (Lasse Nein nicht Lasse) made by me me is me ahhahahahahahaah</div>
        <div class="userstory-sandclock"></div>
        <div class="userstory-time">TIME</div>
        <div class="userstory-memberbox">
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
        </div>
        <div class="userstory-category">CATEGORY FOR THE USER STORY FOR THE USER STORY</div>
        <div class="userstory-sprint">SPRINT FOR THE CURRENT USER STORY</div>
        <div class="userstory-timestats">### / ### / ###</div>
        <div class="userstory-moscow">MO</div>
        <div class="userstory-value">100</div>
        <div class="userstory-risk">100</div>
        <div class="userstory-effort">100</div>
        <div class="userstory-settings-aktive"></div>
    </div>
    <div class="userstory">
    <div class="userstory-state-aktive"></div>
        <div class="userstory-titel">[US0003] Titel der User Story zur Testansicht des Backlogs made by GUI (Lasse Nein nicht Lasse) made by me me is me ahhahahahahahaah</div>
        <div class="userstory-sandclock"></div>
        <div class="userstory-time">TIME</div>
        <div class="userstory-memberbox">
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
            <div class="userstory-member-bubble"></div>
        </div>
        <div class="userstory-category">CATEGORY FOR THE USER STORY FOR THE USER STORY</div>
        <div class="userstory-sprint">SPRINT FOR THE CURRENT USER STORY</div>
        <div class="userstory-timestats">### / ### / ###</div>
        <div class="userstory-moscow">MO</div>
        <div class="userstory-value">100</div>
        <div class="userstory-risk">100</div>
        <div class="userstory-effort">100</div>
        <div class="userstory-settings-aktive"></div>
    </div>
</div>
    
<div class="button-container">
    <a class="create-entity-btn" href="#">
        <div class="head-btn">US</div>
        <div class="user-btn">new Story</div>
    </a> 
</div>
</body>
</html>