<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
    <table class="taskboard-heading">
        <tr>
            <th class="userstory-section">USERSTORY</th>
            <th class="task-section">PLANED</th>
            <th class="task-section">TO-DO</th>
            <th class="task-section">DONE</th>
        </tr>
    </table>
</div>
<table class="taskboard">
    <tr>
        <td class="userstory-section item-section">
            <div class="userstory">
                <div id="aktive" class="userstory-state"></div>
                <div class="userstory-content">
                    <div class="userstory-titel">[US001] SUPER DUPER TOLLE USER STORY</div>
                    <div class="userstory-stats">
                        <div class="time-container">
                            <div class="sandclock"></div>
                            <div class="duetime">TIME</div>
                        </div>
                        <div class="info-container">
                            <div class="moscow">M</div>
                            <div class="value">#</div>
                            <div class="risk">###</div>
                            <div class="timestats">###/###/###</div>
                        </div>
                        <div class="effort">###</div>
                        <div class="member-container">
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                            <div class="userstory-member"></div>
                        </div>
                    </div>
                </div>
                <a id="aktive" class="glyphicon glyphicon-triangle-right userstory-link" href="#"></a>
            </div>
        </td>
        <td class="task-section item-section">
            <div class="task">
                <div class="task-content">
                    <div class="task-name">TASK NAME SUPER DUPER</div>
                    <div class="task-time">### / ### / ###</div>
                    <div class="task-memberbox">
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                        <div class="task-member"></div>
                    </div>
                </div>
                <a href="#" class="glyphicon glyphicon-triangle-right task-link"></a>
            </div>
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
            <div class="task"></div>
        </td>
        <td class="task-section"></td>
        <td class="task-section"></td>
    </tr>
    <tr>
        <td class="userstory-section item-section">
            <div class="userstory"></div>
        </td>
        <td class="task-section"></td>
        <td class="task-section"></td>
        <td class="task-section"></td>
    </tr>
</table>