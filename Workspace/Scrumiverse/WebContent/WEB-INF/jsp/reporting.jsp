<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src="https://code.highcharts.com/highcharts.js"></script>

<script>
$(document).ready(function() {	
	$(".form-control").change(function() {
		alert("Hi!");
		var fid = $(this).attr("fid");
		window.location.replace("/selectedSprint"+ fid +"");
	});
	
	$('.sprintBurnDown').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Sprint BurnDown'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
            	day: '%e of %b'
            }
        },
        yAxis: {
            title: {
                text: 'Backlog items'
            }
        },
        series: [{name: 'Scope of Backlog items', data: ${jsonObject.getJSONArray("backlogScope")} , pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*1000},
                 {name: 'Ideal Remaining Backlog items', data: ${jsonObject.getJSONArray("idealRemaining")}, lineWidth: '1', dashStyle: 'Dash', pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*1000},
                 {name: 'Remaining Backlog items', color: 'red', data:${jsonObject.getJSONArray("remainingItems")}, pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*1000},
                 {name: 'Done Backlog items', color: 'green', data:${jsonObject.getJSONArray("doneItems")}, pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*1000}]
    });
});


</script>

<div class="reporting-page">
	<div class="sprintBurnDown">
	</div>
	<div class="sprints">
		<div class="input-group">
			<span class="input-group-addon">Sprint:</span>
			<form:select fid="${sprint.getId()}" class="form-control" path="sprints">
				<form:options itemLabel="description" items="${sprints}"/>
			</form:select>
		</div>
	</div>
</div>