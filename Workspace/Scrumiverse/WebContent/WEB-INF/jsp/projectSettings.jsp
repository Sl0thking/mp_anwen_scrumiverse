<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript"> // projectSettings specific JavaScript/jQuery
$(document).ready(function(){
	$(".form-control[fid]").change(function() {
		var fid = $(this).attr("fid");
		$("form[fid='" + fid + "']").submit();
	});
	
	$("#sendInvBtn").click(function () {
		var email = $("#invEmail").val()
		var targetUrl = "addUserToProject.htm?email=" + email;
		window.location.href = targetUrl;
	});
});
</script>
<div id="settings">
	<div id="settings-header">
		<div class="site-title">
			<span>
				<span class="glyphicon glyphicon-cog"></span>
				PROJECT SETTINGS
			</span>
			<a href="./removeProject.htm" data-toggle="tooltip" title="Delete project">
				<span class="glyphicon glyphicon-trash"></span>
			</a>
		</div>
		<form:form action="saveProject.htm" commandName="project">
			<div class="input-group">
				<span class="input-group-addon">Name</span>
				<form:input type="text" class="form-control" path="name" value="${project.getName()}"/>
			</div>
			<div class="input-group">
				<span class="input-group-addon">Description</span>
				<form:textarea class="form-control" style="resize:vertical" path="description" value="${project.getDescription()}"/>
			</div>
			<form:hidden path="projectID"/>
			<button class="btn btn-default" type="submit">
				<span class="glyphicon glyphicon-save"></span>
				Save
			</button>
		</form:form>
	</div>
	<div class="user-list-title">
		<span class="glyphicon glyphicon-th-list"></span>
		MEMBERLIST
	</div>
	<div id="user-list">
		<c:forEach items="${project.getProjectUsers()}" var="projectUser">
			<!-- fix for commandName value bug -->
			<c:set var="projectUser" scope="request" value="${projectUser}"/> 
			<form:form fid="${projectUser.id}" action="changeProjectUser.htm" commandName="projectUser"> 
				<div class="user-card">
					<div class="user-card-header">
						<!-- insert messaging button here -->
						<a href="./removeProjectUser.htm?id=${projectUser.user.userID}" data-toggle="tooltip" title="Remove member">
							<span class="glyphicon glyphicon-remove"></span>
						</a>
					</div>
					<div class="user-content">
						<div class="user-picture">
							<img class="img-circle" alt="${projectUser.getUser().getName()}" src="./resources/userPictures/1.png" />
						</div>
						<div class="user-info">
							<div class="user-name">${projectUser.getUser().getName()}</div>
							<div class="user-email">
								<span class="glyphicon glyphicon-envelope"></span>
								<span>${projectUser.getUser().getEmail()}</span>
							</div>
						</div>
					</div>
					<div class="user-role" fid="${projectUser.id}">
						<div class="input-group">
							<span class="input-group-addon">Role</span>
							<form:select fid="${projectUser.id}" class="form-control" path="role">
								<form:options itemLabel="name" items="${project.getRoles()}"/>
							</form:select>
						</div>
					</div>
					<form:hidden path="user"/>
					<form:hidden path="id"/>
				</div>
			</form:form>
		</c:forEach>
	</div>
	<div id="settings-options"></div>
</div>
<div id="quick-button-container">
    <div class="quick-button">
        <span class="quick-button-title">+</span>
        <div class="quick-button-text">
        <!-- form start -->
        	<div class="input-group input-group-sm">
				<input type="text" id="invEmail" class="form-control" placeholder="E-Mail"/>		
				<div class="input-group-btn">
					<button id="sendInvBtn" class="btn btn-secondary" type="button">
						<span class="glyphicon glyphicon-send"></span>
						Invite user
					</button>
				</div>
			</div>
        <!-- form end -->        
        </div>
    </div>
</div>