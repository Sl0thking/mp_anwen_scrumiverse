<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
$(document).ready(function(){
    $(".userstory").click(function(){
        deselectAll();
        $(this).addClass("selected");
        var userStoryId = $(this).attr("userStoryId");
        $(".quick-button").attr("href", "addTask.htm?id="+userStoryId);
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
                <div class="userstory-state inprogress"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">${userStory.description}</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">${userStory.dueDate}</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow">${userStory.moscow.toString().substring(0,1)}</div>
                            <div class="value">${userStory.businessValue}</div>
                            <div class="risk">###</div>
                            <div class="timestats">
                            	${userStory.getWorkedMinutes()} / ${userStory.getRemainingMinutes()} / ${userStory.getPlannedMinutes()}</div>
                        	</div>
                        <div class="effort">${userStory.effortValue}</div>
                        <div class="member-container">
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                        </div>
                    </div>
                </div>
                <a class="glyphicon glyphicon-triangle-right userstory-link inprogress" href="#"></a>
            </div>
        </td>
        <td class="task-section item-section">
        	<c:forEach items="${tasksOfUserStories[userStory]}" var="task">
	            <c:if test="${task.planState.toString() eq 'Planning'}">
		            <div class="task">
		                <div class="task-content">
		                    <div class="task-name">${task.description}</div>
		                    <div class="task-time">${task.getWorkMin()} / ${task.getRemainingMin()} / ${task.getPlannedMin()}</div>
		                    <div class="task-memberbox">
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link planed"></a>
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
		                    <div class="task-time">${task.getWorkMin()} / ${task.getRemainingMin()} / ${task.getPlannedMin()}</div>
		                    <div class="task-memberbox">
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link planed"></a>
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
		                    <div class="task-time">${task.getWorkMin()} / ${task.getRemainingMin()} / ${task.getPlannedMin()}</div>
		                    <div class="task-memberbox">
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                        <div class="task-member"></div>
		                    </div>
		                </div>
		                <a href="#" class="glyphicon glyphicon-triangle-right task-link planed"></a>
		            </div>
		    	</c:if>
        	</c:forEach>
        </td>
    </tr>
	</c:forEach>
</table>
    
<div id="quick-button-container">
    <a class="quick-button" href="./addTask.htm">
        <span class="quick-button-title">T</span><span class="quick-button-text">new task</span>
    </a>
</div>