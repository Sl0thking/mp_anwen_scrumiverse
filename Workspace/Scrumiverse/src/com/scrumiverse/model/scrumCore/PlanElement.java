package com.scrumiverse.model.scrumCore;

import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.ChangeEvent;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;

/**
 * Abstract PlanElement Model for 
 * shared plan attributes between Scrum DataModells.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 25.02.16
 *
 */
@MappedSuperclass
public abstract class PlanElement implements Comparable<PlanElement>{
	private String description;
	private int id;
	private SortedSet<HistoryEntry> history;
	private PlanState planState;
	private String acceptanceCriteria;
		
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
	
	@OneToMany
	@JoinColumn(name = "PlanElementID")
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
	
	public void addHistoryEntry(ChangeEvent event, User user){
		
	}
	
	public void setCriteria(String criteria){
		
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
		return this.getDescription().compareTo(o.getDescription());
	}
}
