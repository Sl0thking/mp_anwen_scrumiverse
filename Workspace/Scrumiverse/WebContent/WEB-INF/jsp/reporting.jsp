<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src="https://code.highcharts.com/highcharts.js"></script>

<script>
$(document).ready(function() {
	var sprintJSON = JSON.parse($(".form-control").val());
	var startDate = sprintJSON.startDate
	startDate = startDate.getDate()
	alert(startDate)
	var idealRemaining = "["+sprintJSON.idealRemaining+"]"
	drawChart(startDate, idealRemaining);
	$(".form-control").change(function() {
		sprintJSON = JSON.parse($(".form-control").val());
		startDate = sprintJSON.startDate
		idealRemaining = "["+sprintJSON.idealRemaining+"]"
		alert(idealRemaining)
		drawChart(idealRemaining, startDate);
	});
});

function drawChart(idealRemaining, startDate) {
	$('.sprintBurnDown').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Sprint BurnDown'
        },
       
        yAxis: {
            title: {
            	text: 'Backlog items'
            }
        },
        series: [{name: 'Scope of Backlog items', data: [1,2,3,4,5] , pointStart: startDate, pointInterval: 24*3600*1000},
                 {name: 'Ideal Remaining Backlog items', data: [idealRemaining], lineWidth: '1', dashStyle: 'Dash', pointStart: startDate, pointInterval: 24*3600*1000},
                 {name: 'Remaining Backlog items', color: 'red', data: [], pointStart: startDate, pointInterval: 24*3600*1000},
                 {name: 'Done Backlog items', color: 'green', data:[], pointStart: startDate, pointInterval: 24*3600*1000}]
    });
};


</script>

<div class="reporting-page">
	<div class="sprintBurnDown">
	</div>
	<div class="sprints">
		<div class="input-group">
			<span class="input-group-addon">Sprint:</span>
			<form:select class="form-control" path="chartData" >
				<c:forEach items="${chartData}" var="item">
					<form:option value="${item.getValue()}">${item.getKey().getDescription()}</form:option>
				</c:forEach>				
			</form:select>
		</div>
	</div>
</div>