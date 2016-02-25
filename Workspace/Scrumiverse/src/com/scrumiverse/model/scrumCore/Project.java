package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NotChangeableRoleException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.exception.triedToRemoveLastAdminException;
import com.scrumiverse.model.account.*;
import javax.persistence.JoinColumn;

/**
 * Datamodel for a scrumiverse project
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 24.02.2016
 */

@Entity
public class Project {
	private int projectID;
	private String name;
	private String description;
	private Set<Role> roles;
	private Set<ProjectUser> projectUsers;
	private Date dueDate;
	private Set<Sprint> sprints;
	private Set<UserStory> userstories;
	//next sprint
	//private List<Category> categories;
	
	public Project() {
		name = "New Project";
		description = "Project Description";
		projectUsers = new LinkedHashSet<ProjectUser>();
		roles = new LinkedHashSet<Role>();
		sprints = new LinkedHashSet<Sprint>();
		dueDate = new Date();
		prepareStdRoles(roles);
	}
	
	private void prepareStdRoles(Set<Role> roles) {
		Role productOwner = new Role("ProductOwner");
		productOwner.setChangeable(false);
		productOwner.addRight(Right.Invite_To_Project);
		productOwner.addRight(Right.Manage_Project);
		productOwner.addRight(Right.Remove_From_Project);
		productOwner.addRight(Right.Create_Sprint);
		productOwner.addRight(Right.Create_UserStory);
		productOwner.addRight(Right.Delete_Project);
		productOwner.addRight(Right.Delete_Sprint);
		productOwner.addRight(Right.Delete_UserStory);
		productOwner.addRight(Right.Edit_Sprint);
		productOwner.addRight(Right.Edit_UserStory);
		productOwner.addRight(Right.Read_Sprint);
		productOwner.addRight(Right.Read_Task);
		productOwner.addRight(Right.Read_UserStory);
		productOwner.addRight(Right.View_Review);
		
		Role member = new Role("Member");
		member.setChangeable(false);
		member.addRight(Right.Read_Sprint);
		member.addRight(Right.Read_Task);
		member.addRight(Right.Read_UserStory);
		member.addRight(Right.View_Review);
		member.addRight(Right.Create_Task);
		member.addRight(Right.Edit_Task);
		member.addRight(Right.Delete_Task);
		
		Role scrumMaster = new Role("ScrumMaster");
		scrumMaster.setChangeable(false);
		scrumMaster.addRight(Right.Read_Sprint);
		scrumMaster.addRight(Right.Read_Task);
		scrumMaster.addRight(Right.Read_UserStory);
		scrumMaster.addRight(Right.View_Review);
		scrumMaster.addRight(Right.AlertAllNotifications_From_CurrentSprint);
		
		roles.add(productOwner);
		roles.add(member);
		roles.add(scrumMaster);
	}

	@Id
	@GeneratedValue
	@Column(name="ProjectID")
	public int getProjectID() {
		return projectID;
	}
	
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
	@JoinColumn(name = "ProjectID", nullable=false)
	public Set<ProjectUser> getProjectUsers() {
		return projectUsers;
	}
	
	public void setProjectUsers(Set<ProjectUser> projectUsers) {
		this.projectUsers = projectUsers;
	}
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "ProjectID")
	public Set<Sprint> getSprints() {
		return sprints;
	}
	
	public void setSprints(Set<Sprint> sprints) {
		this.sprints = sprints;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "ProjectID", nullable=true)
	public Set<UserStory> getUserstories() {
		return userstories;
	}
	
	public void setUserstories(Set<UserStory> userstories) {
		this.userstories = userstories;
	}
//	public List<Category> getCategories() {
//		return categories;
//	}
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}
	
//	public void addCategory(Category c) {		
//		this.categories.add(c);				
//	}
//	
//	public void deleteCategory(int CategoryID) {
//		this.categories.remove(CategoryID);		
//	}
	
	public void addProjectUser(User u, Role r) {
		ProjectUser projectUser = new ProjectUser(u,r);
		projectUsers.add(projectUser);		
	}
	
	public void removeProjectUser(User u) throws NoSuchUserException {
		ProjectUser pu = getProjectUserFromUser(u);
		projectUsers.remove(pu);		
	}
	
	@Transient
	public ProjectUser getProjectUserFromUser(User u) throws NoSuchUserException {
		ProjectUser requestedProjectUser = null;
		for(ProjectUser pu : this.projectUsers) {
			if(pu.getUser().equals(u)) {
				requestedProjectUser = pu;
			}
		}
		if(requestedProjectUser != null) {
			return requestedProjectUser;
		} else {
			throw new NoSuchUserException();
		}
	}
	
	public boolean hasUserRight(Right right, User user) {
		try {
			ProjectUser pUser = getProjectUserFromUser(user);
			return pUser.getRole().hasRights(right);
		} catch (NoSuchUserException e) {
			return false;
		}
	}
	
	public void setProjectUserRole(User user, Role r) throws RoleNotInProjectException, triedToRemoveLastAdminException, NoSuchUserException {
		if(!roles.contains(r)) {
			throw new RoleNotInProjectException();
		} 
		ProjectUser pUser = getProjectUserFromUser(user);
		if(countAdmins() == 1 || !containsAdminRights(pUser.getRole())) {
			throw new triedToRemoveLastAdminException();
		}
		pUser.setRole(r);
	}
	
	//Move to role?
	private boolean containsAdminRights(Role r) {
		Set<Right> rights = r.getRights();
		return rights.contains(Right.Invite_To_Project) &&
			   rights.contains(Right.Manage_Project);
	}

	private int countAdmins() {
		int adminCount = 0;
		for(ProjectUser pUser : projectUsers) {
			if(containsAdminRights(pUser.getRole())) {
				adminCount++;
			}
		}
		return adminCount;
	}
	
	private List<ProjectUser> getProjectUsersWithRole(Role r) {
		List<ProjectUser> pUsers = new ArrayList<ProjectUser>();
		for(ProjectUser projectUser : projectUsers) {
			if(projectUser.getRole().equals(r)) {
				pUsers.add(projectUser);
			}
		}
		return pUsers;
	}

	public void deleteRole(Role r) throws RoleNotInProjectException, NotChangeableRoleException {
		if(!roles.contains(r)) {
			throw new RoleNotInProjectException();
		} else if(!r.isChangeable()) {
			throw new NotChangeableRoleException();
		}
		for(ProjectUser pUser : getProjectUsersWithRole(r)) {
			pUser.setRole((Role) roles.toArray()[1]);
		}
		this.roles.remove(r);
	}
	
//	public void addRole(Role r) {
//		this.roles.add(r);
//	}
//	
//	public void addSprint(Sprint s) {
//		this.sprints.add(s);
//	}
//	
//	public void removeSprint(int sprintID) {
//		this.sprints.remove(sprintID);
//	}
//	
	public void addUserStory(UserStory u) {
		this.userstories.add(u);
	}
	
	public void removeUserStory(int UserStoryID) {
		this.userstories.remove(UserStoryID);
	}
//	
//	@Override
//	public String toString() {
//		return "Project [projectID=" + projectID + ", name=" + name + ", description=" + description + ", roles="
//				+ roles + ", users=" + users + ", sprints=" + sprints + ", userstories=" + userstories + ", categories="
//				+ categories + "]";
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + projectID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (projectID != other.projectID)
			return false;
		return true;
	}

	public void addProjectUser(User user) {
		Role role = (Role) roles.toArray()[1];
		this.addProjectUser(user, role);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isUserMember(User user) {
		for(ProjectUser pUser : projectUsers) {
			if(pUser.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		for(ProjectUser pUser : projectUsers) {
			users.add(pUser.getUser());
		}
		return users;
	}
}