<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="projectpage">
	<c:forEach items="${loggedUser.projects }" var="project">
		<div class="project">
		<div class="project-logo"></div>
		<div class="project-name">${project.name }</div>
		<div class="project-desc">${project.description }</div>
		<div class="project-member">Projectmembers: ${project.projectUsers.size()}</div>
		<div class="project-date">Duedate: ${project.dueDate}</div>
		<div class="project-control">
			<c:if test="${manageRight[project]}">
				<a class="glyphicon glyphicon-cog project-settings" href="./projectSettings.htm?id=${project.projectID }"></a>
			</c:if>
			<a class="glyphicon glyphicon-triangle-right project-link" href="./selectProject.htm?id=${project.projectID }"></a>
		</div>
		</div>
	</c:forEach>
</div>
<div id="quick-button-container">
    <a class="quick-button" href="./addProject.htm">
        <span class="quick-button-title">P</span><span class="quick-button-text">new project</span>
    </a>
</div>