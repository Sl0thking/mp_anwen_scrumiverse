<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
$(document).ready(function(){
    $(".userstory").click(function(){
        deselectAll();
        $(this).addClass("selected");
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
            <div class="userstory">
                <div class="userstory-state inprogress"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">${userStory.description}</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">${userStory.dueDate}</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow">${userStory.moscow.toString()}</div>
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
            <div class="task">
                <div class="task-content">
                    <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                    <div class="task-time">### / ### / ###</div>
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
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
        </td>
        <td class="task-section item-section"></td>
        <td class="task-section item-section">
        <div class="task">
            <div class="task-content">
                <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                <div class="task-time">### / ### / ###</div>
                <div class="task-memberbox">
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                </div>
            </div>
            <a href="#" class="glyphicon glyphicon-triangle-right task-link done"></a>
        </div>
        </td>
    </tr>
	</c:forEach>
    <tr>
        <td class="userstory-section item-section">
            <div class="userstory">
                <div class="userstory-state inprogress"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">[US001] SUPER DUPER TOLLE USER STORY</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">TIME</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow">M</div>
                            <div class="value">#</div>
                            <div class="risk">###</div>
                            <div class="timestats">###/###/###</div>
                        </div>
                        <div class="effort">###</div>
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
            <div class="task">
                <div class="task-content">
                    <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                    <div class="task-time">### / ### / ###</div>
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
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
        </td>
        <td class="task-section item-section"></td>
        <td class="task-section item-section">
        <div class="task">
            <div class="task-content">
                <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                <div class="task-time">### / ### / ###</div>
                <div class="task-memberbox">
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                    <div class="task-member"></div>
                </div>
            </div>
            <a href="#" class="glyphicon glyphicon-triangle-right task-link done"></a>
        </div>
        </td>
    </tr>
<!-- DUMMY -->
    <tr>
        <td class="userstory-section item-section">
            <div class="userstory">
                <div class="userstory-state inprogress"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">[US001] SUPER DUPER TOLLE USER STORY</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">TIME</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow">M</div>
                            <div class="value">#</div>
                            <div class="risk">###</div>
                            <div class="timestats">###/###/###</div>
                        </div>
                        <div class="effort">###</div>
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
            <div class="task">
                <div class="task-content">
                    <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                    <div class="task-time">### / ### / ###</div>
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
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
        </td>
        <td class="task-section item-section">
            <div class="task">
                <div class="task-content">
                    <div class="task-name">TASK NAME SUPadadsaddaddasdER DUPER</div>
                    <div class="task-time">### / ### / ###</div>
                    <div class="task-memberbox">
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                    </div>
                </div>
                <a href="#" class="glyphicon glyphicon-triangle-right task-link inprogress"></a>
            </div>
        </td>
        <td class="task-section item-section">
        </td>
    </tr>
</table>
    
<div id="quick-button-container">
    <a class="quick-button" href="./addTask.htm">
        <span class="quick-button-title">T</span><span class="quick-button-text">new task</span>
    </a>
</div>