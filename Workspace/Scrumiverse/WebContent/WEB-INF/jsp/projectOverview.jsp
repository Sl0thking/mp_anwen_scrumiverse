<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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

<div class="projectpage">
	<c:forEach items="${loggedUser.projects }" var="project">
		<div class="project">
		<div class="project-logo"></div>
		<div class="project-name">${project.name }</div>
		<div class="project-desc">${project.description }</div>
		<div class="project-member">Projectmembers: ${project.users.size()}</div>
		<div class="project-date">Duedate: ##.##.####</div>
		<div class="project-control">
			<a class="glyphicon glyphicon-cog project-settings" href="./projectSettings.htm?id=${project.projectID }"></a>
			<a class="glyphicon glyphicon-triangle-right project-link" href="./selectProject.htm?id=${project.projectID }"></a>
		</div>
		</div>
	</c:forEach>
</div>

<div class="button-container">
    <a class="create-entity-btn" href="./addProject.htm">
        <div class="head-btn">P</div>
        <div class="user-btn">new Story</div>
    </a> 
</div>