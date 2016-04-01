<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
$(document).ready(function(){
    $(".backlogbar").click(function(){
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
    $(".addusbtn").hide();
    $("#removebtn").hide();
    selectUserstory();
    toggleSprintlog();
    moveUserstories();
    if($(this).parent().attr("class")=="content"){
        checkSelected(".content");
    }
});

function moveUserstories(){
    $(".addusbtn, #removebtn").click(function(){
        var sprint = $(".openlog").attr("sprintid");
        var backlogusids = [];
        var sprintusids = [];
        getSelected(".content").each(function(){
            backlogusids.push($(this).attr("usid"));
        });
        getSelected(".openlog").each(function(){
        	sprintusids.push($(this).attr("usid"));
        });
        var urlchange = "?sprintid="+sprint+"&addedStories="+backlogusids+"&removedStories="+sprintusids;
        $(".addusbtn").attr("href", $(".addusbtn").attr("href")+urlchange);
        $("#removebtn").attr("href", $("#removebtn").attr("href")+urlchange);
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
        $(".addusbtn").show();
    } else {
        $(".addusbtn").hide();
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
                Time</br>
                <fmt:formatNumber value="${project.getIceBoxRemainingTime()/60}" maxFractionDigits="1"/> /
                <fmt:formatNumber value="${project.getIceBoxPlannedTime()/60}" maxFractionDigits="1"/> h
                <div class="progressbar">
                    <div class="progress" style="width:${project.getIceBoxRemainingTime() / project.getIceBoxPlannedTime() * 100}%"/></div>
                </div>
            </div>
            <div class="data-container">
                Effort</br>
                ${project.getIceBoxDoneEffort()} / ${project.getIceBoxEffort()}
                <div class="progressbar">
                    <div class="progress" style="width:${project.getIceBoxDoneEffort() / project.getIceBoxEffort() * 100}%"/></div>
                </div>
            </div>
            <div class="data-container">
                Value</br>
                ${project.getIceBoxDoneValue()} / ${project.getIceBoxValue()}
                <div class="progressbar">
                    <div class="progress" style="width:${project.getIceBoxDoneValue() / project.getIceBoxValue() * 100}%"/></div>
                </div>
            </div>
        </div>
        <div class="content">
        	<c:forEach items="${project.getIceBox()}" var="userstory">
	            <div usid="${userstory.id }" class="userstory">
			        <div class="userstory-titel">${userstory.description }</div>
			        <div class="userstory-content">
			        Time:   <fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="1"/> /
			                <fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="1"/> /
			        		<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="1"/> h</br>
			        Effort: ${userstory.getEffortValue()}</br>
			        Value:  ${userstory.getBusinessValue()}
			    	</div>
				</div>
			</c:forEach>
        </div>
        <a class="addusbtn" href="./syncBacklogAndSprint.htm">Add to Sprint</a>
    </div>
</div>

<div class="sprintpage">
	<c:forEach items="${sprints}" var="sprint">
	    <div sprintid="${sprint.id}" class="sprint">
	        <div id="${sprint.planState.toString() }" class="sprint-state"></div>
	        <div class="sprint-content">
	            <div class="sprint-name">${sprint.description }</div>
	            <div class="sprint-stats">
	                <div class="sprint-time-overview">
	                    <div class="sprint-sandclock"></div>
	                    <div class="sprint-date">${sprint.startDate.toString().substring(0,10)} -</br>${sprint.endDate.toString().substring(0,10)}</div>
	                    <div class="sprint-time"><fmt:formatNumber value="${sprint.getRemainingMinutes() / 60 / 24 }" maxFractionDigits="1" /> d</div>
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
	                        	<fmt:formatNumber value="${sprint.getRemainingMinutes() / 60 }" maxFractionDigits="1" /> / 
	                        	<fmt:formatNumber value="${sprint.getPlannedMinutes() / 60}" maxFractionDigits="1" /> h</div>
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
	                        	<div class="progress" style="width:${sprint.getCompletedBusinessValue() / sprint.getCombinedBusinessValue() * 100}%"></div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div id="${sprint.planState.toString() }" class="sprint-control">
	            <a class="glyphicon glyphicon-triangle-right sprint-link" href="#" data-toggle="modal" data-target="#sprintmodal_${sprint.id }"></a>
	            <span class="glyphicon glyphicon-triangle-bottom sprint-dropdown"></span>
	        </div>
	        <div class="sprintlog">
		        <c:forEach items="${sprint.getUserStories()}" var="userstory">
		            <div usid="${userstory.id }" class="userstory">
		                <div class="userstory-titel">${userstory.description }</div>
		                <div class="userstory-content">
		                    Time:   <fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="0"/> /
		                    		<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="0"/> /
		                    		<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="0"/> h</br>
		                    Effort: ${userstory.getEffortValue()}</br>
		                    Value:  ${userstory.getBusinessValue()}
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>
	    <div id="sprintmodal_${sprint.id }" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		    <div class="modal-dialog">
		        <div class="modal-content sprint-settings">
	                <div class="modal-header">
	                    <span>
					    <span class="glyphicon glyphicon-cog"></span>
					    SPRINT SETTINGS
	                    </span>
	                    <a href="./deleteSprint.htm?id=${sprint.id}" data-toggle="tooltip" title="Delete Sprint">
							<span class="glyphicon glyphicon-trash"></span>
						</a>
						<ul class="nav nav-tabs">
                               <li class="active">
                                   <a data-toggle="tab" role="tab" href=".detail-tab">
                                       <span class="glyphicon glyphicon-info-sign"></span>
                                       Detail
                                   </a>
                               </li>
                               <li>
                                   <a data-toggle="tab" role="tab" href=".history-tab">
                                       <span class="glyphicon glyphicon-list-alt"></span>
                                       History
                                   </a>
                               </li>
                           </ul>
					</div>
					<div class="tab-content">
						<div class="modal-body detail-tab tab-pane fade in active">
						<!--  Fix for commandName bug --> 
						<c:set var="selectedSprint" value="${sprint}" scope="request"/> 
							<form:form action="updateSprint.htm" commandName="selectedSprint">
								<form:hidden path="id"/>
				                <div class="input-group">
				                    <span class="input-group-addon input-group-addon-fix">Description</span>
				                    <form:input class="form-control input-control" path="description" value="${selectedSprint.description }"/>
				                </div>
				                <div class="input-group">
				                <span class="input-group-addon input-group-addon-fix">Acceptance Criteria</span>
				                <form:textarea class="form-control input-control" path="acceptanceCriteria" value="${selectedSprint.acceptanceCriteria }"></form:textarea>
				                </div>
				                <div class="date-container">
				                    <div class="input-group input-startdate">
				                        <span class="input-group-addon input-group-addon-fix">Start Date</span>
				                        <form:input type="date" class="form-control input-control" path="startDate" value="${selectedSprint.startDate.toString().substring(0,10) }"/>
				                    </div>
				                    <div class="input-group input-duedate">
				                        <span class="input-group-addon input-group-addon-fix">Due Date</span>
				                        <form:input type="date" class="form-control input-control" path="endDate" value="${selectedSprint.endDate.toString().substring(0,10) }"/>
				                    </div>
				                </div>
				                <button class="btn btn-default" type="submit">
			                        <span class="glyphicon glyphicon-save"></span>
			                        Save
			                    </button>
			                </form:form>
			                <div class="sprint-data">
			                    <div class="modal-data-container">
			                        Time</br>
			                        <fmt:formatNumber value="${selectedSprint.getRemainingMinutes() / 60 }" maxFractionDigits="1" /> / 
		                        	<fmt:formatNumber value="${selectedSprint.getPlannedMinutes() / 60}" maxFractionDigits="1" /> h
			                        <div class="progressbar">
			                            <div class="progress" style="width:${selectedSprint.getRemainingMinutes() / selectedSprint.getPlannedMinutes() * 100}%"></div>
			                        </div>
			                    </div>
			                    <div class="modal-data-container">
			                        Effort</br>
			                        ${selectedSprint.getCompletedEffort()} / ${selectedSprint.getCombinedEffort() }
			                        <div class="progressbar">
			                            <div class="progress" style="width:${selectedSprint.getCompletedEffort() / selectedSprint.getCombinedEffort() * 100}%"></div>
			                        </div>
			                    </div>
			                    <div class="modal-data-container">
			                        Value</br>
			                        ${selectedSprint.getCompletedBusinessValue()} / ${selectedSprint.getCombinedBusinessValue() }
			                        <div class="progressbar">
			                            <div class="progress" style="width:${selectedSprint.getCompletedBusinessValue() / selectedSprint.getCombinedBusinessValue() * 100}%"></div>
			                        </div>
			                    </div>
			                </div>
			                <div class="userstory-container">
			                      <c:forEach items="${selectedSprint.getUserStories()}" var="userstory">
			            			<div usid="${userstory.id }" class="userstory userstory-fix">
				                		<div class="userstory-titel">${userstory.description }</div>
				                		<div class="userstory-content">
					                		Time:   <fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="0"/> / 
					                		<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="0"/> / 
					                		<fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="0"/> h</br>
					                		Effort: ${userstory.getEffortValue()}</br>
					                		Value:  ${userstory.getBusinessValue()}
				                		</div>
				            		</div>
			        			</c:forEach>
			                </div>
		                </div>
		            	<div class="modal-body history-tab tab-pane fade in">
		            		<c:forEach items="${selectedSprint.getHistory() }" var="history">
			            		<div class="history-item">
	                                <div class="history-changeevent">
	                                	${history.changeEvent }
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
    </c:forEach>

    <div id="quick-button-container">
	    <a id="openBacklog" class="quick-button" href="#">
	        <span class="quick-button-title">B</span><span class="quick-button-text">open Backlog</span>
	    </a>
	    <a class="quick-button" href="./addSprint.htm">
	        <span class="quick-button-title">S</span><span class="quick-button-text">new Sprint</span>
	    </a>
	    <a id="removebtn" class="quick-button" href="./syncBacklogAndSprint.htm">
	        <span class="quick-removebutton-title">X</span><span class="quick-removebutton-text">remove Userstory</span>
	    </a>
	</div>
</div>
