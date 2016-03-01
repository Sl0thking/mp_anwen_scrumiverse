<script type="text/javascript">
// project settings site specific javascript

</script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		<form:form role="form" action="saveProject.htm" commandName="project">
			<form:input class="project-name" path="name" value="${project.getName()}"/>
			<form:textarea class="project-description" path="description" value="${project.getDescription()}"/>
			<button type="submit">Save</button>
		</form:form>
	</div>
	<div class="user-list-title">
		<span class="glyphicon glyphicon-th-list"></span>
		MEMBERLIST
	</div>
	<div id="user-list">
		<!-- start forEach -->
		<div class="user-card">
			<div class="user-card-header">
				<!-- insert messaging button here -->
				<a href="./removeProjectUser.htm?id=${user.id}" data-toggle="tooltip" title="Remove member">
					<span class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
			<div class="user-content">
				<div class="user-picture">
					<img class="img-circle" alt="Horst" src="./resources/userPictures/1.png" />
				</div>
				<div class="user-info">
					<div class="user-name">Horst Meyer</div>
					<div class="user-email">
						<span class="glyphicon glyphicon-envelope"></span>
						<span>horst.meyer@atlas-elektronik.com</span>
					</div>
				</div>
			</div>
			<div class="user-role">
				<!-- insert role selection here -->
			</div>
		</div>
		<div class="user-card">
			<div class="user-card-header">
				<!-- insert messaging button here -->
				<a href="./removeProjectUser.htm?id=${user.id}" data-toggle="tooltip" title="Remove member">
					<span class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
			<div class="user-content">
				<div class="user-picture">
					<img class="img-circle" alt="Horst" src="./resources/userPictures/1.png" />
				</div>
				<div class="user-info">
					<div class="user-name">Horst Meyer</div>
					<div class="user-email">
						<span class="glyphicon glyphicon-envelope"></span>
						<span>horst.meyer@atlas-elektronik.com</span>
					</div>
				</div>
			</div>
			<div class="user-role">
				<!-- insert role selection here -->
			</div>
		</div>
		<div class="user-card">
			<div class="user-card-header">
				<!-- insert messaging button here -->
				<a href="./removeProjectUser.htm?id=${user.id}" data-toggle="tooltip" title="Remove member">
					<span class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
			<div class="user-content">
				<div class="user-picture">
					<img class="img-circle" alt="Horst" src="./resources/userPictures/1.png" />
				</div>
				<div class="user-info">
					<div class="user-name">Horst Meyer</div>
					<div class="user-email">
						<span class="glyphicon glyphicon-envelope"></span>
						<span>horst.meyer@atlas-elektronik.com</span>
					</div>
				</div>
			</div>
			<div class="user-role">
				<!-- insert role selection here -->
			</div>
		</div>
		<div class="user-card">
			<div class="user-card-header">
				<!-- insert messaging button here -->
				<a href="./removeProjectUser.htm?id=${user.id}" data-toggle="tooltip" title="Remove member">
					<span class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
			<div class="user-content">
				<div class="user-picture">
					<img class="img-circle" alt="Horst" src="./resources/userPictures/1.png" />
				</div>
				<div class="user-info">
					<div class="user-name">Horst Meyer</div>
					<div class="user-email">
						<span class="glyphicon glyphicon-envelope"></span>
						<span>horst.meyer@atlas-elektronik.com</span>
					</div>
				</div>
			</div>
			<div class="user-role">
				<!-- insert role selection here -->
			</div>
		</div>
		<!-- end forEach -->
	</div>
	<div id="settings-options">
		<!-- insert save button here -->
	</div>
</div>
<div id="quick-button-container">
    <div class="quick-button">
        <span class="quick-button-title">+</span><span class="quick-button-text">invite user</span>
    </div>
</div>