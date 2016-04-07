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
			<span class="toggle-button glyphicon glyphicon-menu-up"></span>
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
			<span class="toggle-button glyphicon glyphicon-menu-up"></span>
		</div>		
		<div class="element-content sprint">
			<%-- check if there is a sprint in progress --%>
			<c:choose>
				<c:when test="${currentSprint != null}">
					<div id="sprint-information">
						<div class="sprint-description">${currentSprint.description}</div>
						<div class="sprint-dates">
							<span class="glyphicon glyphicon-calendar" date-toggle="tooltip" title="Startdate"></span>
							${currentSprint.startDate.toString().substring(0,10)} -
							<span class="glyphicon glyphicon-calendar" date-toggle="tooltip" title="Enddate"></span>
							${currentSprint.endDate.toString().substring(0,10)}
						</div>
						<hr>
					</div>
					<div id="sprint-data">
						<div class="data-container">
							<div class="data-title">Userstories</div>
							<div class="data-values" data-toggle="tooltip" title="Userstories done / planned">
								${currentSprint.getFinishedUserStories()} / ${currentSprint.getUserStories().size()}
							</div>
							<div class="progressbar">
								<div class="progress" style="width:${currentSprint.getFinishedUserStories() / currentSprint.userStories.size() * 100}%"></div>
							</div>
						</div>
						<div class="data-container">
							<div class="data-title">Time</div>
							<div class="data-values" data-toggle="tooltip" title="Time worked / planned">
								<fmt:formatNumber value="${currentSprint.getWorkedMinutes() / 60}" maxFractionDigits="1"/>h / 
								<fmt:formatNumber value="${currentSprint.getPlannedMinutes() / 60}" maxFractionDigits="1"/>h
							</div>
							<div class="progressbar">
								<div class="progressbar">	
									<%-- alters the color of the progressbar depending on the remaining time --%>												
									<c:choose>
										<c:when test="${currentSprint.getPlannedMinutes() - currentSprint.getWorkedMinutes() < 0}">
											<c:set var="progressColor" value="red" />
										</c:when>
										<c:otherwise>
											<c:set var="progressColor" value="green" />
										</c:otherwise>
									</c:choose>
									<%-- fix for remaining time width error when remaining time is < 0 --%>
									<c:choose>
										<c:when test="${currentSprint.getPlannedMinutes() - currentSprint.getWorkedMinutes() < 0}">
											<c:set var="progressPercentage" value="100" />
										</c:when>
										<c:otherwise>
											<c:set var="progressPercentage" value="${currentSprint.getWorkedMinutes() / currentSprint.getPlannedMinutes() * 100}" />
										</c:otherwise>
									</c:choose>
									<div class="progress" style="width:${progressPercentage}%; background-color:${progressColor}"></div>
								</div>
							</div>
						</div>
						<div class="data-container">
							<div class="data-title">Effort</div>
							<div class="data-values" data-toggle="tooltip" title="Effort completed / combined">
								${currentSprint.getCompletedEffort()} / ${currentSprint.getCombinedEffort()}
							</div>
							<div class="progressbar">
								<div class="progress" style="width:${currentSprint.getCompletedEffort() / currentSprint.getCombinedEffort() * 100}%"></div>
							</div>
						</div>
						<div class="data-container">
							<div class="data-title">Value</div>
							<div class="data-values" data-toggle="tooltip" title="Business value completed / combined">
								${currentSprint.getCompletedBusinessValue()} / ${currentSprint.getCombinedBusinessValue() }
							</div>
							<div class="progressbar">	
								<div class="progress" style="width:${currentSprint.getCompletedBusinessValue() / currentSprint.getCombinedBusinessValue() * 100}%"></div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					No sprint is currently in progress
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-briefcase"></span>
			WORK OVERVIEW
			<span class="toggle-button glyphicon glyphicon-menu-up"></span>
		</div>
		<div class="element-content work-overview">
			<div class="elementlist">
				<div class="type">USERSTORIES</div>
				<div class="list">
					<%-- for each userstory that the current user is assigned to : create userstory element --%>
					<c:forEach items="${relevantUserStories}" var="userstory">
						<%-- checks if the current userstory is not done : will only show userstories in planning or progress --%>
						<c:if test="${userstory.planState ne 'Done'}">
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
								<div class="userstory-name text-box" data-toggle="tooltip" title="${userstory.description}">${userstory.description}</div>
								<div class="userstory-value value-box" data-toggle="tooltip" title="Value">${userstory.businessValue}</div>
								<div class="userstory-effort value-box" data-toggle="tooltip" title="Effort">${userstory.effortValue}</div>
								<div class="userstory-time value-box" data-toggle="tooltip" title="Remaining days">${userstory.getRemainingDays()}d</div>
							</div>
						</c:if>
					</c:forEach>
					<%-- end for each userstory that the current user is assigned to : create userstory element --%>
				</div>
			</div>
			<div class="elementlist">
				<div class="type">TASKS</div>
				<div class="list">
					<%-- for each task that the current user is assigned to : create task element --%>
					<c:forEach items="${relevantTasks}" var="task">
						<%-- checks if the current task is not done : will only show tasks in planning or progress --%>
						<c:if test="${task.planState ne 'Done'}">
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
								<div class="task-description text-box" data-toggle="tooltip" title="${task.description}">${task.description}</div>
								<div class="task-timeplan value-box" data-toggle="tooltip" title="Time planned">
									<fmt:formatNumber value="${plannedTimeOnTask[task.id] / 60}" maxFractionDigits="1"/>h
								</div>
								<div class="task-timeplan value-box" data-toggle="tooltip" title="Time worked">
									<fmt:formatNumber value="${workedTimeOnTask[task.id] / 60}" maxFractionDigits="1"/>h
								</div>
								<div class="task-timeplan value-box" data-toggle="tooltip" title="Time remaining">
									<c:choose>
										<%-- checks if the remaining time is below 0 --%>
										<c:when test="${((plannedTimeOnTask[task.id] - workedTimeOnTask[task.id]) / 60) >= 0}">
											<fmt:formatNumber value="${(plannedTimeOnTask[task.id] - workedTimeOnTask[task.id]) / 60}" maxFractionDigits="1"/>h
										</c:when>
										<c:otherwise>
											0h
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</c:if>				
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
			<span class="toggle-button glyphicon glyphicon-menu-up"></span>
		</div>
		<div class="element-content reporting">
		
		</div>
	</div>
</div>