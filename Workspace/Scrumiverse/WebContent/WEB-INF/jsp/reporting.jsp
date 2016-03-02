<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="https://code.highcharts.com/highcharts.js"></script>

<script>
$(document).ready(function() {
	$('.sprintBurnDown').highcharts({
		chart: {type: 'line'},
		title: {text: '', align: 'center'},
		subtitle: {text: 'Sprint BurnDown', align: 'center'},
		xAxis: {type: 'datetime', dateTimeLabelFormats: {day: '%e of %b'}},
		yAxis: {title: {text: 'Backlog items'}},
		plotOptions: {series:{pointStart: 0}},
		series:[{name: 'Scope of backlog items', data: []},
		        {name: 'Ideal remaining backlog items', data: [], color: 'grey', dashStyle: 'Dash'},
				{name: 'Remaining backlog items', data: [], color: 'red'},
				{name: 'Done backlog items', data: [], color: 'green'}]
	});	
});
</script>

<div class="reporting-page">
	<div class="sprintBurnDown">
	</div>
</div>