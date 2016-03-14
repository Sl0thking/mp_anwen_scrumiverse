<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
$(document).ready(function(){
	$(".quick-button").hide();
	
    $(".userstory").click(function(){
    	$(".quick-button").show();
        deselectAll();
        $(this).addClass("selected");
        var userStoryId = $(this).attr("userStoryId");
        $(".quick-button").attr("href", "addTask.htm?id="+userStoryId);
    });
    
    $("#taskmodal .btn-group.btn-group-planstate button").click(function(){
    	$("#taskmodal .btn-group.btn-group-planstate button").removeClass("active");
    	$(this).addClass("active");
    });
});
    
function deselectAll(){
    $(".userstory").each(function(){
        $(".selected").removeClass("selected");
    });
}
</script>
<div>
    <table class="taskboard-heading">
        <tr>
            <th class="userstory-section">USERSTORY</th>
            <th class="task-section">PLANED</th>
            <th class="task-section">TO-DO</th>
            <th class="task-section">DONE</th>
        </tr>
    </table>
</div>
<table class="taskboard">
	<c:forEach items="${userStories}" var="userStory">
		<tr>
        <td class="userstory-section item-section">
            <div class="userstory" userStoryId="${userStory.id}">
                <div id="${userStory.planState.toString() }" class="userstory-state"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">${userStory.description}</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">${userStory.dueDate}</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow" data-toggle="tooltip" title="MoSCoW">${userStory.moscow.toString().substring(0,1)}</div>
                            <div class="value" data-toggle="tooltip" title="Value">${userStory.businessValue}</div>
                            <div class="risk" data-toggle="tooltip" title="Risk">${userStory.risk}</div>
                            <div class="effort" data-toggle="tooltip" title="Effort">${userStory.effortValue}</div>
                            <div class="timestats">
                            	<fmt:formatNumber value="${userStory.getWorkedMinutes()/60}" maxFractionDigits="1"/> / 
                            	<fmt:formatNumber value="${userStory.getRemainingMinutes()/60}" maxFractionDigits="1"/> / 
                            	<fmt:formatNumber value="${userStory.getPlannedMinutes()/60}" maxFractionDigits="1"/> h</div>
                        	</div>
                        <div class="member-container">
                       		<c:choose>		
			                   	<c:when test="${userStory.getResponsibleUsers().size() <= 6}">
									<c:forEach items="${userStory.getResponsibleUsers()}" var="user">
										<div class="userstory-member"><img src="${user.profileImagePath}"/></div>
									</c:forEach>
			                   	</c:when>
			                   	<c:otherwise>
			                   		<c:forEach begin="0" end="5">
			                   			<div class="userstory-member"><img src="${user.profileImagePath}"/></div>
			                   		</c:forEach>
			                   		<div class="userstory-member">...</div>
			                   	</c:otherwise>
		                   	</c:choose>
                        </div>
                    </div>
                </div>
                <a id="${userStory.planState.toString() }" class="glyphicon glyphicon-triangle-right userstory-link" href="#"></a>
            </div>
        </td>
        <td class="task-section item-section">
        	<c:forEach items="${tasksOfUserStories[userStory]}" var="task">
	            <c:if test="${task.planState.toString() eq 'Planning'}">
		            <div class="task">
		                <div class="task-content">
		                    <div class="task-name">${task.description}</div>
		                    <div class="task-time">
		                    	<fmt:formatNumber value="${task.getWorkMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getRemainingMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getPlannedMin()/60}" maxFractionDigits="1" /> h</div>
		                    <div class="task-memberbox">
		                   		<c:choose>		
			                   		<c:when test="${task.getResponsibleUsers().size() <= 6}">
										<c:forEach items="${task.getResponsibleUsers()}" var="user">
											<div class="task-member"><img src="${user.profileImagePath}"/></div>
										</c:forEach>
			                   		</c:when>
			                   		<c:otherwise>
			                   			<c:forEach begin="0" end="5">
			                   				<div class="task-member"><img src="${user.profileImagePath}"/></div>
			                   			</c:forEach>
			                   			<div class="task-member">...</div>
			                   		</c:otherwise>
		                   		</c:choose>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link ${task.planState.toString()}" data-toggle="modal" data-target="#taskmodal"></a>
		            </div>
		    	</c:if>
        	</c:forEach>
        </td>
        <td class="task-section item-section">
        <c:forEach items="${tasksOfUserStories[userStory]}" var="task">
	            <c:if test="${task.planState.toString() eq 'InProgress'}">
		            <div class="task">
		                <div class="task-content">
		                    <div class="task-name">${task.description}</div>
		                    <div class="task-time">
		                    	<fmt:formatNumber value="${task.getWorkMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getRemainingMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getPlannedMin()/60}" maxFractionDigits="1" /> h</div>
		                    <div class="task-memberbox">
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link ${task.planState.toString()}" data-toggle="modal" data-target="#taskmodal"></a>
		            </div>
		    	</c:if>
        	</c:forEach>
        </td>
        <td class="task-section item-section">
        <c:forEach items="${tasksOfUserStories[userStory]}" var="task">
	            <c:if test="${task.planState.toString() eq 'Done'}">
		            <div class="task">
		                <div class="task-content">
		                    <div class="task-name">${task.description}</div>
		                     <div class="task-time">
		                    	<fmt:formatNumber value="${task.getWorkMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getRemainingMin()/60}" maxFractionDigits="1" /> / 
		                    	<fmt:formatNumber value="${task.getPlannedMin()/60}" maxFractionDigits="1" /> h</div>
		                    <div class="task-memberbox">
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link ${task.planState.toString()}" data-toggle="modal" data-target="#taskmodal"></a>
		            </div>
		    	</c:if>
        	</c:forEach>
        </td>
    </tr>
	</c:forEach>
</table>

<!-- Boostrap Modal for Task details -->
<div id="taskmodal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="info-bar">
					<span class="glyphicon glyphicon-cog"></span>
					TASK DETAIL #12345
					<a href="#" data-toggle="tooltip" title="Delete task">
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
						<div class="btn-group btn-group-sm btn-group-planstate" role="group">
							<button type="button" class="btn btn-secondary active">Planning</button>
							<button type="button" class="btn btn-secondary">In progress</button>
							<button type="button" class="btn btn-secondary">Done</button>
						</div>
						<div class="user-list">
							<a href="#" data-toggle="tooltip" title="Add user">
								<span class="glyphicon glyphicon-plus-sign"></span>
							</a>
							<!-- forEach user in userList -->
							<img class="img-circle" alt="username" src="./resources/userPictures/1.png" data-toggle="tooltip" title="Kevin Wesseler"/>
							<img class="img-circle" alt="username" src="./resources/userPictures/2.PNG" data-toggle="tooltip" title="Kevin Jolitz"/>
							<img class="img-circle" alt="username" src="./resources/userPictures/default.png" data-toggle="tooltip" title="Toni Serfling"/>
							<img class="img-circle" alt="username" src="./resources/userPictures/2.PNG" data-toggle="tooltip" title="Lasse Jacobs"/>
							<!-- end forEach -->
						</div>
       					<div class="input-group">
							<span class="input-group-addon">Description</span>
							<textarea class="form-control" style="resize:vertical">Task description (pls change me)</textarea>
						</div>
       					<div class="input-group">
							<span class="input-group-addon">Criteria</span>
							<textarea class="form-control" style="resize:vertical">Acceptance criteria (pls change me)</textarea>
						</div>
						<div class="tag-list">
							<!-- forEach tag in taglist -->
							<div class="tag">
								60 FPS
								<a href="#">
									<span class="glyphicon glyphicon-remove"></span>
								</a>
							</div>
							<div class="tag">
								FIX MAX HP
								<a href="#">
									<span class="glyphicon glyphicon-remove"></span>
								</a>
							</div>
							<!-- end forEach -->
							<input type="text" placeholder="Add tag"></input>
						</div>
						<table class="time-table">
							<tr>
								<th>Users</th>
								<th>Estimated</th>
								<th>Spent</th>
								<th>Remaining</th>
							</tr>
							<tr>
								<td>
									<img class="img-circle" alt="username" src="./resources/userPictures/2.PNG" data-toggle="tooltip" title="Lasse Jacobs"/>
									Lasse Jacobs
								</td>
								<td>10h</td>
								<td>4h</td>
								<td>6h</td>
							</tr>
							<tr>
								<td>
									<img class="img-circle" alt="username" src="./resources/userPictures/default.png" data-toggle="tooltip" title="Toni Serfling"/>
									Toni Serfling
								</td>
								<td>4h 30min</td>
								<td>3h 30min</td>
								<td>1h</td>
							</tr>
							<tr>
								<td>
									<img class="img-circle" alt="username" src="./resources/userPictures/2.PNG" data-toggle="tooltip" title="Kevin Jolitz"/>
									Kevin Joltiz
								</td>
								<td>6h</td>
								<td>5h</td>
								<td>1h</td>
							</tr>
							<tr>
								<td>
									<img class="img-circle" alt="username" src="./resources/userPictures/1.png" data-toggle="tooltip" title="Kevin Wesseler"/>
									Kevin Wesseler
								</td>
								<td>4h 30min</td>
								<td>5h</td>
								<td>-30min</td>
							</tr>
							<tr>
								<td>Total</td>
								<td>25h</td>
								<td>17h 30min</td>
								<td>7h 30min</td>
							</tr>
						</table>
       				</div>
       				<div id="section-history" class="tab-pane fade in">
       					Preview for Sprint 3
       				</div>
       			</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">
					<span class="glyphicon glyphicon-save"></span>
					Save
				</button>
			</div>
		</div>
	</div>
</div>
    
<div id="quick-button-container">
    <a class="quick-button" href="./addTask.htm">
        <span class="quick-button-title">T</span><span class="quick-button-text">new task</span>
    </a>
</div>