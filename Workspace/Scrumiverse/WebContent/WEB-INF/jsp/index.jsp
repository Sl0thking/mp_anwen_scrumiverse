<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		<!-- Highcharts -->
		<script src="https://code.highcharts.com/highcharts.js"></script>
		
		<!-- Scrumiverse -->
		<link rel="stylesheet" type="text/css" href="style.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/${action.name()}.css" />		
		<link rel="shortcut icon" type="image/x-icon"  href="<c:url value="/resources/images/scrumiverse_fave_icon.png"/>">
		<title>Scrumiverse</title>
		
		<script type="text/javascript">
		// handles the selection of the current page in the navigation
		$(document).ready(function(){
			var curPage = location.pathname.split("/")[location.pathname.split("/").length-1];
			$('#menubar a[href="' + curPage + '"]').after('<div class="current-page"></div>');

			// handles the toggles for messages
			//$('#userbar div.list > div.message > div.message-text').click(function(){
			//	$(this).toggleClass('open');
			//});
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
						<a class="extra-menuitem" href="dashboard.htm">
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
					<%-- only show menubar when a project has been selected --%>
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
					</c:if>
				</div>
				<div id="userbar">
					<div class="user-notifications">
						<img alt="user notifications" src="resources/images/index/icon_user_notifications.png">
						<span class="badge" style='<c:if test="${unreadNotifications eq 0}">visibility:hidden</c:if>'>${unreadNotifications}</span>	
						<div class="object-list">
							<div class="list-header">
								<span class="glyphicon glyphicon-bell"></span>
								NOTIFICATIONS
								<div class="list-options">
									<c:if test="${currentUser.notifications.size() ne 0}">
										<a href="./markAllAsSeen.htm" data-toggle="tooltip" title="Mark all as seen">							
											<span class="glyphicon glyphicon-eye-open"></span>
										</a>				
									</c:if>				
								</div>
							</div>
							<div class="list-container">
								<c:choose>
									<c:when test="${currentUser.notifications.size() eq 0}">
										<div class="alert alert-info">
											You currently have no notifications
										</div>
									</c:when>
									<c:otherwise>									
										<div class="list">
											<c:forEach items="${currentUser.notifications}" var="notification">
												<div class="list-object notification">
													<div class="object-text" data-toggle="tooltip" title="${notification.triggerUser.name}">
														${notification.triggerUser.name}
													</div>
													<div class="object-text" data-toggle="tooltip" title="${notification.triggerDescription}">
														${notification.triggerDescription}
													</div>
													<div>${notification.changeEvent}</div>
													<div class="object-options">
														<c:choose>
															<c:when test="${notification.isSeen()}">
																<span class="glyphicon glyphicon-eye-open"></span>
															</c:when>
															<c:otherwise>
																<a href="./markAsSeen.htm?id=${notification.notificationID}" data-toggle="tooltip" title="Mark as seen">
																	<span class="glyphicon glyphicon-eye-close"></span>
																</a>
															</c:otherwise>
														</c:choose>
														<a href="./deleteNotification.htm?id=${notification.notificationID}" data-toggle="tooltip" title="Delete notification">
															<span class="glyphicon glyphicon-trash"></span>
														</a>
													</div>
												</div>
											</c:forEach>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="user-messages">
						<img alt="user messages" src="resources/images/index/icon_user_messages.png">
						<span class="badge" style='<c:if test="${unreadMessages eq 0}">visibility:hidden</c:if>'>${unreadMessages}</span>						
						<div class="object-list">
							<div class="list-header">
								<span class="glyphicon glyphicon-envelope"></span>
								MESSAGES
								<div class="list-options">
									<a data-toggle="tooltip" title="Write new message">							
										<span class="glyphicon glyphicon-plus-sign" data-toggle="modal" data-target="#messagemodal"></span>
									</a>
									<c:if test="${currentUser.messages.size() ne 0}">
										<a href="./markAllAsRead.htm" data-toggle="tooltip" title="Mark all as seen">							
											<span class="glyphicon glyphicon-eye-open"></span>
										</a>				
									</c:if>				
								</div>
							</div>
							<div class="list-container">
								<c:choose>
									<c:when test="${currentUser.messages.size() eq 0}">
										<div class="alert alert-info">
											You currently have no messages
										</div>
									</c:when>
									<c:otherwise>									
										<div class="list">
											<c:forEach items="${currentUser.messages}" var="message">
												<div class="list-object message">
													<div data-toggle="tooltip" title="${message.date.toString().substring(0,16)}">
														<span class="glyphicon glyphicon-calendar"></span>
													</div>
													<div class="object-text" data-toggle="tooltip" title="${message.sender.name}">
														${message.sender.name}
													</div>
													<div class="object-text message-text" data-toggle="tooltip" title="Click to enlarge">
														${message.content}
													</div>
													<div class="object-options">
														<c:choose>
															<c:when test="${message.isSeen()}">
																<span class="glyphicon glyphicon-eye-open"></span>
															</c:when>
															<c:otherwise>
																<a href="./markAsRead.htm?id=${message.messageID}" data-toggle="tooltip" title="Mark as seen">
																	<span class="glyphicon glyphicon-eye-close"></span>
																</a>
															</c:otherwise>
														</c:choose>
														<a href="./deleteMessage.htm?id=${message.messageID}" data-toggle="tooltip" title="Delete message">
															<span class="glyphicon glyphicon-trash"></span>
														</a>
													</div>
												</div>
											</c:forEach>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div id="messagemodal" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">
											<span class="glyphicon glyphicon-envelope"></span>
											NEW MESSAGE
										</h4>
									</div>
									<form:form action="sendMessage.htm" modelAttribute="message">
										<div class="modal-body">
											<div class="input-group"> 
												<span class="input-group-addon">Recievers</span>
												<form:select class="form-control input-control" path="recievers">
													<form:options itemLabel="name" itemValue="email" items="${potentialRecievers}" />
												</form:select>
											</div>
											<div class="input-group">
												<span class="input-group-addon">Message</span>
												<form:textarea class="form-control" path="content" />
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-submit btn-default">
												<span class="glyphicon glyphicon-send"></span>
												Send
											</button>
										</div>								
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div id="user-menu">
						<img alt="user-icon" class="img-circle" src="${currentUser.profileImagePath}">
						<div class="extra-menu">
							<a class="extra-menuitem" href="userSettings.htm">
								<span class="glyphicon glyphicon-cog"></span>
								ACCOUNT SETTINGS
							</a>
							<a class="extra-menuitem" href="logout.htm">
								<span class="glyphicon glyphicon-log-out"></span>
								LOGOUT
							</a>		
						</div>
					</div>
				</div>
				<img alt="test" src="resources/images/index/scrumiverse_sidestripe_right.png">
			</div>
		</c:if>
		<div id="action_content">
			<%-- includes the current page (action) --%>
			<jsp:include page="${action.name()}.jsp" />
		</div>
	</body>
</html>