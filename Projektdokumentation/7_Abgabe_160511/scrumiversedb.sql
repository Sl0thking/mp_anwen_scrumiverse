--
-- Datenbank: `scrumiversedb`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `category`
--

CREATE TABLE `category` (
  `Id` int(11) NOT NULL,
  `colorCode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `est_user_work_time`
--

CREATE TABLE `est_user_work_time` (
  `TaskID` int(11) NOT NULL,
  `WorkTimeInMin` int(11) DEFAULT NULL,
  `UserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `historyentry`
--

CREATE TABLE `historyentry` (
  `HistoryEntryID` int(11) NOT NULL,
  `changeEvent` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `message`
--

CREATE TABLE `message` (
  `MessageID` int(11) NOT NULL,
  `content` text COLLATE utf8_bin,
  `date` datetime DEFAULT NULL,
  `seen` bit(1) NOT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `message_user`
--

CREATE TABLE `message_user` (
  `UserID` int(11) NOT NULL,
  `messageID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notification`
--

CREATE TABLE `notification` (
  `NotificationID` int(11) NOT NULL,
  `changeEvent` int(11) DEFAULT NULL,
  `seen` bit(1) NOT NULL,
  `triggerDescription` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `triggerUser_UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project`
--

CREATE TABLE `project` (
  `ProjectID` int(11) NOT NULL,
  `description` text COLLATE utf8_bin,
  `dueDate` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `picPath` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projectuser`
--

CREATE TABLE `projectuser` (
  `id` int(11) NOT NULL,
  `role_RoleID` int(11) DEFAULT NULL,
  `user_UserID` int(11) DEFAULT NULL,
  `ProjectID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project_category`
--

CREATE TABLE `project_category` (
  `Project_ProjectID` int(11) NOT NULL,
  `categories_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project_role`
--

CREATE TABLE `project_role` (
  `Project_ProjectID` int(11) NOT NULL,
  `roles_RoleID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `role`
--

CREATE TABLE `role` (
  `RoleID` int(11) NOT NULL,
  `changeable` bit(1) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `role_rights`
--

CREATE TABLE `role_rights` (
  `RoleID` int(11) NOT NULL,
  `right_name` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sprint`
--

CREATE TABLE `sprint` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `planState` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `ProjectID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sprint_historyentry`
--

CREATE TABLE `sprint_historyentry` (
  `Sprint_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task`
--

CREATE TABLE `task` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `planState` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UserStoryID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task_historyentry`
--

CREATE TABLE `task_historyentry` (
  `Task_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task_tags`
--

CREATE TABLE `task_tags` (
  `TaskID` int(11) NOT NULL,
  `Tag` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `emailNotification` bit(1) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profileImagePath` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userstory`
--

CREATE TABLE `userstory` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `planState` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `businessValue` int(11) NOT NULL,
  `dueDate` datetime DEFAULT NULL,
  `effortValue` int(11) NOT NULL,
  `moscow` int(11) DEFAULT NULL,
  `risk` int(11) NOT NULL,
  `category_Id` int(11) DEFAULT NULL,
  `SprintID` int(11) DEFAULT NULL,
  `ProjectID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userstory_historyentry`
--

CREATE TABLE `userstory_historyentry` (
  `UserStory_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_project`
--

CREATE TABLE `user_project` (
  `User_UserID` int(11) NOT NULL,
  `projects_ProjectID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `worklog`
--

CREATE TABLE `worklog` (
  `LogId` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `logText` text COLLATE utf8_bin,
  `loggedMinutes` int(11) NOT NULL,
  `user_UserID` int(11) DEFAULT NULL,
  `TaskID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Id`);

--
-- Indizes für die Tabelle `est_user_work_time`
--
ALTER TABLE `est_user_work_time`
  ADD PRIMARY KEY (`TaskID`,`UserID`),
  ADD KEY `FKF6C9D3A06E0BB3AC` (`UserID`),
  ADD KEY `FKF6C9D3A05B1332D4` (`TaskID`);

--
-- Indizes für die Tabelle `historyentry`
--
ALTER TABLE `historyentry`
  ADD PRIMARY KEY (`HistoryEntryID`),
  ADD KEY `FK9367445E6E0BB3AC` (`UserID`);

--
-- Indizes für die Tabelle `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`MessageID`),
  ADD KEY `FK9C2397E76E0BB3AC` (`UserID`);

--
-- Indizes für die Tabelle `message_user`
--
ALTER TABLE `message_user`
  ADD PRIMARY KEY (`UserID`,`messageID`),
  ADD KEY `FKB3589103439A8BB8` (`messageID`),
  ADD KEY `FKB35891036E0BB3AC` (`UserID`);

--
-- Indizes für die Tabelle `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`NotificationID`),
  ADD KEY `FK2D45DD0BAB2961E8` (`triggerUser_UserID`),
  ADD KEY `FK2D45DD0B6E0BB3AC` (`UserID`);

--
-- Indizes für die Tabelle `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`ProjectID`);

--
-- Indizes für die Tabelle `projectuser`
--
ALTER TABLE `projectuser`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2B651E64CC8B8A2B` (`role_RoleID`),
  ADD KEY `FK2B651E6487B77EBE` (`ProjectID`),
  ADD KEY `FK2B651E649A44FAA0` (`user_UserID`);

--
-- Indizes für die Tabelle `project_category`
--
ALTER TABLE `project_category`
  ADD PRIMARY KEY (`Project_ProjectID`,`categories_Id`),
  ADD UNIQUE KEY `categories_Id` (`categories_Id`),
  ADD KEY `FK8DC6B7A4F33C7918` (`Project_ProjectID`),
  ADD KEY `FK8DC6B7A4AC4408AD` (`categories_Id`);

--
-- Indizes für die Tabelle `project_role`
--
ALTER TABLE `project_role`
  ADD PRIMARY KEY (`Project_ProjectID`,`roles_RoleID`),
  ADD UNIQUE KEY `roles_RoleID` (`roles_RoleID`),
  ADD KEY `FK41BCBE1CF33C7918` (`Project_ProjectID`),
  ADD KEY `FK41BCBE1C3E8E2E04` (`roles_RoleID`);

--
-- Indizes für die Tabelle `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indizes für die Tabelle `role_rights`
--
ALTER TABLE `role_rights`
  ADD PRIMARY KEY (`RoleID`,`right_name`),
  ADD KEY `FK8FBF55A068B65E42` (`RoleID`);

--
-- Indizes für die Tabelle `sprint`
--
ALTER TABLE `sprint`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK9401EE3A87B77EBE` (`ProjectID`);

--
-- Indizes für die Tabelle `sprint_historyentry`
--
ALTER TABLE `sprint_historyentry`
  ADD PRIMARY KEY (`Sprint_ID`,`history_HistoryEntryID`),
  ADD UNIQUE KEY `history_HistoryEntryID` (`history_HistoryEntryID`),
  ADD KEY `FK29572E4363698253` (`history_HistoryEntryID`),
  ADD KEY `FK29572E434FD1CBA9` (`Sprint_ID`);

--
-- Indizes für die Tabelle `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK27A9A513324000` (`UserStoryID`);

--
-- Indizes für die Tabelle `task_historyentry`
--
ALTER TABLE `task_historyentry`
  ADD PRIMARY KEY (`Task_ID`,`history_HistoryEntryID`),
  ADD UNIQUE KEY `history_HistoryEntryID` (`history_HistoryEntryID`),
  ADD KEY `FKCB4C98B863698253` (`history_HistoryEntryID`),
  ADD KEY `FKCB4C98B833F3CF89` (`Task_ID`);

--
-- Indizes für die Tabelle `task_tags`
--
ALTER TABLE `task_tags`
  ADD PRIMARY KEY (`TaskID`,`Tag`),
  ADD KEY `FK822BF6935B1332D4` (`TaskID`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Indizes für die Tabelle `userstory`
--
ALTER TABLE `userstory`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK8B05E3CA91FDBAFE` (`SprintID`),
  ADD KEY `FK8B05E3CAEB38E8B` (`category_Id`),
  ADD KEY `FK8B05E3CA87B77EBE` (`ProjectID`);

--
-- Indizes für die Tabelle `userstory_historyentry`
--
ALTER TABLE `userstory_historyentry`
  ADD PRIMARY KEY (`UserStory_ID`,`history_HistoryEntryID`),
  ADD UNIQUE KEY `history_HistoryEntryID` (`history_HistoryEntryID`),
  ADD KEY `FK3DEAC2B363698253` (`history_HistoryEntryID`),
  ADD KEY `FK3DEAC2B32D7B22CB` (`UserStory_ID`);

--
-- Indizes für die Tabelle `user_project`
--
ALTER TABLE `user_project`
  ADD PRIMARY KEY (`User_UserID`,`projects_ProjectID`),
  ADD KEY `FKB583CEA5D5F0C139` (`projects_ProjectID`),
  ADD KEY `FKB583CEA59A44FAA0` (`User_UserID`);

--
-- Indizes für die Tabelle `worklog`
--
ALTER TABLE `worklog`
  ADD PRIMARY KEY (`LogId`),
  ADD KEY `FKBE2056539A44FAA0` (`user_UserID`),
  ADD KEY `FKBE2056535B1332D4` (`TaskID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `category`
--
ALTER TABLE `category`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `historyentry`
--
ALTER TABLE `historyentry`
  MODIFY `HistoryEntryID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `message`
--
ALTER TABLE `message`
  MODIFY `MessageID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `notification`
--
ALTER TABLE `notification`
  MODIFY `NotificationID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `project`
--
ALTER TABLE `project`
  MODIFY `ProjectID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `projectuser`
--
ALTER TABLE `projectuser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `role`
--
ALTER TABLE `role`
  MODIFY `RoleID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `sprint`
--
ALTER TABLE `sprint`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `task`
--
ALTER TABLE `task`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `userstory`
--
ALTER TABLE `userstory`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `worklog`
--
ALTER TABLE `worklog`
  MODIFY `LogId` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `est_user_work_time`
--
ALTER TABLE `est_user_work_time`
  ADD CONSTRAINT `FKF6C9D3A05B1332D4` FOREIGN KEY (`TaskID`) REFERENCES `task` (`ID`),
  ADD CONSTRAINT `FKF6C9D3A06E0BB3AC` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints der Tabelle `historyentry`
--
ALTER TABLE `historyentry`
  ADD CONSTRAINT `FK9367445E6E0BB3AC` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints der Tabelle `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK9C2397E76E0BB3AC` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints der Tabelle `message_user`
--
ALTER TABLE `message_user`
  ADD CONSTRAINT `FKB3589103439A8BB8` FOREIGN KEY (`messageID`) REFERENCES `message` (`MessageID`),
  ADD CONSTRAINT `FKB35891036E0BB3AC` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints der Tabelle `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK2D45DD0B6E0BB3AC` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `FK2D45DD0BAB2961E8` FOREIGN KEY (`triggerUser_UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints der Tabelle `projectuser`
--
ALTER TABLE `projectuser`
  ADD CONSTRAINT `FK2B651E6487B77EBE` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`),
  ADD CONSTRAINT `FK2B651E649A44FAA0` FOREIGN KEY (`user_UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `FK2B651E64CC8B8A2B` FOREIGN KEY (`role_RoleID`) REFERENCES `role` (`RoleID`);

--
-- Constraints der Tabelle `project_category`
--
ALTER TABLE `project_category`
  ADD CONSTRAINT `FK8DC6B7A4AC4408AD` FOREIGN KEY (`categories_Id`) REFERENCES `category` (`Id`),
  ADD CONSTRAINT `FK8DC6B7A4F33C7918` FOREIGN KEY (`Project_ProjectID`) REFERENCES `project` (`ProjectID`);

--
-- Constraints der Tabelle `project_role`
--
ALTER TABLE `project_role`
  ADD CONSTRAINT `FK41BCBE1C3E8E2E04` FOREIGN KEY (`roles_RoleID`) REFERENCES `role` (`RoleID`),
  ADD CONSTRAINT `FK41BCBE1CF33C7918` FOREIGN KEY (`Project_ProjectID`) REFERENCES `project` (`ProjectID`);

--
-- Constraints der Tabelle `role_rights`
--
ALTER TABLE `role_rights`
  ADD CONSTRAINT `FK8FBF55A068B65E42` FOREIGN KEY (`RoleID`) REFERENCES `role` (`RoleID`);

--
-- Constraints der Tabelle `sprint`
--
ALTER TABLE `sprint`
  ADD CONSTRAINT `FK9401EE3A87B77EBE` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`);

--
-- Constraints der Tabelle `sprint_historyentry`
--
ALTER TABLE `sprint_historyentry`
  ADD CONSTRAINT `FK29572E434FD1CBA9` FOREIGN KEY (`Sprint_ID`) REFERENCES `sprint` (`ID`),
  ADD CONSTRAINT `FK29572E4363698253` FOREIGN KEY (`history_HistoryEntryID`) REFERENCES `historyentry` (`HistoryEntryID`);

--
-- Constraints der Tabelle `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `FK27A9A513324000` FOREIGN KEY (`UserStoryID`) REFERENCES `userstory` (`ID`);

--
-- Constraints der Tabelle `task_historyentry`
--
ALTER TABLE `task_historyentry`
  ADD CONSTRAINT `FKCB4C98B833F3CF89` FOREIGN KEY (`Task_ID`) REFERENCES `task` (`ID`),
  ADD CONSTRAINT `FKCB4C98B863698253` FOREIGN KEY (`history_HistoryEntryID`) REFERENCES `historyentry` (`HistoryEntryID`);

--
-- Constraints der Tabelle `task_tags`
--
ALTER TABLE `task_tags`
  ADD CONSTRAINT `FK822BF6935B1332D4` FOREIGN KEY (`TaskID`) REFERENCES `task` (`ID`);

--
-- Constraints der Tabelle `userstory`
--
ALTER TABLE `userstory`
  ADD CONSTRAINT `FK8B05E3CA87B77EBE` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`),
  ADD CONSTRAINT `FK8B05E3CA91FDBAFE` FOREIGN KEY (`SprintID`) REFERENCES `sprint` (`ID`),
  ADD CONSTRAINT `FK8B05E3CAEB38E8B` FOREIGN KEY (`category_Id`) REFERENCES `category` (`Id`);

--
-- Constraints der Tabelle `userstory_historyentry`
--
ALTER TABLE `userstory_historyentry`
  ADD CONSTRAINT `FK3DEAC2B32D7B22CB` FOREIGN KEY (`UserStory_ID`) REFERENCES `userstory` (`ID`),
  ADD CONSTRAINT `FK3DEAC2B363698253` FOREIGN KEY (`history_HistoryEntryID`) REFERENCES `historyentry` (`HistoryEntryID`);

--
-- Constraints der Tabelle `user_project`
--
ALTER TABLE `user_project`
  ADD CONSTRAINT `FKB583CEA59A44FAA0` FOREIGN KEY (`User_UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `FKB583CEA5D5F0C139` FOREIGN KEY (`projects_ProjectID`) REFERENCES `project` (`ProjectID`);

--
-- Constraints der Tabelle `worklog`
--
ALTER TABLE `worklog`
  ADD CONSTRAINT `FKBE2056535B1332D4` FOREIGN KEY (`TaskID`) REFERENCES `task` (`ID`),
  ADD CONSTRAINT `FKBE2056539A44FAA0` FOREIGN KEY (`user_UserID`) REFERENCES `user` (`UserID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
