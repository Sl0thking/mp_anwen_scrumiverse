<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body class="bg">
	<div id="backlog">
		<c:forEach items="${userstories}" var = "userstory">
			<div class="userstory">
				<div class="userstory-planstate" id="${userstory.planState}"></div>
		        <div class="userstory-titel">${userstory.description}</div>
		        <canvas id="clock" class="userstory-sandclock"></canvas>
		        <div class="userstory-time">${userstory.getRemainingDays()}d</div>
		        <div class="userstory-memberbox">
		        	<c:forEach items="${userstory.getResponsibleUsers()}" var="user">
		        		<img class="user-img alt="err" src="${user.getProfileImagePath()}">
		        	</c:forEach>
		        </div>
		        <div class="userstory-category">Category</div>
		        <div class="userstory-sprint">
			        <c:choose>
					    <c:when test="${userstory.relatedSprint == null}">Backlog</c:when>
					    <c:otherwise>${userstory.relatedSprint.description}</c:otherwise>
					</c:choose>
		        </div>
		        <div class="userstory-timestats">
		        	<fmt:formatNumber value="${userstory.getWorkedMinutes()/60}" maxFractionDigits="1"/> / 
                	<fmt:formatNumber value="${userstory.getRemainingMinutes()/60}" maxFractionDigits="1"/> / 
                    <fmt:formatNumber value="${userstory.getPlannedMinutes()/60}" maxFractionDigits="1"/> h
                </div>
		        <div class="userstory-moscow">${userstory.getMoscow().toString()}</div>
		        <div class="userstory-value">${userstory.businessValue}</div>
		        <div class="userstory-risk">R: ${userstory.risk}</div>
		        <div class="userstory-effort">${userstory.effortValue}</div>
        		<div class="userstory-control" id="${userstory.planState}">
					<a class="glyphicon glyphicon-triangle-right userstory-settings" href="#" data-toggle="modal" data-target=".userstory-detail"></a>
				</div>
			</div>
		    <div class="modal fade userstory-detail" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
			    <div class="modal-dialog">
			        <div class="modal-content userstory-detail-settings">
			            <form>
			                <div class="modal-header"> 
			                    <span>
							    <span class="glyphicon glyphicon-cog"></span>
							    Userstory Settings
			                    </span>
			                    <a href="./removeUserStory.htm?userstoryid=" data-toggle="tooltip" title="Delete Userstory">
									<span class="glyphicon glyphicon-trash"></span>
								</a>
							</div>
			                <div class="input-group">
			                    <span class="input-group-addon input-group-addon-fix">Description</span>
			                    <textarea class="form-control input-control">[US001] Killing Humanity</textarea>
			                </div>
			                <div class="input-group">
			                    <span class="input-group-addon input-group-addon-fix">Criteria</span>
			                    <textarea class="form-control input-control">Everyone Dead</textarea>
			                </div>
			                <div class="input-group">
			                	<span class="input-group-addon input-group-addon-fix">Sprint</span>
			                	<select class="form-control input-control">
								    <option>[S001] Start with a small loan of 1 million dollars</option>
								    <option>[S002] World Domination</option>
								    <option>Backlog</option>
								</select>
			                </div>
			                <div class="input-container">
			                	<div class="input-group input-container-left">
				                	<span class="input-group-addon input-group-addon-fix">Planstate</span>
				                	<select class="form-control input-control">
									    <option>Planning</option>
									    <option>InProgress</option>
									    <option>Done</option>
									</select>
				                </div>
				                <div class="input-group input-container-right">
				                	<span class="input-group-addon input-group-addon-fix">Category</span>
				                	<select class="form-control input-control">
									    <option>Killing Things</option>
									    <option>Trying not to be awesome</option>
									</select>
				                </div>
				             </div>

			                <div class="input-container">
			                	<div class="input-group input-container-left">
			                	    <span class="input-group-addon input-group-addon-fix">Effort</span>
			                    	<input type="number" class="form-control input-control" value="40">
			                	</div>
				                <div class="input-group input-container-right">
				                    <span class="input-group-addon input-group-addon-fix">Businessvalue</span>
				                    <input type="number" class="form-control input-control" value="10">
				                </div>
			                </div>
			                <div class="input-container">
				                <div class="input-group input-container-left">
				                	<span class="input-group-addon input-group-addon-fix">Moscow</span>
				                	<select class="form-control input-control">
									    <option>Should</option>
									    <option>Must</option>
									    <option>Could</option>
									    <option>Wont</option>
									</select>
				                </div>
				                <div class="input-group input-container-right">
				                    <span class="input-group-addon input-group-addon-fix">Risk</span>
				                    <input type="number" class="form-control input-control" value="10">
				                </div>
			                </div>
		                    <div class="input-group">
		                        <span class="input-group-addon input-group-addon-fix">Due Date</span>
		                        <input type="date" class="form-control input-control">
		                    </div>
			                <div class="userstory-detail-data">
			                    <div class="modal-data-container">
			                    	Users</br>
			                    	<img class="user-img" alt="err" src="./resources/userPictures/2.png">
			                    	<img class="user-img alt="err" src="./resources/userPictures/1.png">
			                    	<img class="user-img alt="err" src="./resources/userPictures/default.png">
			                    </div>
			                    <div class="modal-data-container">
			                        1h / 3h / 4h
			                        <div class="progressbar">
			                            <div class="progress"></div>
			                        </div>
			                    </div>
			                </div>
			                <div class="userstory-detail-task-container">
					            <div class="userstory-detail-task">
					                <div class="userstory-detail-task-content">
					                    <div class="userstory-detail-task-name">Killing Hibernate</div>
					                    <div class="userstory-detail-task-memberbox">
					                   		<img class="user-img alt="err" src="./resources/userPictures/2.png">
					                    </div>
					                    <div class="userstory-detail-task-time">1h/2h/3h</div>
					                </div>
					            </div>
					            <div class="userstory-detail-task">
					                <div class="userstory-detail-task-content">
					                    <div class="userstory-detail-task-name">Create new Hibernate with Sloths</div>
					                    <div class="userstory-detail-task-memberbox">
					                   		<img class="user-img alt="err" src="./resources/userPictures/2.png">
					                   		<img class="user-img alt="err" src="./resources/userPictures/1.png">
					                   		<img class="user-img alt="err" src="./resources/userPictures/default.png">
					                    </div>
					                    <div class="userstory-detail-task-time">0h/8h/8h</div>
					                </div>
					            </div>					            
			                </div>
			                <div class="modal-footer footer-fix">
			                    <button type="button" class="btn btn-default" type="submit">
			                        <span class="glyphicon glyphicon-save"></span>
			                        Save
			                    </button>
			                </div>
			            </form>
			        </div>
			    </div>
			</div>
		</c:forEach>
	</div>  
<div id="quick-button-container">
    <a class="quick-button" href="./newUserStory.htm">
        <span class="quick-button-title">U</span><span class="quick-button-text">new UserStory</span>
    </a> 
</div>