-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Mai 2016 um 14:33
-- Server-Version: 10.1.10-MariaDB
-- PHP-Version: 7.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `scrumiversedb`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `category`
--

CREATE TABLE `category` (
  `Id` int(11) NOT NULL,
  `colorCode` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `category`
--

INSERT INTO `category` (`Id`, `colorCode`, `name`) VALUES
(1, '#5c1818', 'Planung'),
(2, '#c9680e', 'Frontend'),
(3, '#31876d', 'Backend'),
(4, '#bfb0b0', 'Planung');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `est_user_work_time`
--

CREATE TABLE `est_user_work_time` (
  `TaskID` int(11) NOT NULL,
  `WorkTimeInMin` int(11) DEFAULT NULL,
  `UserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `est_user_work_time`
--

INSERT INTO `est_user_work_time` (`TaskID`, `WorkTimeInMin`, `UserID`) VALUES
(1, 0, 2),
(3, 0, 3),
(4, 20, 8),
(5, 120, 4),
(6, 60, 5),
(7, 120, 6),
(8, 240, 6);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `historyentry`
--

CREATE TABLE `historyentry` (
  `HistoryEntryID` int(11) NOT NULL,
  `changeEvent` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `historyentry`
--

INSERT INTO `historyentry` (`HistoryEntryID`, `changeEvent`, `date`, `UserID`) VALUES
(1, 'SPRINT_CREATED', '2016-05-09 15:26:49', 1),
(2, 'SPRINT_UPDATED', '2016-05-09 15:28:04', 1),
(3, 'SPRINT_UPDATED', '2016-05-09 15:28:12', 1),
(4, 'USER_STORY_CREATED', '2016-05-09 15:28:19', 1),
(5, 'USER_STORY_UPDATED', '2016-05-09 15:28:31', 1),
(6, 'USER_STORY_CREATED', '2016-05-09 15:28:32', 1),
(7, 'USER_STORY_UPDATED', '2016-05-09 15:28:48', 1),
(8, 'USER_STORY_UPDATED', '2016-05-09 15:28:57', 1),
(9, 'USER_STORY_CREATED', '2016-05-09 15:29:06', 1),
(11, 'USER_STORY_UPDATED', '2016-05-09 15:29:28', 1),
(12, 'TASK_CREATED', '2016-05-09 15:32:38', 3),
(13, 'TASK_UPDATED', '2016-05-09 15:33:19', 3),
(14, 'TASK_UPDATED', '2016-05-09 15:33:32', 3),
(16, 'TASK_CREATED', '2016-05-09 15:33:57', 3),
(17, 'TASK_UPDATED', '2016-05-09 15:34:31', 3),
(18, 'USER_STORY_ASSIGNED', '2016-05-09 15:34:52', 3),
(19, 'SPRINT_ASSIGNED', '2016-05-09 15:34:52', 3),
(20, 'USER_STORY_UPDATED', '2016-05-09 15:34:52', 3),
(21, 'USER_STORY_ASSIGNED', '2016-05-09 15:35:01', 3),
(22, 'SPRINT_ASSIGNED', '2016-05-09 15:35:01', 3),
(23, 'USER_STORY_UPDATED', '2016-05-09 15:35:01', 3),
(24, 'USER_STORY_ASSIGNED', '2016-05-09 15:35:10', 3),
(25, 'SPRINT_ASSIGNED', '2016-05-09 15:35:10', 3),
(26, 'USER_STORY_UPDATED', '2016-05-09 15:35:10', 3),
(27, 'USER_STORY_UPDATED', '2016-05-09 15:35:19', 3),
(28, 'USER_STORY_UPDATED', '2016-05-09 15:35:30', 3),
(29, 'USER_STORY_UPDATED', '2016-05-09 15:35:50', 3),
(30, 'USER_STORY_UPDATED', '2016-05-09 15:36:18', 3),
(31, 'USER_STORY_UPDATED', '2016-05-09 15:36:30', 3),
(32, 'TASK_UPDATED', '2016-05-09 15:37:33', 3),
(33, 'SPRINT_CREATED', '2016-05-10 13:31:27', 7),
(34, 'SPRINT_CREATED', '2016-05-10 13:31:31', 7),
(35, 'SPRINT_UPDATED', '2016-05-10 13:32:16', 7),
(36, 'SPRINT_UPDATED', '2016-05-10 13:33:14', 7),
(37, 'SPRINT_CREATED', '2016-05-10 13:33:17', 7),
(38, 'SPRINT_UPDATED', '2016-05-10 13:34:19', 7),
(39, 'SPRINT_UPDATED', '2016-05-10 13:34:33', 7),
(40, 'USER_STORY_CREATED', '2016-05-10 13:34:44', 7),
(41, 'USER_STORY_CREATED', '2016-05-10 13:34:48', 7),
(42, 'USER_STORY_CREATED', '2016-05-10 13:34:50', 7),
(43, 'USER_STORY_UPDATED', '2016-05-10 13:35:21', 7),
(44, 'USER_STORY_UPDATED', '2016-05-10 13:35:41', 7),
(45, 'USER_STORY_UPDATED', '2016-05-10 13:35:58', 7),
(46, 'USER_STORY_ASSIGNED', '2016-05-10 13:36:11', 7),
(47, 'SPRINT_ASSIGNED', '2016-05-10 13:36:11', 7),
(48, 'USER_STORY_UPDATED', '2016-05-10 13:36:11', 7),
(49, 'USER_STORY_ASSIGNED', '2016-05-10 13:36:24', 7),
(50, 'SPRINT_ASSIGNED', '2016-05-10 13:36:24', 7),
(51, 'USER_STORY_UPDATED', '2016-05-10 13:36:24', 7),
(52, 'USER_STORY_ASSIGNED', '2016-05-10 13:36:43', 7),
(53, 'SPRINT_ASSIGNED', '2016-05-10 13:36:43', 7),
(54, 'USER_STORY_UPDATED', '2016-05-10 13:36:43', 7),
(55, 'USER_STORY_CREATED', '2016-05-10 13:36:51', 7),
(56, 'USER_STORY_ASSIGNED', '2016-05-10 13:37:31', 7),
(57, 'SPRINT_ASSIGNED', '2016-05-10 13:37:31', 7),
(58, 'USER_STORY_UPDATED', '2016-05-10 13:37:31', 7),
(59, 'USER_STORY_UPDATED', '2016-05-10 13:38:19', 7),
(60, 'USER_STORY_CREATED', '2016-05-10 13:38:27', 7),
(61, 'USER_STORY_ASSIGNED', '2016-05-10 13:39:10', 7),
(62, 'SPRINT_ASSIGNED', '2016-05-10 13:39:10', 7),
(63, 'USER_STORY_UPDATED', '2016-05-10 13:39:10', 7),
(64, 'TASK_CREATED', '2016-05-10 13:40:23', 8),
(65, 'TASK_UPDATED', '2016-05-10 13:40:48', 8),
(66, 'TASK_UPDATED', '2016-05-10 13:40:58', 8),
(67, 'TASK_UPDATED', '2016-05-10 13:41:05', 8),
(68, 'TASK_UPDATED', '2016-05-10 13:42:09', 8),
(69, 'TASK_CREATED', '2016-05-10 13:42:25', 8),
(70, 'TASK_UPDATED', '2016-05-10 13:42:45', 8),
(71, 'TASK_UPDATED', '2016-05-10 13:45:13', 4),
(72, 'TASK_UPDATED', '2016-05-10 13:46:34', 4),
(73, 'TASK_CREATED', '2016-05-10 13:47:02', 5),
(74, 'TASK_UPDATED', '2016-05-10 13:47:27', 5),
(75, 'TASK_UPDATED', '2016-05-10 13:49:14', 5),
(77, 'TASK_CREATED', '2016-05-10 13:51:15', 6),
(78, 'TASK_UPDATED', '2016-05-10 13:52:22', 6),
(79, 'TASK_UPDATED', '2016-05-10 13:53:10', 6),
(80, 'TASK_CREATED', '2016-05-10 13:53:19', 6),
(81, 'TASK_UPDATED', '2016-05-10 13:53:53', 6),
(82, 'TASK_UPDATED', '2016-05-10 13:53:53', 6),
(83, 'TASK_UPDATED', '2016-05-10 13:54:09', 6),
(84, 'TASK_UPDATED', '2016-05-10 13:54:09', 6),
(85, 'USER_STORY_UPDATED', '2016-05-10 14:03:53', 7),
(86, 'USER_STORY_UPDATED', '2016-05-10 14:03:55', 7),
(87, 'USER_STORY_UPDATED', '2016-05-10 14:04:40', 7),
(88, 'USER_STORY_UPDATED', '2016-05-10 14:04:58', 7),
(89, 'USER_STORY_UPDATED', '2016-05-10 14:05:12', 7),
(90, 'USER_STORY_UPDATED', '2016-05-10 14:05:12', 7),
(91, 'USER_STORY_CREATED', '2016-05-10 14:05:29', 7),
(92, 'USER_STORY_ASSIGNED', '2016-05-10 14:06:21', 7),
(93, 'SPRINT_ASSIGNED', '2016-05-10 14:06:21', 7),
(94, 'USER_STORY_UPDATED', '2016-05-10 14:06:21', 7),
(95, 'USER_STORY_REMOVED', '2016-05-10 14:07:25', 7),
(96, 'SPRINT_REMOVED', '2016-05-10 14:07:25', 7),
(97, 'USER_STORY_UPDATED', '2016-05-10 14:07:25', 7),
(98, 'USER_STORY_UPDATED', '2016-05-10 14:07:36', 7);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `message`
--

CREATE TABLE `message` (
  `MessageID` int(11) NOT NULL,
  `content` text,
  `date` datetime DEFAULT NULL,
  `seen` bit(1) NOT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `message_user`
--

CREATE TABLE `message_user` (
  `UserID` int(11) NOT NULL,
  `messageID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notification`
--

CREATE TABLE `notification` (
  `NotificationID` int(11) NOT NULL,
  `changeEvent` int(11) DEFAULT NULL,
  `seen` bit(1) NOT NULL,
  `triggerDescription` varchar(255) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `triggerUser_UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notification`
--

INSERT INTO `notification` (`NotificationID`, `changeEvent`, `seen`, `triggerDescription`, `UserID`, `triggerUser_UserID`) VALUES
(1, 7, b'0', '[US001] Projektdokumentation', 2, 3),
(2, 7, b'0', '[US002] Kostenplanung', 2, 3),
(3, 7, b'0', '[US003] Materialplanung', 2, 3),
(4, 7, b'0', '[US003] Materialplanung', 2, 3),
(5, 7, b'0', '[US003] Materialplanung', 2, 3),
(6, 7, b'0', '[US003] Materialplanung', 2, 3),
(7, 7, b'0', '[US002] Kostenplanung', 2, 3),
(8, 7, b'0', '[US001] Projektdokumentation', 2, 3),
(9, 10, b'0', 'Materialanalyse', 2, 3),
(10, 10, b'0', 'Wände und Hintergrund anzeigen', 7, 6),
(11, 10, b'0', 'Wände und Hintergrund anzeigen', 7, 6),
(12, 10, b'0', 'Objekte eines Raumes anzeigen', 7, 6),
(13, 10, b'0', 'Objekte eines Raumes anzeigen', 7, 6),
(14, 10, b'0', 'Objekte eines Raumes anzeigen', 7, 6),
(15, 10, b'0', 'Objekte eines Raumes anzeigen', 7, 6),
(16, 7, b'0', '[US062] Spielfeld anzeigen', 6, 7),
(17, 7, b'0', '[US062] Spielfeld anzeigen', 6, 7),
(18, 7, b'0', '[US062] Spielfeld anzeigen', 6, 7);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project`
--

CREATE TABLE `project` (
  `ProjectID` int(11) NOT NULL,
  `description` text,
  `dueDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picPath` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `project`
--

INSERT INTO `project` (`ProjectID`, `description`, `dueDate`, `name`, `picPath`) VALUES
(1, 'Einen Fludhaven in Berlin bauen. Möglicht ohne Verspätung!', '2016-08-10 00:00:00', 'Flughafen Berlin', 'resources\\projectPictures\\projectPic_1.jpg'),
(2, 'Ein Rouge-Like Videospiel in 2D mit der Programmiersprache Java entwickeln.', '2016-09-22 00:00:00', 'Wuppi Souls', './resources/projectPictures/default.png'),
(3, 'Endlich die Galaxie erobern, mit einem Todesstern natürlich. Diesmal ohne Luftklappe, versprochen!', '3212-11-20 00:00:00', 'Todesstern v4.2', 'resources\\projectPictures\\projectPic_3.jpg');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projectuser`
--

CREATE TABLE `projectuser` (
  `id` int(11) NOT NULL,
  `role_RoleID` int(11) DEFAULT NULL,
  `user_UserID` int(11) DEFAULT NULL,
  `ProjectID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `projectuser`
--

INSERT INTO `projectuser` (`id`, `role_RoleID`, `user_UserID`, `ProjectID`) VALUES
(1, 2, 1, 1),
(2, 3, 2, 1),
(3, 4, 3, 1),
(4, 7, 7, 2),
(5, 5, 8, 2),
(6, 5, 6, 2),
(7, 5, 4, 2),
(8, 5, 5, 2),
(9, 6, 3, 2),
(10, 10, 3, 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project_category`
--

CREATE TABLE `project_category` (
  `Project_ProjectID` int(11) NOT NULL,
  `categories_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `project_category`
--

INSERT INTO `project_category` (`Project_ProjectID`, `categories_Id`) VALUES
(1, 1),
(2, 2),
(2, 3),
(2, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project_role`
--

CREATE TABLE `project_role` (
  `Project_ProjectID` int(11) NOT NULL,
  `roles_RoleID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `project_role`
--

INSERT INTO `project_role` (`Project_ProjectID`, `roles_RoleID`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 9),
(3, 10),
(3, 11);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `role`
--

CREATE TABLE `role` (
  `RoleID` int(11) NOT NULL,
  `changeable` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `role`
--

INSERT INTO `role` (`RoleID`, `changeable`, `name`) VALUES
(1, b'0', 'Member'),
(2, b'0', 'ProductOwner'),
(3, b'0', 'ScrumMaster'),
(4, b'1', 'Bauverantwortlicher'),
(5, b'0', 'Member'),
(6, b'0', 'ProductOwner'),
(7, b'0', 'ScrumMaster'),
(8, b'1', 'Godmode'),
(9, b'0', 'Member'),
(10, b'0', 'ProductOwner'),
(11, b'0', 'ScrumMaster');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `role_rights`
--

CREATE TABLE `role_rights` (
  `RoleID` int(11) NOT NULL,
  `right_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `role_rights`
--

INSERT INTO `role_rights` (`RoleID`, `right_name`) VALUES
(1, 'Create_Task'),
(1, 'Delete_Task'),
(1, 'Notify_Your_UserStory_Task'),
(1, 'Read_Sprint'),
(1, 'Read_Task'),
(1, 'Read_UserStory'),
(1, 'Update_Task'),
(1, 'View_Review'),
(2, 'Create_Sprint'),
(2, 'Create_UserStory'),
(2, 'Delete_Project'),
(2, 'Delete_Sprint'),
(2, 'Delete_UserStory'),
(2, 'Invite_To_Project'),
(2, 'Read_Sprint'),
(2, 'Read_Task'),
(2, 'Read_UserStory'),
(2, 'Remove_From_Project'),
(2, 'Update_Project'),
(2, 'Update_Sprint'),
(2, 'Update_UserStory'),
(2, 'View_Review'),
(3, 'Notify_PlannedMin_for_Current_Sprint'),
(3, 'Notify_UserStory_Task_for_Current_Sprint'),
(3, 'Read_Sprint'),
(3, 'Read_Task'),
(3, 'Read_UserStory'),
(3, 'View_Review'),
(4, 'Create_Sprint'),
(4, 'Create_Task'),
(4, 'Create_UserStory'),
(4, 'Delete_Project'),
(4, 'Delete_Sprint'),
(4, 'Delete_Task'),
(4, 'Delete_UserStory'),
(4, 'Invite_To_Project'),
(4, 'Notify_PlannedMin_for_Current_Sprint'),
(4, 'Notify_UserStory_Task_for_Current_Sprint'),
(4, 'Notify_Your_UserStory_Task'),
(4, 'Read_Sprint'),
(4, 'Read_Task'),
(4, 'Read_UserStory'),
(4, 'Remove_From_Project'),
(4, 'Update_Project'),
(4, 'Update_Sprint'),
(4, 'Update_Task'),
(4, 'Update_UserStory'),
(5, 'Create_Task'),
(5, 'Delete_Task'),
(5, 'Notify_Your_UserStory_Task'),
(5, 'Read_Sprint'),
(5, 'Read_Task'),
(5, 'Read_UserStory'),
(5, 'Update_Task'),
(5, 'View_Review'),
(6, 'Create_Sprint'),
(6, 'Create_UserStory'),
(6, 'Delete_Project'),
(6, 'Delete_Sprint'),
(6, 'Delete_UserStory'),
(6, 'Invite_To_Project'),
(6, 'Read_Sprint'),
(6, 'Read_Task'),
(6, 'Read_UserStory'),
(6, 'Remove_From_Project'),
(6, 'Update_Project'),
(6, 'Update_Sprint'),
(6, 'Update_UserStory'),
(6, 'View_Review'),
(7, 'Notify_PlannedMin_for_Current_Sprint'),
(7, 'Notify_UserStory_Task_for_Current_Sprint'),
(7, 'Read_Sprint'),
(7, 'Read_Task'),
(7, 'Read_UserStory'),
(7, 'View_Review'),
(8, 'Create_Sprint'),
(8, 'Create_Task'),
(8, 'Create_UserStory'),
(8, 'Delete_Project'),
(8, 'Delete_Sprint'),
(8, 'Delete_Task'),
(8, 'Delete_UserStory'),
(8, 'Invite_To_Project'),
(8, 'Notify_PlannedMin_for_Current_Sprint'),
(8, 'Notify_UserStory_Task_for_Current_Sprint'),
(8, 'Notify_Your_UserStory_Task'),
(8, 'Read_Sprint'),
(8, 'Read_Task'),
(8, 'Read_UserStory'),
(8, 'Remove_From_Project'),
(8, 'Update_Project'),
(8, 'Update_Sprint'),
(8, 'Update_Task'),
(8, 'Update_UserStory'),
(9, 'Create_Task'),
(9, 'Delete_Task'),
(9, 'Notify_Your_UserStory_Task'),
(9, 'Read_Sprint'),
(9, 'Read_Task'),
(9, 'Read_UserStory'),
(9, 'Update_Task'),
(9, 'View_Review'),
(10, 'Create_Sprint'),
(10, 'Create_UserStory'),
(10, 'Delete_Project'),
(10, 'Delete_Sprint'),
(10, 'Delete_UserStory'),
(10, 'Invite_To_Project'),
(10, 'Read_Sprint'),
(10, 'Read_Task'),
(10, 'Read_UserStory'),
(10, 'Remove_From_Project'),
(10, 'Update_Project'),
(10, 'Update_Sprint'),
(10, 'Update_UserStory'),
(10, 'View_Review'),
(11, 'Notify_PlannedMin_for_Current_Sprint'),
(11, 'Notify_UserStory_Task_for_Current_Sprint'),
(11, 'Read_Sprint'),
(11, 'Read_Task'),
(11, 'Read_UserStory'),
(11, 'View_Review');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sprint`
--

CREATE TABLE `sprint` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `planState` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `ProjectID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `sprint`
--

INSERT INTO `sprint` (`ID`, `acceptanceCriteria`, `description`, `planState`, `endDate`, `startDate`, `ProjectID`) VALUES
(1, 'Folgende Dokumente erstellt:\r\n- Dokumentation\r\n- Projektablaufplan\r\n- Projektstrukturplan', '[S001] Planungsphase', 'InProgress', '2016-06-09 00:00:00', '2016-05-09 00:00:00', 1),
(2, 'Planung des Proektes abgeschlossen.', '[S001] Projektplanung', 'Done', '2016-05-10 00:00:00', '2016-04-10 00:00:00', 2),
(3, 'Grundgerüst mit wenigen Assets.', '[S002] Kernimplementation', 'InProgress', '2016-06-10 00:00:00', '2016-05-10 00:00:00', 2),
(4, 'Grafisches Design und erweiterte Features (Lootsystem, Combatsystem)', '[S003] Featureimplementation', 'Planning', '2016-07-10 00:00:00', '2016-06-10 00:00:00', 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sprint_historyentry`
--

CREATE TABLE `sprint_historyentry` (
  `Sprint_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `sprint_historyentry`
--

INSERT INTO `sprint_historyentry` (`Sprint_ID`, `history_HistoryEntryID`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 18),
(1, 21),
(1, 24),
(2, 33),
(2, 35),
(2, 39),
(2, 56),
(2, 61),
(3, 34),
(3, 36),
(3, 46),
(3, 52),
(3, 92),
(3, 95),
(4, 37),
(4, 38),
(4, 49);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task`
--

CREATE TABLE `task` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `planState` varchar(255) DEFAULT NULL,
  `UserStoryID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `task`
--

INSERT INTO `task` (`ID`, `acceptanceCriteria`, `description`, `planState`, `UserStoryID`) VALUES
(1, '', 'Projektablaufplan erstellen', 'InProgress', 3),
(3, '', 'Materialanalyse', 'InProgress', 1),
(4, '', 'Dokument erstellen', 'Done', 7),
(5, '', 'Plan erstellen', 'Done', 7),
(6, '', 'Konkurrenzsuche', 'Done', 8),
(7, '', 'Wände und Hintergrund anzeigen', 'Done', 4),
(8, '', 'Objekte eines Raumes anzeigen', 'InProgress', 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task_historyentry`
--

CREATE TABLE `task_historyentry` (
  `Task_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `task_historyentry`
--

INSERT INTO `task_historyentry` (`Task_ID`, `history_HistoryEntryID`) VALUES
(1, 12),
(1, 13),
(1, 14),
(3, 16),
(3, 17),
(3, 32),
(4, 64),
(4, 65),
(4, 66),
(4, 67),
(4, 68),
(5, 69),
(5, 70),
(5, 71),
(5, 72),
(6, 73),
(6, 74),
(6, 75),
(7, 77),
(7, 78),
(7, 79),
(8, 80),
(8, 81),
(8, 82),
(8, 83),
(8, 84);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `task_tags`
--

CREATE TABLE `task_tags` (
  `TaskID` int(11) NOT NULL,
  `Tag` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `task_tags`
--

INSERT INTO `task_tags` (`TaskID`, `Tag`) VALUES
(4, 'Planung');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `emailNotification` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profileImagePath` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`UserID`, `email`, `emailNotification`, `name`, `password`, `profileImagePath`) VALUES
(1, 'manfred.mammut@web.de', b'0', 'Manfred Mammut', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(2, 'heinrich.hase@web.de', b'0', 'Heinrich Hase', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(3, 'peter.panther@web.de', b'0', 'Peter Panther', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(4, 'toni.serfling@web.de', b'0', 'Toni Serfling', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(5, 'joshua.ward@web.de', b'0', 'Joshua Ward', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(6, 'lasse.jacobs@web.de', b'0', 'Lasse Jacobs', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(7, 'kevin.jolitz@web.de', b'0', 'Kevin Jolitz', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png'),
(8, 'kevin.wesseler@web.de', b'0', 'Kevin Wesseler', '46E44AA0BC21D8A826D79344DF38BE4B', './resources/userPictures/default.png');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userstory`
--

CREATE TABLE `userstory` (
  `ID` int(11) NOT NULL,
  `acceptanceCriteria` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `planState` varchar(255) DEFAULT NULL,
  `businessValue` int(11) NOT NULL,
  `dueDate` datetime DEFAULT NULL,
  `effortValue` int(11) NOT NULL,
  `moscow` int(11) DEFAULT NULL,
  `risk` int(11) NOT NULL,
  `category_Id` int(11) DEFAULT NULL,
  `SprintID` int(11) DEFAULT NULL,
  `ProjectID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `userstory`
--

INSERT INTO `userstory` (`ID`, `acceptanceCriteria`, `description`, `planState`, `businessValue`, `dueDate`, `effortValue`, `moscow`, `risk`, `category_Id`, `SprintID`, `ProjectID`) VALUES
(1, '', '[US003] Materialplanung', 'Planning', 30, '2016-05-09 00:00:00', 20, 0, 50, 1, 1, 1),
(2, '', '[US002] Kostenplanung', 'Planning', 30, '2016-05-09 00:00:00', 40, 2, 50, 1, 1, 1),
(3, '', '[US001] Projektdokumentation', 'Planning', 40, '2016-05-09 00:00:00', 100, 2, 20, 1, 1, 1),
(4, '', '[US062] Spielfeld anzeigen', 'InProgress', 100, '2016-06-10 00:00:00', 60, 2, 10, 2, 3, 2),
(5, '', '[US064] Minimap anzeigen', 'Planning', 0, '2016-07-10 00:00:00', 0, 2, 0, 2, 4, 2),
(6, '', '[US063] HUD anzeigen', 'Planning', 0, '2016-07-10 00:00:00', 0, 2, 0, 2, 3, 2),
(7, '', '[US020] Projektablaufplan erstellen', 'Done', 50, '2016-05-10 00:00:00', 20, 0, 10, 4, 2, 2),
(8, '', '[US021] Konkurrenzanalyse', 'Done', 30, '2016-05-10 00:00:00', 10, 1, 20, 4, 2, 2),
(10, '', '[US058] Itemgenerierung', 'Planning', 100, '2016-05-20 00:00:00', 50, 0, 60, 3, NULL, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userstory_historyentry`
--

CREATE TABLE `userstory_historyentry` (
  `UserStory_ID` int(11) NOT NULL,
  `history_HistoryEntryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `userstory_historyentry`
--

INSERT INTO `userstory_historyentry` (`UserStory_ID`, `history_HistoryEntryID`) VALUES
(1, 4),
(1, 5),
(1, 8),
(1, 25),
(1, 26),
(1, 27),
(1, 28),
(1, 29),
(2, 6),
(2, 7),
(2, 22),
(2, 23),
(2, 30),
(3, 9),
(3, 11),
(3, 19),
(3, 20),
(3, 31),
(4, 40),
(4, 43),
(4, 53),
(4, 54),
(4, 85),
(4, 86),
(4, 87),
(5, 41),
(5, 45),
(5, 50),
(5, 51),
(5, 89),
(5, 90),
(6, 42),
(6, 44),
(6, 47),
(6, 48),
(6, 88),
(7, 55),
(7, 57),
(7, 58),
(7, 59),
(8, 60),
(8, 62),
(8, 63),
(10, 91),
(10, 93),
(10, 94),
(10, 96),
(10, 97),
(10, 98);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_project`
--

CREATE TABLE `user_project` (
  `User_UserID` int(11) NOT NULL,
  `projects_ProjectID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user_project`
--

INSERT INTO `user_project` (`User_UserID`, `projects_ProjectID`) VALUES
(1, 1),
(2, 1),
(3, 1),
(3, 2),
(3, 3),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `worklog`
--

CREATE TABLE `worklog` (
  `LogId` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `logText` text,
  `loggedMinutes` int(11) NOT NULL,
  `user_UserID` int(11) DEFAULT NULL,
  `TaskID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `worklog`
--

INSERT INTO `worklog` (`LogId`, `date`, `logText`, `loggedMinutes`, `user_UserID`, `TaskID`) VALUES
(1, '2016-05-09 00:00:00', 'Material gesichtet', 240, 3, 3),
(2, '2016-05-02 00:00:00', 'Dokument erstellt', 17, 8, NULL),
(3, '2016-05-03 00:00:00', 'Plan grob erstellt', 90, 4, NULL),
(4, '2016-05-07 00:00:00', 'Plan vollendet.', 60, 4, NULL),
(5, '2016-04-28 00:00:00', 'Konkurrenz zusammengefasst', 40, 5, NULL),
(6, '2016-05-12 00:00:00', 'Anzeige programmiert.', 300, 6, NULL),
(7, '2016-05-14 00:00:00', 'Spieler angezeigt', 20, 6, 8);

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
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT für Tabelle `historyentry`
--
ALTER TABLE `historyentry`
  MODIFY `HistoryEntryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;
--
-- AUTO_INCREMENT für Tabelle `message`
--
ALTER TABLE `message`
  MODIFY `MessageID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `notification`
--
ALTER TABLE `notification`
  MODIFY `NotificationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT für Tabelle `project`
--
ALTER TABLE `project`
  MODIFY `ProjectID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `projectuser`
--
ALTER TABLE `projectuser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT für Tabelle `role`
--
ALTER TABLE `role`
  MODIFY `RoleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT für Tabelle `sprint`
--
ALTER TABLE `sprint`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT für Tabelle `task`
--
ALTER TABLE `task`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT für Tabelle `userstory`
--
ALTER TABLE `userstory`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT für Tabelle `worklog`
--
ALTER TABLE `worklog`
  MODIFY `LogId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
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
