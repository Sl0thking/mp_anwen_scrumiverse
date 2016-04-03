<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>

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

$(document).ready(function(){
	setView("userstory",getView("usview"));
    $(".cardview").click(function(){
        setView(getView(),"note");
    });
    $(".listview").click(function(){
    	setView(getView(),"userstory");
    });
});
</script>

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
			        		<img class="user-img" alt="err" src="${user.getProfileImagePath()}">
			        	</c:forEach>
			        </div>
			        <div class="timebox">
				        <div class="userstory-sandclock"></div>
				        <div class="userstory-time">${userstory.getRemainingDays()} d</div>
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
				        	<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="1"/> / 
		                	<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="1"/> / 
		                    <fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="1"/> h
		                </div>
				        <div class="userstory-moscow" data-toggle="tooltip" title="MoSCoW">${userstory.getMoscow().toString()}</div>
				        <div class="userstory-value" data-toggle="tooltip" title="Value">${userstory.businessValue}</div>
				        <div class="userstory-risk" data-toggle="tooltip" title="Risk">${userstory.risk}</div>
				        <div class="userstory-effort" data-toggle="tooltip" title="Effort">${userstory.effortValue}</div>
        			</div>
        		</div>
        		<div class="userstory-control" id="${userstory.planState}">
					<a class="glyphicon glyphicon-triangle-right userstory-settings" href="#" data-toggle="modal" data-target="#userstory-detail-${userstory.id}"></a>
				</div>
			</div>
			<c:set var="userstory" scope="request" value="${userstory}"/>
			<form:form fid="${userstory.id}" action="changeUserStory.htm" modelAttribute="userstory">
				    <div id="userstory-detail-${userstory.id}" class="modal fade userstory-detail" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
					    <div class="modal-dialog">
					        <div class="modal-content userstory-detail-settings">
					        	<div class="modal-header">
						        	<div class="info-bar">
										<span class="glyphicon glyphicon-cog"></span>
										Userstory
										<a href="./removeUserStory" data-toggle="tooltip" title="Delete userstory">
											<span class="glyphicon glyphicon-trash"></span>
										</a>				
									</div>
									<ul class="nav nav-tabs">
										<li class="active">
											<a data-toggle="tab" href="#section-detail">
												<span class="glyphicon glyphicon-info-sign"></span>
												Detail
											</a>
										</li>
										<li>
											<a data-toggle="tab" href="#section-history">
												<span class="glyphicon glyphicon-list-alt"></span>
												History
											</a>
										</li>
									</ul> 
								</div>
								<div class="modal-body">
									<div class="tab-content">
										<div id="section-detail" class="tab-pane fade in active">
											<div class="input-group">
							                    <span class="input-group-addon input-group-addon-fix">Description</span>
							                    <form:input path="description" class="form-control input-control" />
							                </div>
							                <div class="input-group">
							                    <span class="input-group-addon input-group-addon-fix">Criteria</span>
							                    <form:input path="acceptanceCriteria" class="form-control input-control"/>
							                </div>
							                <div class="input-group">
							                	<span class="input-group-addon input-group-addon-fix">Sprint</span>
							                	<form:select fid="${userstory.id}" path="relatedSprint" class="form-control input-control">
							                		<form:option label="backlog" value="0"/>
													<form:options itemLabel="description" itemValue="id" items="${currentProject.getSprints()}"/>
												</form:select>
							                </div>
							                <div class="input-container">
							                	<div class="input-group input-container-left">
								                	<span class="input-group-addon input-group-addon-fix">Planstate</span>
								                	<form:select fid="${userstory.id}" path="planState" class="form-control input-control">
													    <form:options items="${planstates}"/>
													</form:select>
								                </div>
								                <div class="input-group input-container-right">
								                	<span class="input-group-addon input-group-addon-fix">Category</span>
								                	<form:select fid="${userstory.id}" path="category" class="form-control input-control">
								                		<form:option label="" value="0"/>
														<form:options itemLabel="name" itemValue="id" items="${currentProject.getCategories()}"/>
													</form:select>
								                </div>
								             </div>
							                <div class="input-container">
							                	<div class="input-group input-container-left">
							                	    <span class="input-group-addon input-group-addon-fix">Effort</span>
							                    	<form:input path="effortValue" type="number" class="form-control input-control"/>
							                	</div>
								                <div class="input-group input-container-right">
								                    <span class="input-group-addon input-group-addon-fix">Businessvalue</span>
								                    <form:input path="businessValue" type="number" class="form-control input-control"/>
								                </div>
							                </div>
							                <div class="input-container">
								                <div class="input-group input-container-left">
								                	<span class="input-group-addon input-group-addon-fix">Moscow</span>
								                	<form:select fid="${userstory.id}" path="moscow" class="form-control input-control">
													    <form:options items="${moscows}"/>
													</form:select>
								                </div>
								                <div class="input-group input-container-right">
								                    <span class="input-group-addon input-group-addon-fix">Risk</span>
								                    <form:input path="risk" type="number" class="form-control input-control"/>
								                </div>
							                </div>
						                    <div class="input-group">
						                        <span class="input-group-addon input-group-addon-fix">Due Date</span>
						                        <form:input path="dueDate" type="date" class="form-control input-control" value="${userstory.getFormattedDueDate()}"/>
						                    </div>
							                <div class="userstory-detail-data">
							                    <div class="modal-data-container">
							                    	Users
							                    	<div class="userstory-memberbox">
											        	<c:forEach items="${userstory.getResponsibleUsers()}" var="user">
											        		<img class="user-img" alt="err" src="${user.getProfileImagePath()}">
											        	</c:forEach>
											        </div>
							                    </div>
							                    <div class="modal-data-container">
							                        <div class="userstory-timestats">
											        	<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="1"/> / 
									                	<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="1"/> / 
									                    <fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="1"/> h
									                </div>
							                        <div class="progressbar">
							                            <div class="progress"></div>
							                        </div>
							                    </div>
							                </div>
							                <div class="userstory-detail-task-container">
							                	<c:forEach items="${userstory.getTasks()}" var="task">
							                		<div class="userstory-detail-task">
									                    <div class="userstory-detail-task-name">${task.description}</div>
									                    <div class="userstory-detail-task-memberbox">
									                    	<c:forEach items="${task.getResponsibleUsers()}" var="tuser">
									                    		<img class="user-img" alt="err" src="${tuser.getProfileImagePath()}">
									                    	</c:forEach>
									                    </div>
									                    <div class="userstory-detail-task-time">
						        							<fmt:formatNumber value="${task.getWorkMin()/60}" maxFractionDigits="1"/> / 
									                		<fmt:formatNumber value="${task.getRemainingMin()/60}" maxFractionDigits="1"/> / 
									                    	<fmt:formatNumber value="${task.getPlannedMin()/60}" maxFractionDigits="1"/> h
									                    </div>	
									            	</div>
							                	</c:forEach>		            
							                </div>

					                	</div>
					                	<div id="section-history" class="tab-pane fade in">
					                	</div>
					        		</div>
					        	</div>
					        	<div class="modal-footer">
				                    <button class="btn btn-default" type="submit">
				                        <span class="glyphicon glyphicon-save"></span>
				                        Save
				                    </button>
				                </div>
					        </div>
					    </div>
						<form:hidden path="id"/>
					</div>
			</form:form>
		</c:forEach>
	</div>  
	<div id="quick-button-container">
		<c:if test="${createUserStory}">
	    	<a class="quick-button" href="./newUserStory.htm">
	        	<span class="quick-button-title">U</span><span class="quick-button-text">new UserStory</span>
	    	</a>
		</c:if>
	</div>	
