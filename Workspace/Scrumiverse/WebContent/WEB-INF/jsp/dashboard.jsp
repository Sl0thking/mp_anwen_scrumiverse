<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<span class="toggle-button glyphicon glyphicon-menu-down"></span>
		</div>
		<div class="element-content project">
			<div class="project-name">${project.name}</div>
			<hr>
			<div class="project-description">${project.description}</div>
		</div>
	</div>
	<div class="dashboard-element">
		<div class="element-title">
			<span class="glyphicon glyphicon-retweet"></span>
			CURRENT SPRINT
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
					<div class="userstory">
						<!-- TODO: planstate background-color muss per if abfrage gesetzt werden -->
						<div class="element-planstate" style="background-color: red;"></div>
						<div class="userstory-name text-box">[US003] ITSys PLS! WHY U DO THIS TO ME EDEN.</div>
						<div class="userstory-value value-box" data-toggle="tooltip" title="Value">100</div>
						<div class="userstory-effort value-box" data-toggle="tooltip" title="Effort">100</div>
						<div class="userstory-time value-box" data-toggle="tooltip" title="Remaining days">-100d</div>
					</div>
				</div>
			</div>
			<div class="elementlist">
				<div class="type">TASKS</div>
				<div class="list">
					<div class="task">
						<!-- TODO: planstate background-color muss per if abfrage gesetzt werden -->
						<div class="element-planstate" style="background-color: green;"></div>
						<div class="task-description text-box">Implementierung von 5 Seiten VoIP bullshit.</div>
						<div class="task-timeplan value-box" data-toggle="tooltip" title="Time planned / Time spent / Time remaining">300h / 100h / 200h</div>
					</div>
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