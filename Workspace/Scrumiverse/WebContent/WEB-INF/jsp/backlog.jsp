<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
$(document).ready(function(){
    $(".cardview").click(function(){
        if($(this).parent().next().attr("class")=="userstory"){
            var backlog = $(this).parent().parent();
            $(backlog).children().each(function(){
                if($(this).attr("class")=="userstory"){
                    $(this).removeClass("userstory").addClass("note");    
                }
            });
        }
    });
    $(".listview").click(function(){
        if($(this).parent().next().attr("class")=="note"){
            var backlog = $(this).parent().parent();
            $(backlog).children().each(function(){
                if($(this).attr("class")=="note"){
                    $(this).removeClass("note").addClass("userstory");    
                }
            });
        }
    });
});
</script>

	<div id="backlog">
	<div class="backlog-settings">
		<a href="#" class="listview glyphicon glyphicon-align-justify"></a>
		<a href="#" class="cardview glyphicon glyphicon-th"></a>
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
				        <div class="userstory-time">${userstory.getRemainingDays()}d</div>
			        </div>
			        <div class="info-container">
				        <div class="userstory-category">Category</div>
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
					                    <span>
									    <span class="glyphicon glyphicon-cog"></span>
									    Userstory Settings
					                    </span>
					                    <a href="./removeUserStory.htm" data-toggle="tooltip" title="Delete Userstory">
											<span class="glyphicon glyphicon-trash"></span>
										</a>
									</div>
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
						                	<span class="input-group-addon input-group-addon-fix">Category NOT DONE</span>
						                	<select class="form-control input-control">
											    <option>Killing Things</option>
											    <option>Trying not to be awesome</option>
											</select>
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
							            <div class="userstory-detail-task">
							                <div class="userstory-detail-task-content">
							                    <div class="userstory-detail-task-name">Killing Hibernate</div>
							                    <div class="userstory-detail-task-memberbox">
							                   		<img class="user-img" alt="err" src="./resources/userPictures/2.png">
							                    </div>
							                    <div class="userstory-detail-task-time">1h/2h/3h</div>
							                </div>
							            </div>
							            <div class="userstory-detail-task">
							                <div class="userstory-detail-task-content">
							                    <div class="userstory-detail-task-name">Create new Hibernate with Sloths</div>
							                    <div class="userstory-detail-task-memberbox">
							                   		<img class="user-img alt="err" src="./resources/userPictures/2.png">
							                   		<img class="user-img alt="err" src="./resources/userPictures/1.png">
							                   		<img class="user-img alt="err" src="./resources/userPictures/default.png">
							                    </div>
							                    <div class="userstory-detail-task-time">0h/8h/8h</div>
							                </div>
							            </div>					            
					                </div>
					                <div class="modal-footer footer-fix">
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
    <a class="quick-button" href="./newUserStory.htm">
        <span class="quick-button-title">U</span><span class="quick-button-text">new UserStory</span>
    </a> 
</div>