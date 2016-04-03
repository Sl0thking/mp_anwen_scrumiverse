package com.scrumiverse.model.scrumFeatures;

/**
 * Events that occur on plan elements
 * 
 * @author Kevin Jolitz
 * @version 25.03.2016
 */
public enum ChangeEvent {
	//SPRINT
	USER_STORY_ASSIGNED,
	USER_STORY_REMOVED, 
	SPRINT_CREATED,
	SPRINT_UPDATED,
	
	//USERSTORY
	SPRINT_ASSIGNED,
	SPRINT_REMOVED,
	USER_STORY_CREATED,
	USER_STORY_UPDATED,
	TASK_DELETED,
	
	//TASK
	TASK_CREATED,
	TASK_UPDATED,
	USER_REMOVED,
	USER_ADDED,
}