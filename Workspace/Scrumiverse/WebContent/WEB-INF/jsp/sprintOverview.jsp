<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="resources/javascript/dialog.js"></script>
<script>
	// open the backlog and handels the "button"
	function openBacklog() {
		if ($(".backlog-placeholder").css("left") == "0px") {
			$(".backlog-placeholder").animate({
				left : "-287px"
			}, 500);
			$(".glyphicon-menu-right").css({
				transform : "rotate(0deg)"
			});
		} else {
			$(".backlog-placeholder").animate({
				left : "0px"
			}, 500);
			$(".glyphicon-menu-right").css({
				transform : "rotate(180deg)"
			});
		}
	}

	// hide buttons and actives the click events
	$(document).ready(function() {
		$(".addusbtn").hide();
		$("#removebtn").hide();
		$(".backlogbar").click(function() {
			openBacklog();
		});
		$("#openBacklog").click(function() {
			openBacklog();
		});
		selectUserstory();
		toggleSprintlog();
		moveUserstories();
		if ($(this).parent().attr("class") == "content") {
			checkSelected(".content");
		}
	});

	// attached the selcted(add/remove) userstory to the url
	function moveUserstories() {
		$(".addusbtn, #removebtn").click(
				function() {
					var sprint = $(".openlog").attr("sprintid");
					var backlogusids = [];
					var sprintusids = [];
					getSelected(".content").each(function() {
						backlogusids.push($(this).attr("usid"));
					});
					getSelected(".openlog").each(function() {
						sprintusids.push($(this).attr("usid"));
					});
					var urlchange = "?sprintid=" + sprint + "&addedStories="
							+ backlogusids + "&removedStories=" + sprintusids;
					$(".addusbtn").attr("href",
							$(".addusbtn").attr("href") + urlchange);
					$("#removebtn").attr("href",
							$("#removebtn").attr("href") + urlchange);
					//$.post("addToSprint.htm",change,deselectAll());
				});
	}

	//Closes all Sprintlogs and deselect all Userstories.
	//Set the needed classes to the Sprintlogs.
	function toggleSprintlog() {
		$(".sprint-dropdown").click(
				function() {
					if ($(this).hasClass("glyphicon-triangle-bottom")) {
						closeAll();
						$(this).removeClass("glyphicon-triangle-bottom")
								.addClass("glyphicon-triangle-top");
						$(this).parents(".sprint").addClass("openlog");
						deselectAll();
					} else {
						closeAll();
						$(this).removeClass("glyphicon-triangle-top").addClass(
								"glyphicon-triangle-bottom");
						$(this).parents(".sprint").removeClass("openlog");
						deselectAll();
					}
				});
	}

	//Selects/Deselects the Userstory and handle the Addbtn
	function selectUserstory() {
		$(".userstory").click(function() {
			if ($(this).hasClass("selected")) {
				$(this).removeClass("selected");
				toggleAddbtn();
				toggleRemovebtn();
			} else {
				$(this).addClass("selected");
				toggleAddbtn();
				toggleRemovebtn();
			}
		});
	}

	//Retunrs the selected Userstories in the Element "classselector".
	function getSelected(classselctor) {
		return $(classselctor).find(".userstory.selected");
	}

	// Checks the given parent, if one child has the class ".userstory.selected" it will retuen true.
	function checkSelected(parentClass) {
		if ($(parentClass).find(".userstory.selected").size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Deselect all Userstorys
	function deselectAll() {
		$(".userstory").each(function() {
			$(".selected").removeClass("selected");
		});
	}

	// Close all Sprintlogs
	function closeAll() {
		$(".sprint").each(
				function() {
					$(this).removeClass("openlog");
					$(".glyphicon-triangle-top").removeClass(
							"glyphicon-triangle-top").addClass(
							"glyphicon-triangle-bottom");
				});
	}

	//Toggles the Addbtn
	//Check the selected Userstories and one Sprintlog must be open
	function toggleAddbtn() {
		if (checkSelected(".content") && $(".sprint").hasClass("openlog")) {
			$(".addusbtn").show();
		} else {
			$(".addusbtn").hide();
		}
	}

	//Toggles the Removebtn
	function toggleRemovebtn() {
		if (checkSelected(".sprintlog")) {
			$("#removebtn").show();
		} else {
			$("#removebtn").hide();
		}
	}
</script>
<div id="user-dialog">
	<div class="dialog-header">
		<span class="glyphicon glyphicon-alert"></span> <span
			id="dialog-title"></span>
	</div>
	<div class="dialog-body">
		<div class="dialog-text"></div>
		<a id="dialog-hide" class="btn btn-danger">No</a> <a href="#"
			id="dialog-delete" class="btn btn-success">Yes</a>
	</div>
</div>
<%-- Creates the backlog on the left side of the sprintpage --%>
<div class="backlog-placeholder">
	<div class="backlogbar">
		<span class="glyphicon glyphicon-menu-right barbtn"></span>
	</div>
	<div class="backlog">
		<div class="backlog-header">Backlog</div>
		<div class="backlog-data">
			<div class="data-container">
				Time</br>
				<fmt:formatNumber value="${project.getIceBoxWorkedTime()/60}"
					maxFractionDigits="1" />
				/
				<fmt:formatNumber value="${project.getIceBoxPlannedTime()/60}"
					maxFractionDigits="1" />
				h
				<div class="progressbar">
					<div class="progress"
						style="width:${project.getIceBoxRemainingTime() / project.getIceBoxPlannedTime() * 100}%" /></div>
				</div>
			</div>
			<div class="data-container">
				Effort</br> ${project.getIceBoxDoneEffort()} /
				${project.getIceBoxEffort()}
				<div class="progressbar">
					<div class="progress"
						style="width:${project.getIceBoxDoneEffort() / project.getIceBoxEffort() * 100}%" /></div>
				</div>
			</div>
			<div class="data-container">
				Value</br> ${project.getIceBoxDoneValue()} / ${project.getIceBoxValue()}
				<div class="progressbar">
					<div class="progress"
						style="width:${project.getIceBoxDoneValue() / project.getIceBoxValue() * 100}%" /></div>
				</div>
			</div>
		</div>
		<%-- content contains all userstory of the backlog --%>
		<div class="content">
			<c:forEach items="${project.getIceBox()}" var="userstory">
				<div usid="${userstory.id }" class="userstory">
					<div class="userstory-titel">${userstory.description }</div>
					<div class="userstory-content">
						Time:
						<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}"
							maxFractionDigits="1" />
						/
						<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}"
							maxFractionDigits="1" />
						/
						<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}"
							maxFractionDigits="1" />
						h</br> Effort: ${userstory.getEffortValue()}</br> Value:
						${userstory.getBusinessValue()}
					</div>
				</div>
			</c:forEach>
		</div>
		<c:if test="${canUpdateSprint }">
			<a class="addusbtn" href="./syncBacklogAndSprint.htm">Add to
				Sprint</a>
		</c:if>
	</div>
</div>

<div class="sprintpage">
	<%-- Build the sprintpage with the sprints of the project --%>
	<c:forEach items="${sprints}" var="sprint">
		<div sprintid="${sprint.id}" class="sprint">
			<div planstate="${sprint.planState.toString() }" class="sprint-state"></div>
			<div class="sprint-content">
				<div class="sprint-name">${sprint.description }</div>
				<div class="sprint-stats">
					<div class="sprint-time-overview">
						<%-- Show the sandclock-icon depending on the remaining days  --%>
						<c:choose>
							<c:when test="${sprint.getRemainingDays() > 3}">
								<img src="./resources/images/sandclock/SandClock_4.png" alt=""
									class="sprint-sandclock"></img>
							</c:when>
							<c:when test="${sprint.getRemainingDays() > 1}">
								<img src="./resources/images/sandclock/SandClock_3.png" alt=""
									class="sprint-sandclock"></img>
							</c:when>
							<c:when test="${sprint.getRemainingDays() == 1}">
								<img src="./resources/images/sandclock/SandClock_1.png" alt=""
									class="sprint-sandclock"></img>
							</c:when>
							<c:otherwise>
								<img src="./resources/images/sandclock/SandClock_0.png" alt=""
									class="sprint-sandclock"></img>
							</c:otherwise>
						</c:choose>
						<div class="sprint-date">${sprint.startDate.toString().substring(0,10)}
							-</br>${sprint.endDate.toString().substring(0,10)}</div>
						<div class="sprint-time">
							<c:if test="${sprint.planState.toString()=='InProgress' }">
								<fmt:formatNumber value="${sprint.getRemainingDays() }"
									maxFractionDigits="0" /> d</c:if>
						</div>
					</div>
					<div class="sprint-data">
						<div class="data-container">
							Userstories
							<div class="count">${sprint.getFinishedUserStories() }/
								${sprint.getUserStories().size()}</div>
							<div class="progressbar">
								<div class="progress"
									style="width:${sprint.getFinishedUserStories() / sprint.userStories.size() * 100}%"></div>
							</div>
						</div>
						<div class="data-container">
							Time
							<div class="count">
								<fmt:formatNumber value="${sprint.getWorkedMinutes() / 60 }"
									maxFractionDigits="1" />
								/
								<fmt:formatNumber value="${sprint.getPlannedMinutes() / 60}"
									maxFractionDigits="1" />
								h
							</div>
							<div class="progressbar">
								<div class="progress"
									style="width:${sprint.getRemainingMinutes() / sprint.getPlannedMinutes() * 100}%"></div>
							</div>
						</div>
						<div class="data-container">
							Effort
							<div class="count">${sprint.getCompletedEffort()}/
								${sprint.getCombinedEffort() }</div>
							<div class="progressbar">
								<div class="progress"
									style="width:${sprint.getCompletedEffort() / sprint.getCombinedEffort() * 100}%"></div>
							</div>
						</div>
						<div class="data-container">
							Value
							<div class="count">${sprint.getCompletedBusinessValue()}/
								${sprint.getCombinedBusinessValue() }</div>
							<div class="progressbar">
								<div class="progress"
									style="width:${sprint.getCompletedBusinessValue() / sprint.getCombinedBusinessValue() * 100}%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div planstate="${sprint.planState.toString() }" class="sprint-control">
				<%-- set the link for the modal detailview of the sprint depending on the sprint id --%>
				<a class="glyphicon glyphicon-triangle-right sprint-link" href="#"
					data-toggle="modal" data-target=".modal-detail[sprintid='${sprint.id}']"></a> 
					<span class="glyphicon glyphicon-triangle-bottom sprint-dropdown"></span>
			</div>
			<div class="sprintlog">
				<%-- Creates the sprintlog with the userstories of the sprint --%>
				<c:forEach items="${sprint.getUserStories()}" var="userstory">
					<div usid="${userstory.id }" class="userstory">
						<div class="userstory-titel">${userstory.description }</div>
						<div class="userstory-content">
							Time:
							<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}"
								maxFractionDigits="0" />
							/
							<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}"
								maxFractionDigits="0" />
							/
							<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}"
								maxFractionDigits="0" />
							h</br> Effort: ${userstory.getEffortValue()}</br> Value:
							${userstory.getBusinessValue()}
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<%-- Creates the sprintdetail-view. Sets the id of the modal with the id of the sprint --%>
		<div class="modal-detail modal fade" sprintid="${sprint.id}" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<div class="info-bar">
							<span class="glyphicon glyphicon-cog"></span> SPRINT DETAIL
							<c:if test="${canDeleteSprint}">
								<a class="dialog_action"
									link="./deleteSprint.htm?id=${sprint.id}" data-toggle="tooltip"
									title="Delete Sprint"
									msg="Do you really want to delete this sprint?"> <span
									class="glyphicon glyphicon-trash"></span>
								</a>
							</c:if>
						</div>
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab"
								href=".modal-detail[sprintid='${sprint.id}'] .section-detail">
									<span class="glyphicon glyphicon-info-sign"></span> Detail
							</a></li>
							<li><a data-toggle="tab"
								href=".modal-detail[sprintid='${sprint.id}'] .section-history">
									<span class="glyphicon glyphicon-list-alt"></span> History
							</a></li>
						</ul>
					</div>
					<div class="modal-body">
						<%-- detail-tab of the sprintdetail-view --%>
						<div class="tab-content">
							<div class="section-detail tab-pane fade in active">
								<%--  Fix for commandName bug  --%>
								<c:set var="selectedSprint" value="${sprint}" scope="request" />
								<form:form action="updateSprint.htm"
									modelAttribute="selectedSprint">
									<div class="input-group">
										<span class="input-group-addon">Description</span>
										<form:textarea disabled="${!canUpdateSprint }"
											class="form-control" path="description"
											value="${selectedSprint.description }"></form:textarea>
									</div>
									<div class="input-group">
										<span class="input-group-addon">Criteria</span>
										<form:textarea disabled="${!canUpdateSprint }"
											class="form-control" path="acceptanceCriteria"
											value="${selectedSprint.acceptanceCriteria }"></form:textarea>
									</div>
									<div class="input-group">
										<span class="input-group-addon">Planstate</span>
										<form:select disabled="${!canUpdateSprint }" path="planState"
											class="form-control input-control">
											<form:options items="${planstates}" />
										</form:select>
									</div>
									<div class="input-group input-container-1-of-2">
										<span class="input-group-addon">Start Date</span>
										<form:input disabled="${!canUpdateSprint }" type="date"
											class="form-control input-control" path="startDate"
											value="${selectedSprint.startDate.toString().substring(0,10) }" />
									</div>
									<div class="input-group input-container-2-of-2">
										<span class="input-group-addon">Due Date</span>
										<form:input disabled="${!canUpdateSprint }" type="date"
											class="form-control input-control" path="endDate"
											value="${selectedSprint.endDate.toString().substring(0,10) }" />
									</div>
									<button <c:if test="${!canUpdateSprint }">disabled</c:if>
										type="submit" class="btn btn-submit btn-default">
										<span class="glyphicon glyphicon-save"></span> Save
									</button>
									<form:hidden path="id" />
								</form:form>
								<%-- Contains the data (Time, Effort, Value) of the sprint --%>
								<div class="info-bar">Sprint information</div>
								<div class="sprint-data">
									<div class="modal-data-container">
										Time</br>
										<fmt:formatNumber
											value="${selectedSprint.getRemainingMinutes() / 60 }"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber
											value="${selectedSprint.getPlannedMinutes() / 60}"
											maxFractionDigits="1" />
										h
										<div class="progressbar">
											<div class="progress"
												style="width:${selectedSprint.getRemainingMinutes() / selectedSprint.getPlannedMinutes() * 100}%"></div>
										</div>
									</div>
									<div class="modal-data-container">
										Effort</br> ${selectedSprint.getCompletedEffort()} /
										${selectedSprint.getCombinedEffort() }
										<div class="progressbar">
											<div class="progress"
												style="width:${selectedSprint.getCompletedEffort() / selectedSprint.getCombinedEffort() * 100}%"></div>
										</div>
									</div>
									<div class="modal-data-container">
										Value</br> ${selectedSprint.getCompletedBusinessValue()} /
										${selectedSprint.getCombinedBusinessValue() }
										<div class="progressbar">
											<div class="progress"
												style="width:${selectedSprint.getCompletedBusinessValue() / selectedSprint.getCombinedBusinessValue() * 100}%"></div>
										</div>
									</div>
								</div>
								<%-- Creates the userstory-container of the sprintdetail-view. --%>
								<div class="userstory-container">
									<c:forEach items="${selectedSprint.getUserStories()}"
										var="userstory">
										<div usid="${userstory.id }" class="userstory userstory-fix">
											<div class="userstory-titel">${userstory.description }</div>
											<div class="userstory-content">
												Time:
												<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}"
													maxFractionDigits="0" />
												/
												<fmt:formatNumber
													value="${userstory.getRemainingMinutes()/60}"
													maxFractionDigits="0" />
												/
												<fmt:formatNumber
													value="${userstory.getPlannedMinutes()/60}"
													maxFractionDigits="0" />
												h</br> Effort: ${userstory.getEffortValue()}</br> Value:
												${userstory.getBusinessValue()}
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<%-- history-tab of the sprintdetail-view --%>
							<div
								class="modal-body history-tab section-history tab-pane fade in">
								<c:forEach items="${selectedSprint.getHistory() }" var="history">
									<div class="history-item">
										<div class="history-changeevent">${history.changeEvent }
										</div>
										<div class="history-date">${history.date.toString().substring(0,19) }</div>
										<div class="history-user">${history.user.getName() }</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	<%-- Button-container with the buttons for the creation of  a sprint, removing userstory from 
    	the sprint and open the backlog. Buttons are shown if the user is allowed to use them.--%>
	<div id="quick-button-container">
		<a id="openBacklog" class="quick-button" href="#"> <span
			class="quick-button-title">B</span><span class="quick-button-text">open
				Backlog</span>
		</a>
		<c:if test="${canCreateSprint }">
			<a class="quick-button" href="./addSprint.htm"> <span
				class="quick-button-title">S</span><span class="quick-button-text">new
					Sprint</span>
			</a>
		</c:if>
		<c:if test="${canUpdateSprint }">
			<a id="removebtn" class="quick-button"
				href="./syncBacklogAndSprint.htm"> <span
				class="quick-removebutton-title">X</span><span
				class="quick-removebutton-text">remove Userstory</span>
			</a>
		</c:if>
	</div>
</div>
