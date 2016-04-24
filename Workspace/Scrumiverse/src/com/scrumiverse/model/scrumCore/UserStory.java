package com.scrumiverse.model.scrumCore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.scrumiverse.enums.MoscowState;
import com.scrumiverse.enums.PlanState;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;
import com.scrumiverse.model.scrumFeatures.WorkLog;

/**
 * User Story Model for Scrum Projects.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 24.04.16
 *
 */
@Entity
public class UserStory extends PlanElement {
	private int businessValue;
	private int effortValue;
	private MoscowState moscow;
	private Category category;
	private SortedSet<Task> tasks;
	private Date dueDate;
	private Sprint relatedSprint;
	private int risk;
	
	/**
	 * Constructor to set default values.
	 */
	public UserStory(){
		businessValue = 0;
		effortValue = 0;
		moscow = MoscowState.Could;
		tasks = new TreeSet<Task>();
		dueDate = new Date();
		category = null;
		setDescription("New UserStory");
		setPlanState(PlanState.Planning);
		setAcceptanceCriteria("");
		setHistory(new TreeSet<HistoryEntry>());
	}
	
	public int getBusinessValue() {
		return businessValue;
	}

	public void setBusinessValue(int businessValue) {
		this.businessValue = businessValue;
	}

	public int getEffortValue() {
		return effortValue;
	}

	public void setEffortValue(int effortValue) {
		this.effortValue = effortValue;
	}

	public MoscowState getMoscow() {
		return moscow;
	}

	public void setMoscow(MoscowState moscow) {
		this.moscow = moscow;
	}
	@OneToOne
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="UserStoryID")
	@Sort(type=SortType.NATURAL)
	public SortedSet<Task> getTasks() {
		return tasks;
	}

	public void setTasks(SortedSet<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "SprintID")
	public Sprint getRelatedSprint() {
		return relatedSprint;
	}

	public void setRelatedSprint(Sprint relatedSprint) {
		this.relatedSprint = relatedSprint;
	}
	/**
	 * Returns Users working on Tasks in this UserStory.
	 * @return Users from this UserStory
	 */
	@Transient
	public List<User> getResponsibleUsers(){
		List<User> userlist = new ArrayList<User>();
		for(Task task: getTasks()){
			for(User user: task.getResponsibleUsers()){
				if(!userlist.contains(user)){
					userlist.add(user);
				}
			}
		}
		return userlist;
	}
	/**
	 * Returns all planned minutes from tasks in this UserStory.
	 * @return Number of planned minutes
	 */
	@Transient
	public int getPlannedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user:task.getResponsibleUsers()){
				result += task.getPlannedMinOfUser(user);
			}
		}
		return result;
	}
	/**
	 * Returns all remaining minutes from tasks in this UserStory.
	 * @return Number of remaining minutes
	 */
	@Transient
	public int getRemainingMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getRemainingMinOfUser(user);
			}
		}
		if(result < 0) {
			return 0;
		}
		return result;
	}
	/**
	 * Returns all logged minutes of work from tasks in this UserStory.
	 * @return Number of worked minutes
	 */
	@Transient
	public int getWorkedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getWorkTimeOfUser(user);
			}
		}
		return result;
	}
	/**
	 * Adds a Task to this UserStory
	 * @param task the task to add
	 */
	public void addTask(Task task){
		tasks.add(task);
	}
	
	/**
	 * Returns a list of work logs from all tasks in this UserStory
	 * @return List of work logs sorted by time
	 */
	@Transient
	public List<WorkLog> getWorkLogs(){
		List<WorkLog> result = new ArrayList<WorkLog>();
		for(Task task: tasks){
			for(WorkLog work: task.getWorkLogs()){
				result.add(work);
			}
		}
		return (List<WorkLog>) result;
	}
	/**
	 * Returns remaining days until due date.
	 * @return Number of days
	 */
	@Transient
	public int getRemainingDays(){
		Date today = new Date();
		int result = 0;
		try{
			result = (int) (dueDate.getTime()/(1000*60*60*24)+1 - today.getTime()/(1000*60*60*24));
			if(result<=0){
				result=0;
			}
		}catch (NullPointerException e){
			return 0;
		}
		return result;
	}
	/**
	 * Returns the due date in the yyyy-MM-dd format
	 * @return formatted String of dueDate
	 */
	@Transient
	public String getFormattedDueDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dueDate);
	}

	@Override
	public String toString(){
		return "UserStory "+getId()+" - Description: "+getDescription();
	}

	public int getRisk() {
		return risk;
	}

	public void setRisk(int risk) {
		this.risk = risk;
	}
}