<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="resources/colorpicker/css/bootstrap-colorpicker.css"/>
<script src="resources/colorpicker/js/bootstrap-colorpicker.js"></script>

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
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" role="tab" href=".detail-tab">
                        <span class="glyphicon glyphicon-info-sign"></span>
                        Detail
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" role="tab" href=".role-tab">
                        <span class="glyphicon glyphicon-list-alt"></span>
                        Role
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" role="tab" href=".category-tab">
                        <span class="glyphicon glyphicon-list-alt"></span>
                        Category
                    </a>
                </li>
            </ul>
		</div>
	</div>
	<div class="tab-content">
        <div class="detail-tab tab-pane fade in active">
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
			<div id="settings-options">
				<c:if test="${param.error eq 1}">
					<c:out value="An error occured" />
				</c:if>
			</div>
		</div>
		<div class="role-tab tab-pane fade in">
            <form action="">
                <div class="input-group">
                    <span class="input-group-addon">Role</span>
                    <select class="form-control" name="roles">
                        <option>Member</option>
                        <option>Guest</option>
                        <option>Extern</option>
                        <option>Teacher</option>
                    </select>
                </div>
                <fieldset>
                    <div class="input-group">
                        <span class="input-group-addon">Rolename</span>
                        <input type="text" class="form-control" name="rolename">
                    </div>
                    <div class="roleSettings">
                        <div class="entity">
                            Project<br>
                            <div><input type="checkbox" name="createProject" value="project" > create</div>
                            <div><input type="checkbox" name="removeProject" value="project" > remove</div>
                            <div><input type="checkbox" name="updateProject" value="project" > update</div>
                        </div>
                        <div class="entity">
                            Sprint<br>
                            <div><input type="checkbox" name="createSprint" value="sprint"> create
                            </div>
                            <div><input type="checkbox" name="removeSprint" value="sprint"> remove</div>
                            <div><input type="checkbox" name="updateSprint" value="sprint"> update</div></div>
                        <div class="entity">
                            User Story<br>
                            <div><input type="checkbox" name="createUserstory" value="userstory"> create</div>
                            <div><input type="checkbox" name="removeUserstory" value="userstory"> remove</div>
                            <div><input type="checkbox" name="updateUserstory" value="userstory"> update</div>
                        </div>
                        <div class="entity">
                            Task<br>
                            <div><input type="checkbox" name="createTask" value="task"> create</div>
                            <div><input type="checkbox" name="removeTask" value="task"> remove</div>
                            <div><input type="checkbox" name="updateTask" value="task"> update</div>
                        </div>
                    </div>
                    <button class="btn btn-default" type="submit">
                        <span class="glyphicon glyphicon-save"></span>
                        Save
                    </button>
                </fieldset>
            </form>
            <div id="settings-options"></div>
        </div>
        <div class="category-tab tab-pane fade in">
            <div class="category-tab tab-pane fade in">
	            <form>
	                <div class="input-group">
	                    <span class="input-group-addon">Category</span>
	                    <select class="form-control" name="categories">
	                        <option>Category</option>
	                        <option>Category</option>
	                        <option>Category</option>
	                        <option>Category</option>
	                    </select>
	                </div>
	                <fieldset>
	                    <div class="input-group input-group-fix">
	                        <span class="input-group-addon">Name</span>
	                        <input type="text" class="form-control" name="rolename">
	                    </div>
	                    <div class="input-group input-group-fix colorpicker-component picker">
	                        <span class="input-group-addon">Color</span>
	                        <input type="text" data-format="hex" value="#000000" class="form-control"/>
	                        <span class="input-group-addon color-component"><i></i></span>
	                    </div>
	                        <script>
	                            $(function(){
	                                $('.picker').colorpicker({
	                                  colorSelectors: {
	                                    '#000000': '#000000',
	                                    '#337ab7': '#337ab7',
	                                    '#5cb85c': '#5cb85c',
	                                    '#5bc0de': '#5bc0de',
	                                    '#f0ad4e': '#f0ad4e',
	                                    '#d9534f': '#d9534f'
	                                  },
	                                    component: '.color-component',
	                                    format: 'hex',
	                                });
	                            });
	                        </script>
	                    <button class="btn btn-default" type="submit">
	                        <span class="glyphicon glyphicon-save"></span>
	                        Save
	                    </button>
	                </fieldset>
	            </form>
	            <div id="settings-options"></div>
	        </div>
        </div>
	</div>
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
    <a class="quick-button" href="./addRole.htm">
        <span class="quick-button-title">R</span><span class="quick-button-text alternative">new Role</span>
    </a>
    <a class="quick-button" href="./addCategory.htm">
        <span class="quick-button-title">C</span><span class="quick-button-text alternative">new Category</span>
    </a>
</div>