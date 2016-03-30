package com.scrumiverse.model.account;

/**
 * Enum of different user rights
 * 
 * @author Kevin Jolitz, Toni Serfling
 * @version 25.02.2016
 *
 */
public enum Right{
	//Project specific
	Invite_To_Project,
	Remove_From_Project,
	Update_Project,
	Delete_Project,
	
	//Sprint Rights
	Read_Sprint,
	Create_Sprint,
	Update_Sprint,
	Delete_Sprint,
	
	//User Story rights
	Create_UserStory,
	Read_UserStory,
	Update_UserStory,
	Delete_UserStory,

	//Task rights
	Create_Task,
	Read_Task,
	Update_Task,
	Delete_Task,
	
	View_Review,
	
	//Notifications
	Notify_UserStory_Task_for_Current_Sprint,
	Notify_Your_UserStory_Task,
	Notify_PlannedMin_for_Current_Sprint
}