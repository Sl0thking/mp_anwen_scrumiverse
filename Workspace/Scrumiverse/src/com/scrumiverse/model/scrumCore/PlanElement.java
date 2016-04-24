package com.scrumiverse.model.scrumCore;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.scrumiverse.enums.ChangeEvent;
import com.scrumiverse.enums.PlanState;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;

/**
 * Abstract PlanElement Model for 
 * shared plan attributes between Scrum DataModels.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 17.03.16
 *
 */
@MappedSuperclass
public abstract class PlanElement implements Comparable<PlanElement>{
	private String description;
	private int id;
	private SortedSet<HistoryEntry> history;
	private PlanState planState;
	private String acceptanceCriteria;
		
	public PlanElement() {
		history = new TreeSet<HistoryEntry>();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Sort(type=SortType.NATURAL)
	public SortedSet<HistoryEntry> getHistory() {
		return history;
	}
	
	public void setHistory(SortedSet<HistoryEntry> history) {
		this.history = history;
	}
	
	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}
	
	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	
	/**
	 * Adds a new history entry to this planElement
	 * 
	 * @param event occured changeEvent
	 * @param user user who triggered this event
	 */
	public void addHistoryEntry(ChangeEvent event, User user){
		this.history.add(new HistoryEntry(user, event));
	}
	
	/**
	 * Adds a new history entry to this planElement
	 * 
	 * @param entry history entry for this planElement
	 */
	public void addHistoryEntry(HistoryEntry entry){
		this.history.add(entry);
	}
	
	@JoinColumn(name = "planState")
	@Enumerated(EnumType.STRING)
	public PlanState getPlanState() {
		return planState;
	}
	
	public void setPlanState(PlanState planState) {
		this.planState = planState;
	}
	
	public boolean equals(PlanElement pelement){
		return getId() == pelement.getId();
	}
	
	@Override
	public int compareTo(PlanElement o) {
		if(this.getDescription().compareTo(o.getDescription()) == 0) {
			return new Integer(this.getId()).compareTo(new Integer(o.getId()));
		} else {
			return this.getDescription().compareTo(o.getDescription()) ;
		}
	}
}