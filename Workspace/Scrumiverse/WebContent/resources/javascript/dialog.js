/*
 * This dialog handles removing-events in Scrumivers. It will be the last
 * chance for the user over deleting or not deleting the entity.
 * The dialog need the following attribute in the html-tag
 * 
 * class: must be class=".dialog_action"
 * link: e.g. link="./removeProject.htm?id=1", needed for the "yes/btn-success"
 * title: e.g. title="Remove project", needed for the header of the dialog (usefull for the tooltip)
 * msg: e.g.msg="Do you really want to delete this project", the message of the dialog
 * 
 * example:
 *  <a class="dialog_action"
 *  	link="./removeProject.htm"
 *  	data-toggle="tooltip" title="Delete project"
 *  	msg="Do you really want to delete this project?">
 * 
 * 
 * 
 * Every page need one user-dialog in its html-code
 * <div id="user-dialog">
 * 		<div class="dialog-header"><span class="glyphicon glyphicon-alert"></span> <span id="dialog-title">Delete Project</span></div>
 * 			<div class="dialog-body">
 * 			<div class="dialog-text"></div>
 * 			<a id="dialog-hide" class="btn btn-danger">No</a>
 * 			<a href="#" id="dialog-delete" class="btn btn-success">Yes</a>
 * 		</div>
 * 	</div>
 */

$(document).ready(function(){
	$("#user-dialog").hide();
	$(".dialog_action").click(function(){
	    $("#dialog-delete").attr("href",$(this).attr("link"));
	    $("#dialog-title").text($(this).attr("title"));
	    $(".dialog-text").text($(this).attr("msg"));
	    $("#user-dialog").show();
	});
	$("#dialog-hide").click(function(){
	    $("#user-dialog").hide();
	});
});