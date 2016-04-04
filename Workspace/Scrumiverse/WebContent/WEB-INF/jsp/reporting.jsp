<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src="https://code.highcharts.com/highcharts.js"></script>

<script>
$(document).ready(function() {
	//get the JSONObject of the selected sprint and draw the chart on page load
	var sprintJSON = JSON.parse($(".form-control").val());	
	drawChart(sprintJSON);
	//get the JSONObject of the selected sprint and draw the chart on change of dropdown-menu
	$(".form-control").change(function() {
		sprintJSON = JSON.parse($(".form-control").val());
		drawChart(sprintJSON);
	});
});
// get the JSONArray idealRemaining from the JSONObject and turn its content into a javascript array
function prepareIdealRemaining(sprintJSON) {
	var idealRemaining = [];
	var data = sprintJSON.idealRemaining;
	for (var i = 0; i < data.length;i++) {
		idealRemaining.push([data[i]]);
	}
	return idealRemaining
}
// get the JSONArray backlogScope from the JSONObject and turn its content into a javascript array
function prepareBacklogScope(sprintJSON){
	var backlogScope = [];
	var data = sprintJSON.backlogScope;
	for (var i = 0; i < data.length;i++) {
		backlogScope.push([data[i]]);
	}
	return backlogScope
}
//get the JSONArray doneItems from the JSONObject and turn its content into a javascript array
function prepareDoneItems(sprintJSON){
	var doneItems = [];
	var data = sprintJSON.doneItems;
	for (var i = 0; i < data.length;i++) {
		doneItems.push([data[i]]);
	}
	return doneItems
}
//get the JSONArray remainingItems from the JSONObject and turn its content into a javascript array
function prepareRemainingItems(sprintJSON){
	var remainingItems = [];
	var data = sprintJSON.remainingItems;
	for (var i = 0; i < data.length;i++) {
		remainingItems.push([data[i]]);
	}
	return remainingItems
}
// get the year from the JSONObject and return it as int
function prepareYear(sprintJSON) {
	var startDate = new Date(sprintJSON.startDate);
	year = startDate.getFullYear();
	return year
}
// get the month from the JSONObject and return it as int
function prepareMonth(sprintJSON) {
	var startDate = new Date(sprintJSON.startDate);
	month = startDate.getMonth();
	return month 
}
// get the day from the JSONObject and return it as int
function prepareDay(sprintJSON) {
	var startDate = new Date(sprintJSON.startDate);
	day = startDate.getDate();
	return day
}

function drawChart(sprintJSON) {
	// prepare variables for chart drawing
	var year = prepareYear(sprintJSON);
	var month = prepareMonth(sprintJSON);
	var day = prepareDay(sprintJSON);
	var idealRemaining = [];
	idealRemaining = prepareIdealRemaining(sprintJSON);
	var backlogScope = [];
	backlogScope = prepareBacklogScope(sprintJSON);
	var doneItems = [];
	doneItems = prepareDoneItems(sprintJSON);
	var remainingItems = [];
	remainingItems = prepareRemainingItems(sprintJSON);
	
	$('.sprintBurnDown').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Sprint BurnDown'
        },
        xAxis: {
            type: 'datetime',
        },
       
        yAxis: {
            title: {
            	text: 'Backlog items'
            }
        },
        plotOptions: {
        	series: {
        		pointStart: Date.UTC(year,month,day),
        		pointInterval: 24*3600*1000
        	}
        },
        series: [{name: 'Scope of Backlog items', data: backlogScope},
                 {name: 'Ideal Remaining Backlog items', data: idealRemaining, lineWidth: '1', dashStyle: 'Dash'},
                 {name: 'Remaining Backlog items', data: remainingItems, color: 'red'},
                 {name: 'Done Backlog items', data: doneItems, color: 'green'}]
    });
};


</script>

<div class="reporting-page">
	<%-- Div containing the chart --%>
	<div class="sprintBurnDown">
	</div>
	<div class="sprints">
		<div class="input-group">
			<span class="input-group-addon">Sprint:</span>
			<%--Dropdown-menu with all sprints in the project--%>
			<form:select class="form-control" path="chartData">
				<c:forEach items="${chartData}" var="item">
					<form:option value="${item.getValue()}">${item.getKey().getDescription()}</form:option>
				</c:forEach>				
			</form:select>
		</div>
	</div>
</div>