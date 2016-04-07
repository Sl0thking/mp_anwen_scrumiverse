<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(document).ready(function(){
	// handles toggling of dashboard elements
	$('#dashboard .toggle-button').click(function(){
		if ($(this).hasClass('glyphicon-menu-down'))
			$(this).removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
		else
			$(this).removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
		$(this).parent().parent().children('.element-content').slideToggle();
	});
});
</script>
<div id="dashboard">
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-credit-card"></span>
			PROJECT
		</div>
		<div class="element-content project">
			<div class="project-name">${currentProject.name}</div>
			<hr>
			<div class="project-description">${currentProject.description}</div>
		</div>
	</div>
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-retweet"></span>
			CURRENT SPRINT
			<span class="toggle-button glyphicon glyphicon-menu-down"></span>
		</div>
		<div class="element-content sprint">
			<div class="sprint-information">
				<div class="sprint-description">My Sprint</div>
				<div class="sprint-dates">
					<span class="glyphicon glyphicon-calendar" date-toggle="tooltip" title="Startdate - Enddate"></span>
					2016-03-17 - 2016-04-13
				</div>
			</div>
			<div class="sprint-infoelement">
				<div class="infoelement-title">asdasd</div>
				<div class="infoelement-values value-box" data-toggle="tooltip" title="Userstories done / Userstories planned">5 / 25</div>
				<div class="infoelement-progressbar">
					<div class="infoelement-progression"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-briefcase"></span>
			WORK OVERVIEW
			<span class="toggle-button glyphicon glyphicon-menu-down"></span>
		</div>
		<div class="element-content work-overview">
			<div class="elementlist">
				<div class="type">USERSTORIES</div>
				<div class="list">
					<%-- for each userstory that the current user is assigned to : create userstory element --%>
					<c:forEach items="${relevantUserStories}" var="userstory">
						<div class="userstory">							
							<%-- alters the color of the planstate element depending on the current planstate --%>
							<c:choose>
								<c:when test="${userstory.planState eq 'Planning'}">
									<c:set var="planStateColor" value="darkgrey" />
								</c:when>
								<c:when test="${userstory.planState eq 'InProgress'}">
									<c:set var="planStateColor" value="orange" />
								</c:when>
								<c:otherwise>
									<c:set var="planStateColor" value="green" />
								</c:otherwise>
							</c:choose>
							<div class="element-planstate" style="background-color: ${planStateColor};"></div>
							<div class="userstory-name text-box">${userstory.description}</div>
							<div class="userstory-value value-box" data-toggle="tooltip" title="Value">${userstory.businessValue}</div>
							<div class="userstory-effort value-box" data-toggle="tooltip" title="Effort">${userstory.effortValue}</div>
							<div class="userstory-time value-box" data-toggle="tooltip" title="Remaining days">${userstory.getRemainingDays()}	</div>
						</div>
					</c:forEach>
					<%-- end for each userstory that the current user is assigned to : create userstory element --%>
				</div>
			</div>
			<div class="elementlist">
				<div class="type">TASKS</div>
				<div class="list">
					<%-- for each task that the current user is assigned to : create task element --%>
					<c:forEach items="${relevantTasks}" var="task">
						<div class="task">
							<%-- alters the color of the planstate element depending on the current planstate --%>
							<c:choose>
								<c:when test="${task.planState eq 'Planning'}">
									<c:set var="planStateColor" value="darkgrey" />
								</c:when>
								<c:when test="${task.planState eq 'InProgress'}">
									<c:set var="planStateColor" value="orange" />
								</c:when>
								<c:otherwise>
									<c:set var="planStateColor" value="green" />
								</c:otherwise>
							</c:choose>
							<div class="element-planstate" style="background-color: ${planStateColor};"></div>
							<div class="task-description text-box">${task.description}</div>
							<div class="task-timeplan value-box" data-toggle="tooltip" title="Your time planned / time spent / time remaining">
								<fmt:formatNumber value="${plannedTimeOnTask[task] / 60}" maxFractionDigits="1"/>h / 
								<fmt:formatNumber value="${workedTimeOnTask[task] / 60}" maxFractionDigits="1"/>h / 
								<fmt:formatNumber value="${(plannedTimeOnTask[task] - workedTimeOnTask[task]) /60}" maxFractionDigits="1"/>h
							</div>
						</div>					
					</c:forEach>
					<%-- end for each task that the current user is assigned to : create task element --%>
				</div>
			</div>		
		</div>
	</div>	
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-stats"></span>
			REPORTING
			<span class="toggle-button glyphicon glyphicon-menu-down"></span>
		</div>
		<div class="element-content reporting">
		
		</div>
	</div>
</div>