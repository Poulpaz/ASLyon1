-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 18, 2019 at 10:23 AM
-- Server version: 8.0.13
-- PHP Version: 7.2.10-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aslyon1_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `idEvent` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `idOffer` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `startDate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `endDate` varchar(255) NOT NULL,
  `nbParticipants` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribe_event`
--

CREATE TABLE `subscribe_event` (
  `idSubscribe_event` int(11) NOT NULL,
  `user_idUser` int(11) NOT NULL,
  `event_idEvent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribe_tournament`
--

CREATE TABLE `subscribe_tournament` (
  `idSubscribe_tournament` int(11) NOT NULL,
  `tournament_idTournament` int(11) NOT NULL,
  `team_idTeam` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `idTeam` int(11) NOT NULL,
  `teamName` varchar(255) NOT NULL,
  `user_idUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `teamPlayers`
--

CREATE TABLE `teamPlayers` (
  `idTeamPlayers` int(11) NOT NULL,
  `team_idTeam` int(11) NOT NULL,
  `lastnameSubscriber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `firstnameSubscriber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tournament`
--

CREATE TABLE `tournament` (
  `idTournament` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `nbTeam` int(11) NOT NULL,
  `nbPlayersTeam` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL,
  `token` text,
  `lastname` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `dateOfBirth` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(12) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `token`, `lastname`, `firstname`, `dateOfBirth`, `email`, `password`, `phoneNumber`, `isAdmin`) VALUES
(23, 'cEiiCx48p1M:APA91bEGq_Emb1aGeTTo5s6GWMm2rx2TjDkQIcyCbqHGjGh0L_XVCb51Vg5yNkqdjoxCgUy-x3bR_bYX02cqOVrsYxUzWND6lIMuOOJqaz5B1NbNLzmGTtJjQOV6uBdcl0eVv2tG82Ot', 'Chastagnier', 'Carlos', '15/01/2019 15:45:00', 'carlos.chastagnier@gmail.com', 'p', '0635285635', 1),
(25, 'cXm8KCjJoUo:APA91bH3ZVXxEX8bFa0-rktzIH10bYxMClQfSSFlHIEqgAhYEn7Kp0TcRN_Wmcu-FGRM9MFQg3CFn4VVfpocvhvy0qzWJbk8PkbAhQEofovtqIuReJ6nDVGX418koxyRzbs4OByFJSLy', 'REFFAY', 'Tristan', '04/11/1998 08:54', 'tristan.reffay@gmx.fr', 'jo33b42y', '0786659857', 1),
(26, 'fwkv0UcWpTQ:APA91bH4viS1aTJkaTZ77C9Zr9fAhpQsHBPiPlmBby-n5BvTIJtKovypS3jiAuId9A36WRuvr3bzYdYdobl3VPbXcYU1_mbq5sogtJTKQUphUa0ArCK39lLhQhSugZxZoOa68frg9sKo', 'Benzaied', 'Sofiane', '12/11/2018 10:59', 'sofianebenzaied@gmail.com', '1234', '5464949595', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`idEvent`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`idOffer`);

--
-- Indexes for table `subscribe_event`
--
ALTER TABLE `subscribe_event`
  ADD PRIMARY KEY (`idSubscribe_event`),
  ADD KEY `user_idUser` (`user_idUser`),
  ADD KEY `event_idEvent` (`event_idEvent`);

--
-- Indexes for table `subscribe_tournament`
--
ALTER TABLE `subscribe_tournament`
  ADD PRIMARY KEY (`idSubscribe_tournament`),
  ADD KEY `team_idTeam` (`team_idTeam`),
  ADD KEY `tournament_idTournament` (`tournament_idTournament`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`idTeam`),
  ADD KEY `user_idUser` (`user_idUser`);

--
-- Indexes for table `teamPlayers`
--
ALTER TABLE `teamPlayers`
  ADD PRIMARY KEY (`idTeamPlayers`),
  ADD KEY `team_idTeam` (`team_idTeam`);

--
-- Indexes for table `tournament`
--
ALTER TABLE `tournament`
  ADD PRIMARY KEY (`idTournament`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `idEvent` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `offer`
--
ALTER TABLE `offer`
  MODIFY `idOffer` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscribe_event`
--
ALTER TABLE `subscribe_event`
  MODIFY `idSubscribe_event` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscribe_tournament`
--
ALTER TABLE `subscribe_tournament`
  MODIFY `idSubscribe_tournament` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `team`
--
ALTER TABLE `team`
  MODIFY `idTeam` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `teamPlayers`
--
ALTER TABLE `teamPlayers`
  MODIFY `idTeamPlayers` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tournament`
--
ALTER TABLE `tournament`
  MODIFY `idTournament` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `subscribe_event`
--
ALTER TABLE `subscribe_event`
  ADD CONSTRAINT `subscribe_event_ibfk_1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `subscribe_event_ibfk_2` FOREIGN KEY (`event_idEvent`) REFERENCES `event` (`idevent`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `subscribe_tournament`
--
ALTER TABLE `subscribe_tournament`
  ADD CONSTRAINT `subscribe_tournament_ibfk_1` FOREIGN KEY (`team_idTeam`) REFERENCES `team` (`idteam`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `subscribe_tournament_ibfk_2` FOREIGN KEY (`tournament_idTournament`) REFERENCES `tournament` (`idtournament`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `team_ibfk_1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `teamPlayers`
--
ALTER TABLE `teamPlayers`
  ADD CONSTRAINT `teamPlayers_ibfk_1` FOREIGN KEY (`team_idTeam`) REFERENCES `team` (`idteam`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
