<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript"> // projectSettings specific JavaScript/jQuery
$(document).ready(function(){
	$(".user-card > .user-role select").change(function() {
		alert("Role change event!");
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
			<button type="button" class="btn btn-default" type="submit">
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
			<!-- <form:form action="changeProjectUser.htm" commandName="projectUser"> -->
				<div class="user-card">
					<div class="user-card-header">
						<!-- insert messaging button here -->
						<a href="./removeProjectUser.htm?id=${projectUser.getId()}" data-toggle="tooltip" title="Remove member">
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
					<div class="user-role">
						<div class="input-group">
							<span class="input-group-addon">Role</span>
							<select class="form-control">
								<c:forEach items="${project.getRoles()}" var="role">
									<c:choose>
										<c:when test="${role.getName() == projectUser.getRole().getName()}">
											<option selected="selected" value="${role.getName()}">${role.getName()}</option>
										</c:when>
										<c:otherwise>
											<option value="${role.getName()}">${role.getName()}</option>
										</c:otherwise>										
									</c:choose>		
								</c:forEach>
        					</select>
						</div>
					</div>
				</div>
			<!-- </form:form> -->
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
				<input class="form-control" placeholder="E-Mail"/>		
				<div class="input-group-btn">
					<button class="btn btn-secondary" type="button">
						<span class="glyphicon glyphicon-send"></span>
						Invite user
					</button>
				</div>
			</div>
        <!-- form end -->        
        </div>
    </div>
</div>