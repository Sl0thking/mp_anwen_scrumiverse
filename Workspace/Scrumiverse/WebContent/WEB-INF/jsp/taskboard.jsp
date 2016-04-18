<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="resources/javascript/dialog.js"></script>
<script>
	$(document).ready(function() {
		$(".quick-button").hide();
		$(".userstory").click(function() {
			deselectAll();
			$(this).addClass("selected");
			if ('${canCreateTask}') {
				$('.quick-button[href="./addTask.htm"]').show();
				var userStoryId = $(this).attr("userStoryId");
				$(".quick-button").attr("href","addTask.htm?id="+ userStoryId);
			}
		});

		// task-details specific JavaScript    
		// handles load of modal on pageload 
		var target = document.location.hash.replace("#", "");
		if (target.length) {
			var targetModal = $('.modal-detail[taskid="'+ target + '"]');
			if (targetModal) {
				targetModal.modal('show');
				history.pushState("", document.title,window.location.pathname);
			}
		}

		// handles adding of users to task
		$('.section-detail .user-add button').click(function(e) {
			var taskID = $(this).closest('.modal-detail').attr('taskid');
			var userID = $('.modal-detail[taskid="'+ taskID
			          		+ '"] .section-detail .user-add select > option:selected')
			          		.attr('value');
			if (userID) {
				var targetUrl = './addUserToTask.htm?taskID='+ taskID+ '&userID='
									+ userID;
				window.location.href = targetUrl;
			}
		});

		// handles adding of tags to task
		$('.section-detail input.add-tag').keypress(function(e) {
			if (e.which == 13) {
				var taskID = $(this).closest('.modal-detail').attr('taskid');
				var tags = $.trim($(this).val());
				if (tags != "") {
					var targetUrl = './addTagsToTask.htm?taskID='+ taskID
										+ '&tags='+ tags;
					window.location.href = targetUrl;
				}
				return false;
			}
		});
	});

	function deselectAll() {
		$(".userstory").each(function() {
			$(".selected").removeClass("selected");
		});
	}
</script>
<div id="user-dialog">
	<div class="dialog-header">
		<span class="glyphicon glyphicon-alert"></span> <span
			id="dialog-title">Delete Project</span>
	</div>
	<div class="dialog-body">
		<div class="dialog-text"></div>
		<a id="dialog-hide" class="btn btn-danger">No</a> <a href="#"
			id="dialog-delete" class="btn btn-success">Yes</a>
	</div>
</div>
<div>
	<table class="taskboard-heading">
		<tr>
			<th class="userstory-section">USERSTORY</th>
			<th class="task-section">PLANNED</th>
			<th class="task-section">TO-DO</th>
			<th class="task-section">DONE</th>
		</tr>
	</table>
</div>
<div class="taskboard-content">
	<table class="taskboard">
		<c:forEach items="${userStories}" var="userStory">
			<tr>
				<td class="userstory-section item-section">
					<div class="userstory" userStoryId="${userStory.id}">
						<div id="${userStory.planState.toString() }"
							class="userstory-state"></div>
						<div class="userstory-content">
							<div class="userstory-titel">${userStory.description}</div>
							<div class="userstory-stats">
								<div class="time-container">
									<div class="timebox">
										<c:if test="${userStory.planState!='Done'}">
											<c:choose>
												<c:when test="${userStory.getRemainingDays() > 3}">
													<img src="./resources/images/sandclock/SandClock_4.png"
														alt="" class="hourglass"></img>
												</c:when>
												<c:when test="${userStory.getRemainingDays() > 1}">
													<img src="./resources/images/sandclock/SandClock_3.png"
														alt="" class="hourglass"></img>
												</c:when>
												<c:when test="${userStory.getRemainingDays() == 1}">
													<img src="./resources/images/sandclock/SandClock_1.png"
														alt="" class="hourglass"></img>
												</c:when>
												<c:otherwise>
													<img src="./resources/images/sandclock/SandClock_0.png"
														alt="" class="hourglass"></img>
												</c:otherwise>
											</c:choose>
											<div class="hourglass-time">${userStory.getRemainingDays()}
												d</div>
										</c:if>
									</div>
									<div class="duetime">${userStory.getRemainingDays()}d</div>
								</div>
								<div class="info-container">
									<div class="moscow" data-toggle="tooltip" title="MoSCoW">${userStory.moscow.toString().substring(0,1)}</div>
									<div class="value" data-toggle="tooltip" title="Value">${userStory.businessValue}</div>
									<div class="risk" data-toggle="tooltip" title="Risk">${userStory.risk}</div>
									<div class="effort" data-toggle="tooltip" title="Effort">${userStory.effortValue}</div>
									<div class="timestats">
										<fmt:formatNumber value="${userStory.getWorkedMinutes()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber
											value="${userStory.getRemainingMinutes()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${userStory.getPlannedMinutes()/60}"
											maxFractionDigits="1" />
										h
									</div>
								</div>
								<div class="member-container">
									<c:choose>
										<c:when test="${userStory.getResponsibleUsers().size() <= 6}">
											<c:forEach items="${userStory.getResponsibleUsers()}"
												var="user">
												<div class="userstory-member">
													<img src="${user.profileImagePath}" data-toggle="tooltip"
														title="${user.name }" />
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach begin="0" end="5" var="user">
												<div class="userstory-member">
													<img
														src="${userStory.getResponsibleUsers()[user].profileImagePath}"
														data-toggle="tooltip" title="${user.name }" />
												</div>
											</c:forEach>
											<div class="userstory-member">...</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<span id="${userStory.planState.toString() }"
							class="userstory-link" href="#"></span>
					</div>
				</td>
				<td class="task-section item-section"><c:forEach
						items="${tasksOfUserStories[userStory]}" var="task">
						<c:if test="${task.planState.toString() eq 'Planning'}">
							<div class="task">
								<div class="task-content">
									<div class="task-name">${task.description}</div>
									<div class="task-time">
										<fmt:formatNumber value="${task.getWorkMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getRemainingMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getPlannedMin()/60}"
											maxFractionDigits="1" />
										h
									</div>
									<div class="task-memberbox">
										<c:choose>
											<c:when test="${task.getResponsibleUsers().size() <= 6}">
												<c:forEach items="${task.getResponsibleUsers()}" var="user">
													<div class="task-member">
														<img src="${user.profileImagePath}" data-toggle="tooltip"
															title="${user.name }" />
													</div>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach begin="0" end="5">
													<div class="task-member">
														<img src="${user.profileImagePath}" data-toggle="tooltip"
															title="${user.name }" />
													</div>
												</c:forEach>
												<div class="task-member">...</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<a href="#" class="glyphicon glyphicon-triangle-right task-link"
									id="${task.planState.toString()}" data-toggle="modal"
									data-target=".modal-detail[taskid='${task.getId()}']"></a>
							</div>
							<div class="modal-detail modal fade" taskid="${task.id}"
								role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<div class="info-bar">
												<span class="glyphicon glyphicon-cog"></span> TASK DETAIL
												#${task.id}
												<c:if test="${canDeleteTask}">
													<a class="dialog_action"
														link="./deleteTask.htm?taskID=${task.id}"
														data-toggle="tooltip" title="Delete Task"
														msg="Do you really want to delete this task?"> <span
														class="glyphicon glyphicon-trash"></span>
													</a>
												</c:if>
											</div>
											<ul class="nav nav-tabs">
												<li class="active"><a data-toggle="tab"
													href=".modal-detail[taskid='${task.id}'] .section-detail">
														<span class="glyphicon glyphicon-info-sign"></span> Detail
												</a></li>
												<li><a data-toggle="tab"
													href=".modal-detail[taskid='${task.id}'] .section-history">
														<span class="glyphicon glyphicon-list-alt"></span> History
												</a></li>
											</ul>
										</div>
										<div class="modal-body">
											<div class="tab-content">
												<div class="section-detail tab-pane fade in active">
													<div class="member-list">
														<%-- for each user in task : create user bubble --%>
														<c:forEach items="${task.responsibleUsers}" var="user">
															<img class="member-img" alt="${user.getName()}"
																src="${user.profileImagePath}" data-toggle="tooltip"
																title="${user.getName()}" />
														</c:forEach>
														<%-- end for each user in task : create user bubble --%>
													</div>
													<div class="input-group input-group-sm user-add">
														<select class="selectpicker form-control">
															<option value="" disabled selected hidden="true">Select
																user to add</option>
															<%-- for each user in project : filter out users that are already in task --%>
															<c:forEach items="${currentProject.projectUsers}"
																var="projectUser">
																<c:set var="isAlreadyInTask" value="false" />
																<c:forEach items="${task.responsibleUsers}"
																	var="taskUser">
																	<c:if test="${taskUser == projectUser.user}">
																		<c:set var="isAlreadyInTask" value="true" />
																	</c:if>
																</c:forEach>
																<c:if test="${not isAlreadyInTask}">
																	<option value="${projectUser.user.userID}"
																		label="${projectUser.user.name}" />
																</c:if>
															</c:forEach>
															<%-- end for each user in project : filter out users that are already in task --%>
														</select> <span class="input-group-btn">
															<button class="btn btn-default" type="submit"
																title="Add user to task">+</button>
														</span>
													</div>
													<c:set var="selectedTask" value="${task}" scope="request" />
													<form:form action="updateTask.htm"
														modelAttribute="selectedTask">
														<div class="input-group">
															<span class="input-group-addon">Description</span>
															<form:textarea class="form-control"
																style="resize:vertical" path="description"
																value="${selectedTask.description}" />
														</div>
														<div class="input-group">
															<span class="input-group-addon">Criteria</span>
															<form:textarea class="form-control"
																style="resize:vertical" path="acceptanceCriteria"
																value="${selectedTask.acceptanceCriteria}" />
														</div>
														<div class="input-group">
															<span class="input-group-addon">Planstate</span>
															<form:select class="form-control input-control"
																path="planState">
																<form:options items="${planStates}" />
															</form:select>
														</div>
														<div class="tag-list-header">Tags</div>
														<div class="tag-list">
															<div class="tag-container">
																<%-- for each tag in task : create tag element --%>
																<c:forEach items="${task.getTags()}" var="tag">
																	<div class="tag">
																		${tag} <a
																			href="./removeTagFromTask.htm?taskID=${task.id}&tag=${tag}">
																			<span class="glyphicon glyphicon-remove"></span>
																		</a>
																	</div>
																</c:forEach>
																<%-- end for each tag in task : create tag element --%>
															</div>
															<input class="add-tag" type="text"
																placeholder="Add tag (Seperate multiple tags with commas)"></input>
														</div>
														<button type="submit" class="btn btn-submit btn-default">
															<span class="glyphicon glyphicon-save"></span> Save
														</button>
														<form:hidden path="id" />
													</form:form>
													<div class="info-bar">Timetable for users</div>
													<table class="detail-table">
														<tr>
															<th>Users</th>
															<th>Estimated</th>
															<th>Spent</th>
															<th>Remaining</th>
															<th></th>
														</tr>
														<%-- for each user/plannedtime in task : create user time row --%>
														<c:forEach items="${task.getPlannedMinOfUsers()}"
															var="userEntry">
															<tr>
																<td><img class="member-img"
																	alt="${userEntry.key.name}"
																	src="${userEntry.key.profileImagePath}"
																	data-toggle="tooltip" title="${userEntry.key.name}" />
																	${userEntry.key.name}</td>
																<td>
																	<%-- choose whether its the currently logged in user --%>
																	<c:choose>
																		<c:when
																			test="${userEntry.key eq currentUser && task.planState eq 'Planning'}">
																			<input type="text" value="${userEntry.value}">
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber value="${userEntry.value / 60}"
																				maxFractionDigits="1" />h																	
																	</c:otherwise>
																	</c:choose>
																</td>
																<td><fmt:formatNumber
																		value="${userWorkedTimeOfTask[task][userEntry.key] / 60}"
																		maxFractionDigits="1" />h</td>
																<td><c:choose>
																		<c:when
																			test="${((userEntry.value - userWorkedTimeOfTask[task][userEntry.key]) / 60) >= 0}">
																			<fmt:formatNumber
																				value="${(userEntry.value - userWorkedTimeOfTask[task][userEntry.key]) / 60}"
																				maxFractionDigits="1" />h
																	</c:when>
																		<c:otherwise>
																		0h
																	</c:otherwise>
																	</c:choose></td>
																<td><a class="dialog_action"
																	link="./removeUserFromTask.htm?taskID=${task.id}&userID=${userEntry.key.userID}"
																	data-toggle="tooltip" title="Remove User"
																	msg="Do you really want to remove ${userEntry.key.name} from this task?">
																		<span class="glyphicon glyphicon-remove"></span>
																</a></td>
															</tr>
														</c:forEach>
														<%-- end for each user/plannedtime in task : create user time row --%>
														<tr>
															<td>Total</td>
															<td><fmt:formatNumber
																	value="${task.getPlannedMin() / 60}"
																	maxFractionDigits="1" />h</td>
															<td><fmt:formatNumber
																	value="${task.getWorkMin() / 60}" maxFractionDigits="1" />h
															</td>
															<td>
																<%-- fix for wrong eclipse error --%> <c:set
																	var="remainingMin"
																	value="${task.getPlannedMin() - task.getWorkMin()}" />
																<fmt:formatNumber value="${remainingMin / 60}"
																	maxFractionDigits="1" />h
															</td>
															<td></td>
														</tr>
													</table>
												</div>
												<div
													class="modal-body history-tab section-history tab-pane fade in">
													<c:forEach items="${task.getHistory()}" var="history">
														<div class="history-item">
															<div class="history-changeevent">${history.changeEvent }
															</div>
															<div class="history-date">${history.date }</div>
															<div class="history-user">${history.user.getName() }</div>
														</div>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach></td>
				<td class="task-section item-section"><c:forEach
						items="${tasksOfUserStories[userStory]}" var="task">
						<c:if test="${task.planState.toString() eq 'InProgress'}">
							<div class="task">
								<div class="task-content">
									<div class="task-name">${task.description}</div>
									<div class="task-time">
										<fmt:formatNumber value="${task.getWorkMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getRemainingMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getPlannedMin()/60}"
											maxFractionDigits="1" />
										h
									</div>
									<div class="task-memberbox">
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
									</div>
								</div>
								<a href="#"
									class="glyphicon glyphicon-triangle-right task-link ${task.planState.toString()}"
									data-toggle="modal"
									data-target=".modal-detail[taskid='${task.id}']"></a>
							</div>
						</c:if>
					</c:forEach></td>
				<td class="task-section item-section"><c:forEach
						items="${tasksOfUserStories[userStory]}" var="task">
						<c:if test="${task.planState.toString() eq 'Done'}">
							<div class="task">
								<div class="task-content">
									<div class="task-name">${task.description}</div>
									<div class="task-time">
										<fmt:formatNumber value="${task.getWorkMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getRemainingMin()/60}"
											maxFractionDigits="1" />
										/
										<fmt:formatNumber value="${task.getPlannedMin()/60}"
											maxFractionDigits="1" />
										h
									</div>
									<div class="task-memberbox">
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
										<div class="task-member"></div>
									</div>
								</div>
								<a href="#"
									class="glyphicon glyphicon-triangle-right task-link ${task.planState.toString()}"
									data-toggle="modal" data-target="#modal-detail"></a>
							</div>
						</c:if>
					</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
</div>
<div id="quick-button-container">
	<c:if test="${canCreateTask}">
		<a class="quick-button" href="./addTask.htm"> <span
			class="quick-button-title">T</span><span class="quick-button-text">new
				task</span>
		</a>
	</c:if>
</div>