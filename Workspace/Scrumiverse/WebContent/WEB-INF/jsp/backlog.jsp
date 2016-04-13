<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="resources/javascript/dialog.js"></script>
<script>
$(document).ready(function(){
	setView("userstory",getView("usview"));
    $(".cardview").click(function(){
        setView(getView(),"note");
    });
    $(".listview").click(function(){
    	setView(getView(),"userstory");
    });
});
function getView(){
	console.log(document.cookie)
	if(document.cookie==""){
		document.cookie="usview=userstory"
		return "userstory";
	} else {
		return document.cookie.split("=")[1];
	}
}

/* set the active view and set the hast of the userstory-button-url for the backend*/
function setView(oldView, newView){
	$(document).find("."+oldView).each(function(){
	        $(this).removeClass(oldView).addClass(newView);
	});
	document.cookie="usview="+newView;
}
</script>
<div id="user-dialog">
    <div class="dialog-header"><span class="glyphicon glyphicon-alert"></span> <span id="dialog-title"></span></div>
    <div class="dialog-body">
        <div class="dialog-text"></div>
        <a id="dialog-hide" class="btn btn-danger">No</a>
        <a href="#" id="dialog-delete" class="btn btn-success">Yes</a>
    </div>
</div>
<div id="backlog">
	<div class="backlog-settings">
		<a href="#us" class="listview glyphicon glyphicon-align-justify"></a>
		<a href="#note" class="cardview glyphicon glyphicon-th"></a>
	</div>
	<c:forEach items="${userstories}" var = "userstory">
		<div class="userstory">
			<div class="userstory-planstate" id="${userstory.planState}"></div>
	        <div class="userstory-container">
		        <div class="userstory-titel">${userstory.description}</div>
		        <div class="userstory-memberbox">
		        	<c:forEach items="${userstory.getResponsibleUsers()}" var="user">
		        		<img class="user-img" alt="err" src="${user.getProfileImagePath()}" data-toggle="tooltip" title="${user.name }"></img>
		        	</c:forEach>
		        </div>
		        <div class="timebox">
		        	<c:if test="${userstory.planState!='Done'}">
		        		<c:choose>
		        			<c:when test="${userstory.getRemainingDays() > 3}"><img src="./resources/images/sandclock/SandClock_4.png" alt="" class="userstory-sandclock"></img></c:when>
		        			<c:when test="${userstory.getRemainingDays() > 1}"><img src="./resources/images/sandclock/SandClock_3.png" alt="" class="userstory-sandclock"></img></c:when>
		        			<c:when test="${userstory.getRemainingDays() == 1}"><img src="./resources/images/sandclock/SandClock_1.png" alt="" class="userstory-sandclock"></img></c:when>
		        			<c:otherwise><img src="./resources/images/sandclock/SandClock_0.png" alt="" class="userstory-sandclock"></img></c:otherwise>
		        		</c:choose>
			        	<div class="userstory-time">${userstory.getRemainingDays()} d</div>
			        </c:if>
		        </div>
				<div class="info-container">
					<div class="userstory-category">${userstory.category.name}</div>
					<div class="userstory-sprint">
						<c:choose>
							<c:when test="${userstory.relatedSprint == null}">Backlog</c:when>
							<c:otherwise>${userstory.relatedSprint.description}</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="userstory-stats">
					<div class="userstory-timestats">
						<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}"
							maxFractionDigits="1" />
						/
						<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}"
							maxFractionDigits="1" />
						/
						<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}"
							maxFractionDigits="1" />
						h
					</div>
					<div class="userstory-moscow" data-toggle="tooltip" title="MoSCoW">${userstory.getMoscow().toString()}</div>
					<div class="userstory-value" data-toggle="tooltip" title="Value">${userstory.businessValue}</div>
					<div class="userstory-risk" data-toggle="tooltip" title="Risk">${userstory.risk}</div>
					<div class="userstory-effort" data-toggle="tooltip" title="Effort">${userstory.effortValue}</div>
				</div>
			</div>
			<div class="userstory-control" id="${userstory.planState}">
				<a class="glyphicon glyphicon-triangle-right userstory-settings" href="#" data-toggle="modal" 
					data-target=".modal-detail[userstoryid='${userstory.id}']"></a>
			</div>
		</div>
		<div class="modal-detail modal fade" userstoryid="${userstory.id}"
			role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<div class="info-bar">
							<span class="glyphicon glyphicon-cog"></span> 
							USERSTORY DETAIL 
							<c:if test="${canDeleteUserStory}">
								<a class="dialog_action" link="./removeUserStory.htm?id=${userstory.id}" 
									data-toggle="tooltip" title="Delete Userstory" msg="Do you really want to delete this userstory?">
									<span class="glyphicon glyphicon-trash"></span>
								</a>
							</c:if>
						</div>
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab"
								href=".modal-detail[userstoryid='${userstory.id}'] .section-detail">
									<span class="glyphicon glyphicon-info-sign"></span> Detail
							</a></li>
							<li><a data-toggle="tab"
								href=".modal-detail[userstoryid='${userstory.id}'] .section-history">
									<span class="glyphicon glyphicon-list-alt"></span> History
							</a></li>
						</ul>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="section-detail tab-pane fade in active">
								<div class="member-list">
									<c:forEach items="${userstory.responsibleUsers}" var="user">
										<img class="member-img" alt="${user.getName()}"
											src="${user.profileImagePath}" data-toggle="tooltip"
											title="${user.getName()}" />
									</c:forEach>
								</div>
								<c:set var="selectedUserstory" value="${userstory}" scope="request" />
								<form:form action="changeUserStory.htm" modelAttribute="selectedUserstory">
									<div class="input-group">
										<span class="input-group-addon">Description</span>
										<form:textarea disabled="${!canUpdateUserstory }" class="form-control" 
											style="resize:vertical" path="description" />
									</div>
									<div class="input-group">
										<span class="input-group-addon">Criteria</span>
										<form:textarea disabled="${!canUpdateUserstory }" class="form-control input-control"
											style="resize:vertical" path="acceptanceCriteria" />
									</div>
									<div class="input-group input-container-1-of-2">
										<span class="input-group-addon">Planstate</span>
										<form:select disabled="${!canUpdateUserstory }" class="form-control input-control" 
											path="planState">
											<form:options items="${planstates}" />
										</form:select>
									</div>
									<div class="input-group input-container-2-of-2">
										<span class="input-group-addon">Due Date</span>
										<form:input disabled="${!canUpdateUserstory }" class="form-control input-control" 
											path="dueDate" type="date" value="${userstory.getFormattedDueDate()}" />
									</div>
									<div class="input-group input-container-1-of-2">
										<span class="input-group-addon">Sprint</span>									
										<form:select disabled="${!canUpdateSprint }" class="form-control input-control" path="relatedSprint">
											<form:option label="backlog" value="0" />
											<form:options itemLabel="description" itemValue="id" items="${currentProject.getSprints()}" />
										</form:select>
									</div>
									<div class="input-group input-container-2-of-2">
										<span class="input-group-addon">Category</span>
										<form:select disabled="${!canUpdateUserstory }" class="form-control input-control"
											path="category">
											<form:option label="" value="0" />
											<form:options itemLabel="name" itemValue="id"
												items="${currentProject.getCategories()}" />
										</form:select>
									</div>
									<div class="input-group input-container-1-of-2">
										<span class="input-group-addon">Effort</span>
										<form:input disabled="${!canUpdateUserstory }" class="form-control input-control"
											path="effortValue" type="number" />
									</div>
									<div class="input-group input-container-2-of-2">
										<span class="input-group-addon">Businessvalue</span>
										<form:input disabled="${!canUpdateUserstory }" class="form-control input-control"
											path="businessValue" type="number" />
									</div>
									<div class="input-group input-container-1-of-2">
										<span class="input-group-addon">Moscow</span>
										<form:select disabled="${!canUpdateUserstory }" class="form-control input-control" path="moscow">
											<form:options items="${moscows}" />
										</form:select>
									</div>
									<div class="input-group input-container-2-of-2">
										<span class="input-group-addon">Risk</span>
										<form:input disabled="${!canUpdateUserstory }" class="form-control input-control" path="risk"
											type="number" />
									</div>
									<button <c:if test="${!canUpdateUserstory }">disabled</c:if> type="submit" class="btn btn-submit btn-default">
										<span class="glyphicon glyphicon-save"></span> Save
									</button>
									<form:hidden path="id"/>
								</form:form>
								<div class="info-bar">Tasks</div>
								<table class="detail-table">
									<tr>
										<th>Description</th>
										<th>Users</th>
										<th>Planned</th>
										<th>Worked</th>
										<th>Remaining</th>
										<th></th>
									</tr>
									<c:forEach items="${userstory.getTasks()}" var="task">
										<tr>
											<td>${task.description}</td>
											<td>
												<c:forEach items="${task.getResponsibleUsers()}" var="tuser">
													<img class="user-img" alt="err"
														src="${tuser.getProfileImagePath()}">
												</c:forEach>
											</td>
											<td><fmt:formatNumber value="${task.getPlannedMin()/60}" maxFractionDigits="1" />h</td>
											<td><fmt:formatNumber value="${task.getWorkMin()/60}" maxFractionDigits="1" />h</td>
											<td><fmt:formatNumber value="${task.getRemainingMin()/60}" maxFractionDigits="1" />h</td>
											<td>
												<c:if test="${canDeleteTask}">
													<a class="dialog_acition" link="./deleteTask.htm?taskID=${task.id}"
														data-toggle="tooltip" title="Delete Task" msg="Do you really want to delete this task?">
														<span class="glyphicon glyphicon-remove"></span>
													</a>
												</c:if>										
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>Total</td>
										<td>
											<c:forEach items="${userstory.getResponsibleUsers()}" var="ususer">
												<img class="user-img" alt="err"
													src="${ususer.getProfileImagePath()}">
											</c:forEach>
										</td>
										<td><fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="1" />h</td>
										<td><fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="1" />h</td>
										<td><fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="1" />h</td>
										<td></td>
									</tr>
								</table>
							</div>
							<div class="modal-body history-tab section-history tab-pane fade in">
								<c:forEach items="${userstory.getHistory()}" var="history">
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
	</c:forEach>
</div>
<div id="quick-button-container">
	<c:if test="${canCreateUserStory}">
		<a class="quick-button" href="./newUserStory.htm"> <span
			class="quick-button-title">U</span><span class="quick-button-text">new
				UserStory</span>
		</a>
	</c:if>
</div>
