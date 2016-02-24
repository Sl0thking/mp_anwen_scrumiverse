<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		<!-- Scrumiverse -->
		<c:set var="actionName" value="${action.name()}" />
		<link rel="stylesheet" type="text/css" href="style.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/${actionName}.css" />		
		<link rel="shortcut icon" type="image/x-icon"  href="<c:url value="/resources/images/scrumiverse_fave_icon.png"/>">
		<title>Scrumiverse</title>
	</head>
	<body>
		<c:if test="${isLogged}">
		<div id="navbar">
			<img alt="test" src="resources/images/index/scrumiverse_sidestripe_left.png">
			<div id="logo">
				<img alt="Scrumiverse" src="resources/images/index/scrumiverse_logo.png">
				<div class="extra-menu">
					<a class="extra-menuitem" href="#">DASHBOARD</a>	
					<a class="extra-menuitem" href="#">SETTINGS</a>	
					<a class="extra-menuitem" href="#">PROJECT OVERVIEW</a>	
				</div>
			</div>
			<div id="menubar">
				<div class="menuitem">
					<img alt="backlog icon" src="resources/images/index/icon_backlog.png">
					<a href="#">BACKLOG</a>							
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="userstories icon" src="resources/images/index/icon_userstories.png">
					<a href="#">USERSTORIES</a>							
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="sprints icon" src="resources/images/index/icon_sprints.png">
					<a href="#">SPRINTS</a>			
					<span class="sub-menu">
						<a class="sub-menuitem" href="#">
							<span class="glyphicon glyphicon-chevron-right"></span>
							SUBMENUITEM
						</a>	
						<a class="sub-menuitem" href="#">
							<span class="glyphicon glyphicon-chevron-right"></span>
							SUBMENUITEM
						</a>	
						<a class="sub-menuitem" href="#">
							<span class="glyphicon glyphicon-chevron-right"></span>
							LONGER SUBMENUITEM
						</a>	
					</span>
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="todo icon" src="resources/images/index/icon_todo.png">
					<a href="#">TO-DO</a>
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="report icon" src="resources/images/index/icon_reports.png">
					<a href="#">REPORTS</a>
				</div>
			</div>
			<div id="userbar">
				<div class="user-notifications">
					<img alt="user notifications" src="resources/images/index/icon_user_notifications.png">
					<span class="badge">?</span>
				</div>
				<div class="user-messages">
					<img alt="user messages" src="resources/images/index/icon_user_messages.png">
					<span class="badge">?</span>
				</div>
				<a class="user-icon" href="#">
					<img alt="user icon" src="resources/userPictures/default.png">
				</a>
			</div>
			<img alt="test" src="resources/images/index/scrumiverse_sidestripe_right.png">
		</div>
		</c:if>
		<div id="action_content">
			<c:choose>
				<c:when test="${isLogged && actionName != 'login' && actionName != 'register'}">
					<jsp:include page="${action.name()}.jsp" />
				</c:when>
				<c:when test="${!isLogged}">				
					<c:choose>
						<c:when test="${(actionName == 'login') || (actionName == 'register')}">
							<jsp:include page="${actionName}.jsp" />						
						</c:when>
						<c:otherwise>
							<jsp:include page="login.jsp" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<jsp:include page="projectOverview.jsp" />
				</c:otherwise>
			</c:choose>			
		</div>
	</body>
</html>