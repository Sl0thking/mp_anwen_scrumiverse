<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ProjectOverview main page --%>
<div class="projectpage">
	<%-- Creates project --%>
	<c:forEach items="${projectList}" var="project">
		<div class="project">
			<img class="project-logo" src="${project.picPath }" height="100" width="100"/>
			<div class="project-name">${project.name }</div>
			<div class="project-desc">${project.description }</div>
			<div class="project-member">Projectmembers: ${project.projectUsers.size()}</div>
			<div class="project-date">Duedate: ${project.dueDate.toString().substring(0,10)}</div>
			<div class="project-control">
				<c:if test="${manageRight[project]}">
					<a class="glyphicon glyphicon-cog project-settings" href="./projectSettings.htm?id=${project.projectID }"></a>
				</c:if>
				<a class="glyphicon glyphicon-triangle-right project-link" href="./selectProject.htm?id=${project.projectID }"></a>
			</div>
		</div>
	</c:forEach>
	<%-- Project creation end --%>
</div>
<div id="quick-button-container">
    <a class="quick-button" href="./addProject.htm">
        <span class="quick-button-title">P</span><span class="quick-button-text">new project</span>
    </a>
</div>