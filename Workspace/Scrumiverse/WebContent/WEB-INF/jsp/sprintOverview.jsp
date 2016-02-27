<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
$(document).ready(function(){
    $( ".sprint-dropdown" ).click(function() {
        if($(this).hasClass("glyphicon-triangle-bottom")){
            $( this ).removeClass("glyphicon-triangle-bottom").addClass("glyphicon-triangle-top");
            $( this ).parents(".sprint").addClass("sprint-openlog");
        } else {
            $( this ).removeClass("glyphicon-triangle-top").addClass("glyphicon-triangle-bottom");
            $( this ).parents(".sprint").removeClass("sprint-openlog");
        }
    });
});
</script>
<div class="sprintpage">
	<c:forEach items="${sprints}" var="sprint">
	    <div class="sprint">
	        <div class="sprint-state-active"></div>
	        <div class="sprint-content">
	            <div class="sprint-name">SPRINT - ${sprint.description }</div>
	            <div class="sprint-stats">
	                <div class="sprint-time-overview">
	                    <div class="sprint-sandclock"></div>
	                    <div class="sprint-date">${sprint.startDate.toString().substring(0,10)} -</br>${sprint.endDate.toString().substring(0,10)}</div>
	                    <div class="sprint-time">TIME</div>
	                </div>
	                <div class="sprint-data">
	                    <div class="sprint-userstory-container">
	                        USERSTORIES
	                        <div class="count">${sprint.getFinishedUserStories() } / ${sprint.getUserStories().size()} </div>
	                        <div class="sprint-userstory-progressbar"></div>
	                        <div class="sprint-userstory-progress"></div>
	                    </div>
	                    <div class="sprint-time-container">
	                        TIME
	                        <div class="count">${sprint.getRemainingMinutes() / 60 } / ${sprint.getPlannedMinutes() / 60}</div>
	                        <div class="sprint-time-progressbar"></div>
	                        <div class="sprint-time-progress"></div>
	                    </div>
	                    <div class="sprint-effort-container">
	                        EFFORT
	                        <div class="count">${sprint.getCompletedEffort()} / ${sprint.getCombinedEffort() }</div>
	                        <div class="sprint-effort-progressbar"></div>
	                        <div class="sprint-effort-progress"></div>
	                    </div>
	                    <div class="sprint-value-container">
	                        VALUE
	                        <div class="count">${sprint.getCompletedBusinessValue()} / ${sprint.getCombinedBusinessValue() }</div>
	                        <div class="sprint-value-progressbar"></div>
	                        <div class="sprint-value-progress"></div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="sprint-control">
	            <a class="glyphicon glyphicon-cog sprint-settings" href="#"></a>
	            <a class="glyphicon glyphicon-triangle-right sprint-link" href="#"></a>
	            <span class="glyphicon glyphicon-triangle-bottom sprint-dropdown"></span>
	        </div>
	        <div class="sprintlog">
		        <c:forEach items="${sprint.getUserStories()}" var="userstory">
		            <div class="userstory">
		                <div class="userstory-titel">${userstory.description }</div>
		                <div class="userstory-content">
		                    Time:   ${userstory.getWorkedMinutes()/60}/${userstory.getRemainingMinutes()/60}/${userstory.getPlannedMinutes()/60}</br>
		                    Effort: ${userstory.getEffortValue()}</br>
		                    Value:  ${userstory.getBusinessValue()}
		                </div>
		            </div>
		        </c:forEach>
		    </div>		    	
	    </div>
    </c:forEach>
    <div id="quick-button-container">
    <a class="quick-button" href="./addSprint.htm">
        <span class="quick-button-title">S</span><span class="quick-button-text">new Sprint</span>
    </a>
	</div>
</div>
