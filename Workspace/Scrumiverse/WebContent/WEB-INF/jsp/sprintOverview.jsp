<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
$(document).ready(function(){
    $(".barbtn").click(function(){
        openBacklog();
    });
    
    $("#openBacklog").click(function(){
        openBacklog();
    });
});

function openBacklog(){
    if($(".backlog-placeholder").css("left")=="0px"){
        $(".backlog-placeholder").animate({left:"-287px"},500);
        $(".glyphicon-menu-right").css({transform:"rotate(0deg)"});
    } else {
        $(".backlog-placeholder").animate({left:"0px"},500);
        $(".glyphicon-menu-right").css({transform:"rotate(180deg)"});
    }
}
    
$(document).ready(function(){
    $(".addbtn").hide();
    $("#removebtn").hide();
    selectUserstory();
    toggleSprintlog();
    moveUserstories();
    if($(this).parent().attr("class")=="content"){
        checkSelected(".content");
    }
});

function moveUserstories(){
    $(".addbtn").click(function(){
        var sprint = $(".openlog").attr("sprintid");
        var backlogusids = [];
        var sprintusids = [];
        getSelected(".content").each(function(){
            backlogusids.push($(this).attr("usid"));
        });
        getSelected(".openlog").each(function(){
            backlogusids.push($(this).attr("usid"));
        });
        var urlchange = "?sprintid="+sprint+"&addedStories="+backlogusids+"&removedStories="+sprintusids;
        $(".addbtn").attr("href", $(".addbtn").attr("href")+urlchange);
        $("#removebtn").attr("href", $(".addbtn").attr("href")+urlchange);
        //$.post("addToSprint.htm",change,deselectAll());
    });
}
    
//Closes all Sprintlogs and deselect all Userstories.
//Set the needed classes to the Sprintlogs.
function toggleSprintlog(){
    $( ".sprint-dropdown" ).click(function() {
        if($(this).hasClass("glyphicon-triangle-bottom")){
            closeAll();
            $( this ).removeClass("glyphicon-triangle-bottom").addClass("glyphicon-triangle-top");
            $( this ).parents(".sprint").addClass("openlog");
            deselectAll();
        } else {
            closeAll();
            $( this ).removeClass("glyphicon-triangle-top").addClass("glyphicon-triangle-bottom");
            $( this ).parents(".sprint").removeClass("openlog");
            deselectAll();
        }
    });
}

//Selects/Deselects the Userstory and handle the Addbtn
function selectUserstory(){
    $(".userstory").click(function(){
        if($(this).hasClass("selected")){
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
function getSelected(classselctor){
    return $(classselctor).find(".userstory.selected");
}

// Checks the given parent, if one child has the class ".userstory.selected" it will retuen true.
function checkSelected(parentClass){
    if($(parentClass).find(".userstory.selected").size()>0){
        return true;
    } else {
        return false;
    }
}
    
// Deselect all Userstorys
function deselectAll(){
    $(".userstory").each(function(){
        $(".selected").removeClass("selected");
    });
}

// Close all Sprintlogs
function closeAll(){
    $(".sprint").each(function(){
        $(this).removeClass("openlog");
        $(".glyphicon-triangle-top").removeClass("glyphicon-triangle-top").addClass("glyphicon-triangle-bottom");
    });
}

//Toggles the Addbtn
//Check the selected Userstories and one Sprintlog must be open.
function toggleAddbtn(){
    if(checkSelected(".content") && $(".sprint").hasClass("openlog")){
        $(".addbtn").show();
    } else {
        $(".addbtn").hide();
    }
}
    
//Toggles the Removebtn
function toggleRemovebtn(){
    if(checkSelected(".sprintlog")){
        $("#removebtn").show();
    } else {
        $("#removebtn").hide();
    }
}
</script>
<div class="backlog-placeholder">
    <div class="backlogbar">
        <span class="glyphicon glyphicon-menu-right barbtn"></span>
    </div>
    <div class="backlog">
        <div class="backlog-header">Backlog</div>
        <div class="backlog-data">
            <div class="data-container">
                Value
                ### / ###
                <div class="progressbar">
                    <div class="progress"></div>
                </div>
            </div>
            <div class="data-container">
                Value</br>
                ### / ###
                <div class="progressbar">
                    <div class="progress"></div>
                </div>
            </div>
            <div class="data-container">
                Value</br>
                ### / ###
                <div class="progressbar">
                    <div class="progress"></div>
                </div>
            </div>
        </div>
        <div class="content">
        	<c:forEach items="${project.getUserstories()}" var="userstory">
	            <div usid="${userstory.id }" class="userstory">
			        <div class="userstory-titel">${userstory.description }</div>
			        <div class="userstory-content">
			        Time:   <fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="0"/>h /
			                <fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="0"/>h /
			        		<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="0"/>h</br>
			        Effort: ${userstory.getEffortValue()}</br>
			        Value:  ${userstory.getBusinessValue()}
			    	</div>
				</div>
			</c:forEach>
        </div>
        <a class="addbtn" href="./editUserstories.htm">Add to Sprint</a>
    </div>
</div>

<div class="sprintpage">
	<c:forEach items="${sprints}" var="sprint">
	    <div sprintid="${sprint.id}" class="sprint">
	        <div class="sprint-state ${sprint.planState.name().toLowerCase() }"></div>
	        <div class="sprint-content">
	            <div class="sprint-name">${sprint.description }</div>
	            <div class="sprint-stats">
	                <div class="sprint-time-overview">
	                    <div class="sprint-sandclock"></div>
	                    <div class="sprint-date">${sprint.startDate.toString().substring(0,10)} -</br>${sprint.endDate.toString().substring(0,10)}</div>
	                    <div class="sprint-time">Time</div>
	                </div>
	                <div class="sprint-data">
	                    <div class="data-container">
	                        Userstories
	                        <div class="count">${sprint.getFinishedUserStories() } / ${sprint.getUserStories().size()} </div>
	                        <div class="progressbar">
	                        	<div class="progress" style="width:${sprint.getFinishedUserStories() / sprint.userStories.size() * 100}%"></div>
	                        </div>
	                    </div>
	                    <div class="data-container">
	                        Time
	                        <div class="count">
	                        	<fmt:formatNumber value="${sprint.getRemainingMinutes() / 60 }" maxFractionDigits="0" />h / 
	                        	<fmt:formatNumber value="${sprint.getPlannedMinutes() / 60}" maxFractionDigits="0" />h</div>
	                        <div class="progressbar">
	                        	<div class="progress" style="width:${sprint.getRemainingMinutes() / sprint.getPlannedMinutes() * 100}%"></div>
	                        </div>
	                    </div>
	                    <div class="data-container">
	                        Effort
	                        <div class="count">${sprint.getCompletedEffort()} / ${sprint.getCombinedEffort() }</div>
	                        <div class="progressbar">
	                        	<div class="progress" style="width:${sprint.getCompletedEffort() / sprint.getCombinedEffort() * 100}%"></div>
	                        </div>
	                    </div>
	                    <div class="data-container">
	                        Value
	                        <div class="count">${sprint.getCompletedBusinessValue()} / ${sprint.getCombinedBusinessValue() }</div>
	                        <div class="progressbar">
	                        	<div class="progress" width="${sprint.getCompletedBusinessValue() / sprint.getCombinedBusinessValue() * 100}%"></div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="sprint-control ${sprint.planState.name().toLowerCase() }">
	            <a class="glyphicon glyphicon-triangle-right sprint-link" href="#"></a>
	            <span class="glyphicon glyphicon-triangle-bottom sprint-dropdown"></span>
	        </div>
	        <div class="sprintlog">
		        <c:forEach items="${sprint.getUserStories()}" var="userstory">
		            <div usid="${userstory.id }" class="userstory">
		                <div class="userstory-titel">${userstory.description }</div>
		                <div class="userstory-content">
		                    Time:   <fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="0"/>h /
		                    		<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="0"/>h /
		                    		<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="0"/>h</br>
		                    Effort: ${userstory.getEffortValue()}</br>
		                    Value:  ${userstory.getBusinessValue()}
		                </div>
		            </div>
		        </c:forEach>
		    </div>		    	
	    </div>
    </c:forEach>
    <div id="quick-button-container">
	    <a id="openBacklog" class="quick-button" href="#">
	        <span class="quick-button-title">B</span><span class="quick-button-text">open Backlog</span>
	    </a>
	    <a class="quick-button" href="./addSprint.htm">
	        <span class="quick-button-title">S</span><span class="quick-button-text">new Sprint</span>
	    </a>
	    <a id="removebtn" class="quick-button" href="./editUserstories.htm">
	        <span class="quick-removebutton-title">X</span><span class="quick-removebutton-text">remove Userstory</span>
	    </a>
	</div>
</div>
