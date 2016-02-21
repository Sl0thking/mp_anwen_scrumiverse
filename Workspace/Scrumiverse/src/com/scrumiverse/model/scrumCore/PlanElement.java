package com.scrumiverse.model.scrumCore;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.ChangeEvent;

@Entity
public abstract class PlanElement {
	private String description;
	private int id;
//	private List<HistoryEntry> history;
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
//	public List<HistoryEntry> getHistory() {
//		return history;
//	}
//	public void setHistory(List<HistoryEntry> history) {
//		this.history = history;
//	}
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
}