<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<body class="bg">
	<div id="backlog">
		<c:forEach items="${userstories}" var = "userstory">
			<div class="userstory">
				<div class="userstory-planstate" id="${userstory.planState}"></div>
		        <div class="userstory-titel">${userstory.description}</div>
		        <canvas id="clock" class="userstory-sandclock"></canvas>
		        <div class="userstory-time">${userstory.getRemainingDays()}d</div>
		        <div class="userstory-memberbox">
		        	<c:forEach items="${userstory.getResponsibleUsers()}" var="user">
		        		<img alt="err" src="${user.getProfileImagePath()}">
		        	</c:forEach>
		        </div>
		        <div class="userstory-category">SerflingThings</div>
		        <div class="userstory-sprint">
			        <c:choose>
					    <c:when test="${userstory.relatedSprint == null}">
					    	backlog
					    </c:when>
					    <c:otherwise>
					    	${userstory.relatedSprint.description}
					    </c:otherwise>
					</c:choose>
		        </div>
		        <div class="userstory-timestats">${userstory.getWorkedMinutes()} / ${userstory.getRemainingMinutes()} / ${userstory.getPlannedMinutes()}</div>
		        <div class="userstory-moscow">${userstory.getMoscow().toString().substring(0,1)}</div>
		        <div class="userstory-value">${userstory.businessValue}</div>
		        <div class="userstory-effort">${userstory.effortValue}</div>
        		<div class="userstory-control" id="${userstory.planState}">
					<a class="glyphicon glyphicon-triangle-right userstory-settings" href="./showUserStoryDetails.htm"></a>
				</div>
			</div>
		</div>
	</c:forEach>
</div>  
<div id="quick-button-container">
    <a class="quick-button" href="./newUserStory.htm">
        <span class="quick-button-title">U</span><span class="quick-button-text">new UserStory</span>
    </a> 
</div>