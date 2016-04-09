package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NotChangeableRoleException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.exception.TriedToRemoveAdminException;
import com.scrumiverse.model.account.*;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.web.StdRoleNames;

import javax.persistence.JoinColumn;

/**
 * Datamodel for a scrumiverse project
 * 
 * @author Kevin Jolitz, Lasse Jacobs, Toni Serfling
 * @version 14.03.2016
 */

@Entity
public class Project {
	private int projectID;
	private String name;
	private String description;
	private SortedSet<Role> roles;
	private SortedSet<ProjectUser> projectUsers;
	private Date dueDate;
	private SortedSet<Sprint> sprints;
	private SortedSet<UserStory> userstories;
	private SortedSet<Category> categories;
	
	public Project() {
		name = "New Project";
		description = "Project Description";
		projectUsers = new TreeSet<ProjectUser>();
		categories = new TreeSet<Category>();
		roles = new TreeSet<Role>();
		sprints = new TreeSet<Sprint>();
		dueDate = new Date();
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
	
	@Column(columnDefinition="TEXT")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
	@JoinColumn(name = "ProjectID", nullable=false)
	@Sort(type=SortType.NATURAL)
	public SortedSet<ProjectUser> getProjectUsers() {
		return projectUsers;
	}
	
	public void setProjectUsers(SortedSet<ProjectUser> projectUsers) {
		this.projectUsers = projectUsers;
	}
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Sort(type=SortType.NATURAL)
	public SortedSet<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(SortedSet<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "ProjectID")
	@Sort(type=SortType.NATURAL)
	public SortedSet<Sprint> getSprints() {
		return sprints;
	}
	
	public void setSprints(SortedSet<Sprint> sprints) {
		this.sprints = sprints;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "ProjectID", nullable=true)
	@Sort(type=SortType.NATURAL)
	public SortedSet<UserStory> getUserstories() {
		return userstories;
	}
	
	/**
	 * Returns all userstories currently not in a sprint
	 * @return SortedSet<UserStory>
	 */
	@Transient
	public SortedSet<UserStory> getIceBox() {
		SortedSet<UserStory> iceBox = new TreeSet<UserStory>();
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				iceBox.add(us);
			}
		}
		return iceBox;
	}
	public void setUserstories(SortedSet<UserStory> userstories) {
		this.userstories = userstories;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Sort(type=SortType.NATURAL)
	public SortedSet<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(SortedSet<Category> categories) {
		this.categories = categories;
	}
	
	/**
	 * Adds a category to the project
	 * @param Category
	 */
	@Transient
	public void addCategory(Category c) {		
		this.categories.add(c);				
	}
	/**
	 * Removes a category from the project
	 * @param int
	 */
	@Transient
	public void deleteCategory(int CategoryID) {
		this.categories.remove(CategoryID);		
	}
	
	/**
	 * Adds an User with a specific role as a ProjectUser to the project
	 * @param User
	 * @param Role
	 */
	public void addProjectUser(User u, Role r) {
		ProjectUser projectUser = new ProjectUser(u,r);
		projectUsers.add(projectUser);		
	}
	
	/**
	 * Removes a ProjectUser from the project
	 * @param User
	 * @throws NoSuchUserException
	 * @throws TriedToRemoveAdminException
	 */
	public void removeProjectUser(User u) throws NoSuchUserException, TriedToRemoveAdminException {
		ProjectUser pu = getProjectUserFromUser(u);
		if(containsAdminRights(pu.getRole())) {
			throw new TriedToRemoveAdminException();
		}
		projectUsers.remove(pu);		
	}
	
	/**
	 * Removes a ProjectUser from the project if boolean forced is set
	 * @param User
	 * @param Boolean
	 * @throws NoSuchUserException
	 * @throws TriedToRemoveAdminException
	 */
	public void removeProjectUser(User u, boolean forced) throws NoSuchUserException, TriedToRemoveAdminException {
		if(forced) {
			ProjectUser pu = getProjectUserFromUser(u);
			projectUsers.remove(pu);
		} else {
			removeProjectUser(u);
		}
	}
	
	/**
	 * Returns given user as a ProjectUser
	 * @param User
	 * @return ProjectUser
	 * @throws NoSuchUserException
	 */
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
	
	/**
	 * Checks if given User has given Right
	 * @param Right
	 * @param User
	 * @return boolean
	 */
	public boolean hasUserRight(Right right, User user) {
		try {
			ProjectUser pUser = getProjectUserFromUser(user);
			return pUser.getRole().hasRight(right);
		} catch (NoSuchUserException e) {
			return false;
		}
	}
	/**
	 * Applies the given Role to given ProjectUser 
	 * @param User
	 * @param Role
	 * @throws RoleNotInProjectException
	 * @throws TriedToRemoveAdminException
	 * @throws NoSuchUserException
	 */
	@Transient
	public void setProjectUserRole(User user, Role r) throws RoleNotInProjectException, TriedToRemoveAdminException, NoSuchUserException {
		if(!roles.contains(r)) {
			throw new RoleNotInProjectException();
		} 
		ProjectUser pUser = getProjectUserFromUser(user);
		if(countAdmins() == 1 && containsAdminRights(pUser.getRole()) && !containsAdminRights(r) ) {
			throw new TriedToRemoveAdminException();
		}
		pUser.setRole(r);
	}
	
	/**
	 * Checks if given Role contains Admin rights
	 * @param Role
	 * @return boolean
	 */
	private boolean containsAdminRights(Role r) {
		Set<Right> rights = r.getRights();
		return rights.contains(Right.Invite_To_Project) &&
			   rights.contains(Right.Update_Project);
	}
	
	/**
	 * Counts the amount of admin users in the project
	 * @return int
	 */
	private int countAdmins() {
		int adminCount = 0;
		for(ProjectUser pUser : projectUsers) {
			if(containsAdminRights(pUser.getRole())) {
				adminCount++;
			}
		}
		return adminCount;
	}
	
	/**
	 * Returns all ProjectUsers with given Role
	 * @param Role
	 * @return List<ProjectUser>
	 */
	private List<ProjectUser> getProjectUsersWithRole(Role r) {
		List<ProjectUser> pUsers = new ArrayList<ProjectUser>();
		for(ProjectUser projectUser : projectUsers) {
			if(projectUser.getRole().equals(r)) {
				pUsers.add(projectUser);
			}
		}
		return pUsers;
	}
	
	/**
	 * Deletes given Role from the project
	 * @param Role
	 * @throws RoleNotInProjectException
	 * @throws NotChangeableRoleException
	 */
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
	
	public void addSprint(Sprint s) {
		this.sprints.add(s);
	}
	
	public void removeSprint(int sprintID) {
		this.sprints.remove(sprintID);
	}
	
	public void addUserStory(UserStory u) {
		this.userstories.add(u);
	}
	
	public void removeUserStory(UserStory userStory) {
		this.userstories.remove(userStory);
	}

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
	
	/**
	 * Adds an User to the Project with the Standart Role Member
	 * @param User
	 */
	public void addProjectUser(User user) {
		Role role = getRole(StdRoleNames.Member.name());
		this.addProjectUser(user, role);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * Checks if given User is a member of the Project
	 * @param User
	 * @return boolean
	 */
	public boolean isUserMember(User user) {
		for(ProjectUser pUser : projectUsers) {
			if(pUser.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns all users of the project
	 * @return Set<User>
	 */
	@Transient
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<User>();
		for(ProjectUser pUser : projectUsers) {
			users.add(pUser.getUser());
		}
		return users;
	}	
	/**
	 * Returns the remaining minutes of all UserStories currently not part of a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxRemainingTime() {
		int remTime = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				remTime += us.getRemainingMinutes();
			}
		}
		if(remTime < 0) {
			return 0;
		}
		return remTime;
	}
	/**
	 * Returns the worked minutes of all UserStories currently not part of a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxWorkedTime() {
		int workTime = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				workTime += us.getWorkedMinutes();
			}
		}
		if(workTime < 0) {
			return 0;
		}
		return workTime;
	}
	/**
	 * Returns the planned minutes of all UserStories currently not part of a Sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxPlannedTime() {
		int planTime = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				planTime += us.getPlannedMinutes();
			}
		}
		return planTime;
	}
	
	/**
	 * Returns the BusinessValue of all UserStories currently not part of a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxValue() {
		int value = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				value += us.getBusinessValue();
			}
		}
		return value;
	}
	/**
	 * Returns the value of all done UserStories that are currently not part of a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxDoneValue() {
		int value = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null && us.getPlanState().equals(PlanState.Done)) {
				value += us.getBusinessValue();
			}
		}
		return value;
	}
	/**
	 * Returns the Effort of all UserStories currently not in a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxEffort() {
		int value = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null) {
				value += us.getEffortValue();
			}
		}
		return value;
	}
	/**
	 * Returns the Effort of all done UserStories currently not in a sprint
	 * @return int
	 */
	@Transient
	public int getIceBoxDoneEffort() {
		int value = 0;
		for(UserStory us : userstories) {
			if(us.getRelatedSprint() == null && us.getPlanState().equals(PlanState.Done)) {
				value += us.getEffortValue();
			}
		}
		return value;
	}
	/**
	 * Returns the Role of the given Rolename
	 * @param String
	 * @return Role
	 */
	public Role getRole(String rolename) {
		for(Role role : roles) {
			if(role.getName().equals(rolename)) {
				return role;
			}
		}
		return null;
	}


	public void addRole(Role role) {
		this.roles.add(role);
	}
	/**
	 * Returns the amount of Users with the given Role
	 * @param Role
	 * @return int
	 */
	public int getUsersWithRoleCount(Role role) {
		int counter = 0;
		for(ProjectUser pUser : projectUsers) {
			if(pUser.getRole().equals(role)) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Removes given Category from the project
	 * @param Category
	 */
	public void removeCategory(Category category) {
		for(UserStory uStory : userstories) {
			uStory.setCategory(null);
		}
		this.categories.remove(category);
	}
	/**
	 * Returns the sprint that is currently in progress
	 * @return Sprint
	 */
	@Transient
	public Sprint getCurrentSprint(){
		for(Sprint sprint: sprints){
			if(sprint.getPlanState().equals(PlanState.InProgress)){
				return sprint;
			}
		}
		return null;
	}
}