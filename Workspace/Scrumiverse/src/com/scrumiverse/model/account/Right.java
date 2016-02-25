package com.scrumiverse.model.account;

/**
 * Enum of different user rights
 * 
 * @author Kevin Jolitz, Toni Serfling
 * @version 25.02.2016
 *
 */
public enum Right {
	//Project specific
	Invite_To_Project,
	Remove_From_Project,
	Manage_Project,
	Delete_Project,
	
	Create_UserStory,
	Delete_UserStory,
	Edit_UserStory,
	Read_UserStory,
	Create_Task,
	Delete_Task,
	Edit_Task,
	Read_Task,
	Create_Sprint,
	Delete_Sprint,
	Edit_Sprint,
	Read_Sprint,
	
	View_Review,
	AlertAllNotifications_From_CurrentSprint,

}