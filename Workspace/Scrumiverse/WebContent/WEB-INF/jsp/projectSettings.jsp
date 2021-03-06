<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="resources/colorpicker/css/bootstrap-colorpicker.css"/>
<script src="resources/colorpicker/js/bootstrap-colorpicker.js"></script>
<script src="resources/javascript/dialog.js"></script>
<script type="text/javascript"> // projectSettings specific JavaScript/jQuery

/* scans the url for the tab, needed for the active tab-pane */
var activeTab = "#"+"${param.tab}";
if(activeTab==="#"){
	activeTab = "#detail-tab";
}

$(document).ready(function(){
	/* Append the dismis-button for the error-container */
	$(".error").append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
	activateTab();
	/* Hides the buttons in the button-container. Activate the buttons, which are relative for the page/tab */
    $(".quick-button").hide();
    activateButton(getActiveTab());
	$("select.form-control[fid]").change(function() {
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

function changeURL(value){
	var params = $.param();
	params["tab"] = value;
	var url = "?"+$.param(params);
}
/* Returns activeTab*/
function getActiveTab(){
    return activeTab;
}

/* Sets activeTab*/
function setActiveTab(newTab){
    activeTab = newTab;
}

/* Checks the activeTab-variable and selects the tab. */
function activateTab(){
	if(activeTab!="#detail-tab"){
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
}

/* Activate the buttons, which are relative for the page/tab */
function activateButton(btnTab){
    $(".quick-button").hide();
    $(".quick-button").each(function(){
        if($(this).attr("tab")==btnTab){
            $(this).show();
        }
    });
}
</script>
<%-- Errorcontainer appears with in error if the url have the parameter error=X --%>
<div class="error-container">
	<c:if test="${param.error eq 1}"><div class="alert alert-danger alert-dismissible error" role="alert">unknown error occoured</div></c:if>
    <c:if test="${param.error eq 2}"><div class="alert alert-danger alert-dismissible error" role="alert">can't remove last admin</div></c:if>
    <c:if test="${param.error eq 3}"><div class="alert alert-danger alert-dismissible error" role="alert">you should not shut yourself out</div></c:if>
    <c:if test="${param.error eq 4}"><div class="alert alert-danger alert-dismissible error" role="alert">cannot upload file (empty, wrong format or bigger than 4MB)</div></c:if>
	<c:if test="${param.error eq 5}"><div class="alert alert-danger alert-dismissible error" role="alert">user doesn't exist</div></c:if>
</div>
<%-- Userdialog of the projectsettings. Appears when an object would be removed for ever. --%>
<div id="user-dialog">
    <div class="dialog-header"><span class="glyphicon glyphicon-alert"></span> <span id="dialog-title">Delete Project</span></div>
    <div class="dialog-body">
        <div class="dialog-text"></div>
        <a id="dialog-hide" class="btn btn-danger">No</a>
        <a href="#" id="dialog-delete" class="btn btn-success">Yes</a>
    </div>
</div>
<%-- Projectsetting main page --%>
<div id="settings">
	<div id="settings-header">
		<div class="site-title">
			<span>
				<span class="glyphicon glyphicon-cog"></span>
				PROJECT SETTINGS
			</span>
			<c:if test="${deleteProject}">
				<a class="dialog_action" link="./removeProject.htm" 
					data-toggle="tooltip" title="Delete project" msg="Do you really want to delete this project?">
					<span class="glyphicon glyphicon-trash"></span>
				</a>
			</c:if>
			<%-- Navbar tabs --%>
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" role="tab" href="#detail-tab">
                        <span class="glyphicon glyphicon-info-sign"></span>
                        Detail
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" role="tab" href="#role-tab">
                        <span class="glyphicon glyphicon-list-alt"></span>
                        Role
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" role="tab" href="#category-tab">
                        <span class="glyphicon glyphicon-list-alt"></span>
                        Category
                    </a>
                </li>
            </ul>
			<%-- Navbar tabs end --%>
		</div>
	</div>
	<%--Tabcontent with the 3 tabs --%>
	<div class="tab-content">
		<%--Detailtab with project settings(projectpicture, name, description and duedate)
			and the members with their role --%> 
        <div id="detail-tab" class="tab-pane fade in active">
        	<%-- Projectpicture form --%>
        	<form class="project-picture" method="POST" action="changeProjectPic.htm" enctype="multipart/form-data" >
        		<img src="${project.picPath}" width="150" height="150"/>
       			<div class="input-group">
       				<input name="image" type="file">
       				<button class="btn btn-default" type="submit">Upload</button>
       			</div>
       		</form>
        	<%-- Projectdetails form --%>
			<form:form action="saveProject.htm" commandName="project" acceptCharset="UTF-8">
				<fieldset>
					<div class="input-group">
						<span class="input-group-addon">Name</span>
						<form:input type="text" class="form-control" path="name" value="${project.getName()}"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Description</span>
						<form:textarea class="form-control" style="resize:vertical" path="description" value="${project.getDescription()}"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Due Date</span>
						<form:input class="form-control" path="dueDate" type="date" value="${project.getFormattedDueDate()}" />
					</div>
					<form:hidden path="projectID"/>
					<form:hidden path="picPath"/>
					<button class="btn btn-default" type="submit">
						<span class="glyphicon glyphicon-save"></span>
						Save
					</button>
				</fieldset>
			</form:form>
			<%-- Projectdetails form end --%>
			<div class="user-list-title">
				<span class="glyphicon glyphicon-th-list"></span>
				MEMBERLIST
			</div>
			<%-- Userlist of the project --%>
			<div id="user-list">
				<%-- Creates the usercard for each project-member --%>
				<c:forEach items="${project.getProjectUsers()}" var="projectUser">
					<!-- fix for commandName value bug -->
					<c:set var="projectUser" scope="request" value="${projectUser}"/> 
					<form:form fid="${projectUser.id}" action="changeProjectUser.htm" commandName="projectUser">
						<div class="user-card">
							<div class="user-card-header">
								<!-- insert messaging button here -->
								<c:if test="${removeFromProject}">
									<a class="dialog_action" link="./removeProjectUser.htm?id=${projectUser.user.userID}" 
										data-toggle="tooltip" title="Remove User" msg="Do you really want to remove ${projectUser.user.name} from this project?">
										<span class="glyphicon glyphicon-remove"></span>
									</a>
								</c:if>
							</div>
							<%-- Details about the user --%>
							<div class="user-content">
								<div class="user-picture">
									<img class="img-circle" alt="${projectUser.getUser().getName()}" src="${projectUser.user.profileImagePath}" />
								</div>
								<div class="user-info">
									<div class="user-name">${projectUser.getUser().getName()}</div>
									<div class="user-email">
										<span class="glyphicon glyphicon-envelope"></span>
										<span>${projectUser.getUser().getEmail()}</span>
									</div>
								</div>
							</div>
							<%-- Role of the user --%>
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
			<%-- Userlist end --%>
			<div id="settings-options"></div>
		</div>
		<%-- Roletab with the roles of the project--%>
		<div id="role-tab" class="tab-pane fade in">
			<%-- role selection --%>
        	<div class="input-group">
                <span class="input-group-addon">Role</span>
                <form:form fid="roleSelect" commandName="roleForm" action="projectSettings.htm?id=${project.projectID}&tab=role-tab">
		            <form:select fid="roleSelect" class="form-control" path="role">
						<form:options  itemLabel="name" itemValue="roleID" items="${project.getRoles()}"/>
					</form:select>
				</form:form>
			</div>
			<%-- Role form - build the rights of the selected role, splitted in sections --%>
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
							<span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Project"/> Update</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Project"/> Delete</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Invite_To_Project"/> Invite Members</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Remove_From_Project"/> Remove Members</span>
                        </div>
                        <div class="entity">
                            Sprint<br>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_Sprint"/> Create</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_Sprint"/> Read </span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Sprint"/> Update</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Sprint"/> Delete</span>
                        </div>
                        <div class="entity">
                            User Story<br>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_UserStory"/> Create</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_UserStory"/> Read </span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_UserStory"/> Update</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_UserStory"/> Delete</span>
                        </div>
                        <div class="entity">
                            Task<br>  
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Create_Task"/> Create</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Read_Task"/> Read </span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Update_Task"/> Update</span>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Delete_Task"/> Delete</span>
                        </div>
                        <div class="entity">
                            Notifications<br>  
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Notify_UserStory_Task_for_Current_Sprint"/> Changes to userstories or tasks in current sprint</span><br>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Notify_Your_UserStory_Task"/> Changes to your userstories or tasks in current sprint </span><br>
                            <span><form:checkbox disabled="${!selectedRole.isChangeable()}" path="rights" value="Notify_PlannedMin_for_Current_Sprint"/> Changes to planned minutes from a task in current sprint</span>
                        </div>
                    </div>
                    <%-- Sets the remove and save button if the member is allowed to change rights.
						If the member isn't allowed the remove button will not be placed an the save button is disabled. --%>
                    <c:choose>
                    	<c:when test="${selectedRole.isChangeable()}">
                    		 <button type="submit" class="btn btn-default">
                        		<span class="glyphicon glyphicon-save"></span>
                        		Save
                    		</button>
							<a class="dialog_action btn btn-default" link="./removeRole.htm?id=${selectedRole.roleID }" id="remove-btn"
								data-toggle="tooltip" title="Delete role" msg="Do you really want to delete ${selectedRole.name}?">
								<span class="glyphicon glyphicon-trash"></span>
						  		Remove
							</a>
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
            <%-- Role form end --%>
            <div id="settings-options"></div>
        </div>
        <%-- Categorytab --%>
        <div id="category-tab" class="tab-pane fade in">
        	<%-- category selection --%>
             <div class="input-group">
                 <span class="input-group-addon">Category</span>
                  <form:form fid="categorySelect" commandName="categoryForm" action="projectSettings.htm?id=${project.projectID}&tab=category-tab">
                  	<form:select fid="categorySelect" class="form-control" path="category" disabled="${project.getCategories().size() < 1}">
						<form:options itemLabel="name" items="${project.getCategories()}" itemValue="id"/>
				    </form:select>
				  </form:form>
             </div>
             <%-- build the category form with the BOOTSTRAP COLORPICKER 2.3(Originally written by @eyecon and maintained by @mjolnic and the Github community.)  --%>
             <form:form commandName="selectedCategory" action="updateCategory.htm">
             <form:hidden path="id"/>
             <fieldset>
                 <div class="input-group">
                     <span class="input-group-addon">Name</span>
                     <form:input type="text" class="form-control" path="name" disabled="${project.getCategories().size() < 1}"/>
                 </div>
                 <div class="input-group colorpicker-component picker">
                     <span class="input-group-addon">Color</span>
                     <span class="input-group-addon color-component"><i id="i"></i></span>
                     <form:input type="text" data-format="hex" path="colorCode" class="form-control" disabled="${project.getCategories().size() < 1}"/>
                 </div>
                 <script>
                 /* BOOTSTRAP COLORPICKER 2.3 */
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
                 <%-- Sets the remove and save button if the member is allowed to change categories.
                 	If the member isn't allowed the remove button will not be placed an the save button is disabled. --%>
                 <c:choose>
                 	<c:when test="${project.getCategories().size() < 1}">
                 		<button class="btn btn-default" type="submit" disabled>
                    		<span class="glyphicon glyphicon-save"></span>
                     	Save
                 		</button>
                 	</c:when>
                 	<c:otherwise>
                 		<button class="btn btn-default" type="submit">
                    		<span class="glyphicon glyphicon-save"></span>
                     	Save
                 		</button>
						<a class="dialog_action btn btn-default"  link="./removeCategory.htm?id=${selectedCategory.id }" id="remove-btn"
							data-toggle="tooltip" title="Delete category" msg="Do you really want to delete ${selectedCategory.name}?">
							<span class="glyphicon glyphicon-trash"></span>
					  		Remove
						</a>
                 	</c:otherwise>
                 </c:choose>
             </fieldset>
         </form:form>
         <%-- category form end --%>
         <div id="settings-options"></div>
     </div>
	</div>
	<%--Tabcontent end --%>
</div>
<%-- Buttoncontainer of the projectsettings. With Javascript the button are hidden when their tab isn't active --%>
<div id="quick-button-container">
	<c:if test="${inviteToProject}">
		<div class="quick-button" tab="#detail-tab">
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
    <a class="quick-button" tab="#role-tab" href="./addRole.htm">
        <span class="quick-button-title">R</span><span class="quick-button-text alternative">new Role</span>
    </a>
    <a class="quick-button" tab="#category-tab" href="./addCategory.htm">
        <span class="quick-button-title">C</span><span class="quick-button-text alternative">new Category</span>
    </a>
</div>