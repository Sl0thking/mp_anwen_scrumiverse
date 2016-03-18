<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="resources/colorpicker/css/bootstrap-colorpicker.css"/>
<script src="resources/colorpicker/js/bootstrap-colorpicker.js"></script>

<script type="text/javascript"> // projectSettings specific JavaScript/jQuery

var activeTab = ".role-tab";  // <----- ${tabString};
$(document).ready(function(){
    $(".quick-button").hide();
    activateButton(getActiveTab());
    activateTab();
	$(".form-control[fid]").change(function() {
		var fid = $(this).attr("fid");
		$("form[fid='" + fid + "']").submit();
	});
	
	$("#sendInvBtn").click(function () {
		var email = $("#invEmail").val()
		var targetUrl = "addUserToProject.htm?email=" + email;
		window.location.href = targetUrl;
	});
    
    $("a[role='tab']").click(function(){
        setActiveTab($(this).attr("href"));
        activateButton(getActiveTab());
    });
});
    
function getActiveTab(){
    return activeTab;
}
function setActiveTab(newTab){
    activeTab = newTab;
}
    
function activateTab(){
    $(".nav-tabs").children().each(function(){
        $(this).removeClass("active");
    });
    $(".nav-tabs").children().each(function(){
        if($(this).children().attr("href")==getActiveTab()){
            $(this).children(getActiveTab).click();
        }
    });
    $(".tab-content").children().each(function(){
        $(this).removeClass("active");
    });
    $(".tab-content").children(getActiveTab()).addClass("active");
}

function activateButton(btnTab){
    $(".quick-button").hide();
    $(".quick-button").each(function(){
        if($(this).attr("tab")==btnTab){
            $(this).show();
        }
    });
}
    
</script>
<div id="settings">
	<div id="settings-header">
		<div class="site-title">
			<span>
				<span class="glyphicon glyphicon-cog"></span>
				PROJECT SETTINGS
			</span>
			<c:if test="${deleteProject}">
				<a href="./removeProject.htm" data-toggle="tooltip" title="Delete project">
					<span class="glyphicon glyphicon-trash"></span>
				</a>
			</c:if>
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
								<c:if test="${removeFromProject}">
									<a href="./removeProjectUser.htm?id=${projectUser.user.userID}" data-toggle="tooltip" title="Remove member">
										<span class="glyphicon glyphicon-remove"></span>
									</a>
								</c:if>
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
		<div class="role-tab tab-pane fade in tab-pane-fix">
        	<div class="input-group input-group-fix">
                <span class="input-group-addon addon-fix">Role</span>
                <form:form fid="roleSelect" commandName="roleForm" action="projectSettings.htm?id=${project.projectID}">
		            <form:select class="form-control" path="role">
						<form:options  itemLabel="name" items="${project.getRoles()}"/>
					</form:select>
				</form:form>
			</div>
            <form:form commandName="selectedRole" action="updateRole.htm">
            <form:hidden path="roleID"/>
             <fieldset>
                   	<div class="input-group">
                        <span class="input-group-addon">Rolename</span>
                        <form:input disabled="${!selectedRole.isChangeable()}"  type="text" class="form-control" path="name"/>
                    </div>
                    <div class="roleSettings">
                        <div class="entity">
                            Project<br>
							<div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Project"/> Update</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Project"/> Delete</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Invite_To_Project"/> Invite Members</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Remove_From_Project"/> Remove Members</div>
                        </div>
                        <div class="entity">
                            Sprint<br>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_Sprint"/> Create</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_Sprint"/> Read </div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Sprint"/> Update</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Sprint"/> Delete</div>
                        </div>
                        <div class="entity">
                            User Story<br>

                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_UserStory"/> Create</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_UserStory"/> Read </div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_UserStory"/> Update</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_UserStory"/> Delete</div>
                        </div>
                        <div class="entity">
                            Task<br>  
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_Task"/> Create</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_Task"/> Read </div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Task"/> Update</div>
                            <div><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Task"/> Delete</div>
                        </div>
                    </div>
                    <c:choose>
                    	<c:when test="${selectedRole.isChangeable()}">
                    		 <button type="submit" class="btn btn-default">
                        		<span class="glyphicon glyphicon-save"></span>
                        		Save
                    		</button>
                    	</c:when>
                    	<c:otherwise>
                    		<span class="glyphicon glyphicon-warning-sign"></span> This is non changeable standard role
                    		<button disabled type="submit" class="btn btn-default">
                       			<span class="glyphicon glyphicon-save"></span>
                        		Save
                    		</button>
                    	</c:otherwise>
                    </c:choose>
                </fieldset>
                </form:form>
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
	                        <span class="input-group-addon color-component"><i></i></span>
	                        <input type="text" data-format="hex" value="#000000" class="form-control"/>
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
	                                    align: "left"
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
	<c:if test="${inviteToProject}">
		<div class="quick-button" tab=".detail-tab">
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
	</c:if>
    
    <a class="quick-button" tab=".role-tab" href="./addRole.htm">
        <span class="quick-button-title">R</span><span class="quick-button-text alternative">new Role</span>
    </a>
    <a class="quick-button" tab=".category-tab" href="./addCategory.htm">
        <span class="quick-button-title">C</span><span class="quick-button-text alternative">new Category</span>
    </a>
</div>