/*
 * this dialog need the following attribute in the html-tag
 * 
 * class: must be class=".dialog_action"
 * link: e.g. "./removeProject.htm?id=1", needed for the "yes/btn-success"
 * title: use full for the tooltip, needed for the header of the dialog
 * msg: the message of the dialog e.g."Do you really want to delete this project"
 * 
 * example:
 *  <a class="dialog_action"
 *  	link="./removeProject.htm"
 *  	data-toggle="tooltip" title="Delete project"
 *  	msg="Do you really want to delete this project?">
 *  
 */

$(document).ready(function(){
	$("#user-dialog").hide();
	$(".dialog_action").click(function(){
		console.log("CLICKED");
	    $("#dialog-delete").attr("href",$(this).attr("link"));
	    $("#dialog-title").text($(this).attr("title"));
	    $(".dialog-text").text($(this).attr("msg"));
	    $("#user-dialog").show();
	});
	$("#dialog-hide").click(function(){
	    $("#user-dialog").hide();
	});
});