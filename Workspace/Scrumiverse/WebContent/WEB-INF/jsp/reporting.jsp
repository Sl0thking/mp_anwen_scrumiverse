<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="https://code.highcharts.com/highcharts.js"></script>

<script>
$(document).ready(function() {	
	
	var unprocessedData = '<c:out value="${jsonobject}"></c:out>'
	alert(unprocessedData)
	
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
        series: [{name: 'Scope of Backlog items', data: [30, 30, 31, 36, 36, 36, 30], pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*5000},
                 {name: 'Ideal Remaining Backlog items', data: [36, 30, 24, 18, 12, 6, 0], lineWidth: '1', dashStyle: 'Dash', pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*5000},
                 {name: 'Remaining Backlog items', color: 'red', data:[30, 30, 31, 34, 25, 20, 11], pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*5000},
                 {name: 'Done Backlog items', color: 'green', data:[0, 0, 0, 2, 11, 16, 19], pointStart: Date.UTC(2016, 0, 1), pointInterval: 24*3600*5000}]
    });
});


</script>

<div class="reporting-page">
	<div class="sprintBurnDown">
	</div>
	<div class="sprint-selection">
	Sprint Selection:
		<form:select name="Sprint">
			<form:options items="${sprints}"/>
		</form:select>
	</div>
</div>