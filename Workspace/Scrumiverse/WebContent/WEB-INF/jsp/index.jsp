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
		<link rel="stylesheet" type="text/css" href="style.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/${action.name()}.css" />		
		<link rel="shortcut icon" type="image/x-icon"  href="<c:url value="/resources/images/scrumiverse_fave_icon.png"/>">
		<title>Scrumiverse</title>
		
		<script type="text/javascript">
		// index site specific JavaScript
		$(document).ready(function(){
			var curPage = location.pathname.split("/")[location.pathname.split("/").length-1];
			$('#menubar a[href="' + curPage + '"]').after('<div class="current-page"></div>');
		});
		</script>
	</head>
	<body>
		<c:if test="${isLogged}">
		<div id="navbar">
			<img alt="test" src="resources/images/index/scrumiverse_sidestripe_left.png">
			<div id="logo">
				<img alt="Scrumiverse" src="resources/images/index/scrumiverse_logo.png">
				<div class="extra-menu">
					<c:if test="${currentProject != null}">
					<a class="extra-menuitem" href="backlog.htm">
						<span class="glyphicon glyphicon-th-large"></span>
						DASHBOARD
					</a>		
					</c:if>
					<a class="extra-menuitem" href="projectOverview.htm">
						<span class="glyphicon glyphicon-th-list"></span>
						PROJECT OVERVIEW
					</a>	
				</div>
			</div>
			<div id="menubar">
				<c:if test="${currentProjectId != null}">
				<div class="menuitem">
					<img alt="backlog icon" src="resources/images/index/icon_backlog.png">
					<a href="backlog.htm">BACKLOG</a>							
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="sprints icon" src="resources/images/index/icon_sprints.png">
					<a href="sprintOverview.htm">SPRINTS</a>		
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="todo icon" src="resources/images/index/icon_todo.png">
					<a href="showTasks.htm">TO-DO</a>
				</div>
				<div class="spacer"></div>
				<div class="menuitem">
					<img alt="report icon" src="resources/images/index/icon_reports.png">
					<a href="reporting.htm">REPORTING</a>
				</div>
				</c:if>
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
				<div id="user-menu">
					<img alt="user-icon" src="resources/userPictures/default.png">
					<div class="extra-menu">
						<a class="extra-menuitem" href="logout.htm">
							<span class="glyphicon glyphicon-log-out"></span>
							LOGOUT
						</a>	
						<a class="extra-menuitem" href="#">
							<span class="glyphicon glyphicon-cog"></span>
							ACCOUNT SETTINGS
						</a>	
					</div>
				</div>
			</div>
			<img alt="test" src="resources/images/index/scrumiverse_sidestripe_right.png">
		</div>
		</c:if>
		<div id="action_content">
			<jsp:include page="${action.name()}.jsp" />
		</div>
	</body>
</html>