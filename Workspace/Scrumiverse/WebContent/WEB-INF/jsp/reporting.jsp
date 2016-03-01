<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="https://code.highcharts.com/highcharts.js"></script>
<script>
$(function() {
	$('.sprintBurnDown').highcharts({
		chart: {type: 'line'},
		title: {text: 'Sprint BurnDown', x: -20},
		xAxis: {categories: ['Test1', 'Test2', 'Test3', 'Test4']},
		yAxis: {title: 'Backlog items'},
		plotOptions: {series:{pointStart: 0}},
		series:[{name: 'Ideal remaining backlog items', data: [30, 20, 10, 0]}]
	});
});
</script>

<div class="reporting-page">
	<div class="sprintBurnDown">
	</div>
</div>