/*
 Navicat Premium Data Transfer

 Source Server         : MariaDB
 Source Server Type    : MariaDB
 Source Server Version : 110502 (11.5.2-MariaDB-ubu2404)
 Source Host           : localhost:3306
 Source Schema         : lab05

 Target Server Type    : MariaDB
 Target Server Version : 110502 (11.5.2-MariaDB-ubu2404)
 File Encoding         : 65001

 Date: 13/10/2024 12:15:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for candidates
-- ----------------------------
DROP TABLE IF EXISTS `candidates`;
CREATE TABLE `candidates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) DEFAULT NULL,
  `middle_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `candidates_pk_2` (`email`),
  UNIQUE KEY `candidates_pk_3` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- ----------------------------
-- Table structure for job_skills
-- ----------------------------
DROP TABLE IF EXISTS `job_skills`;
CREATE TABLE `job_skills` (
  `job_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`job_id`,`skill_id`),
  KEY `skill_job` (`skill_id`),
  CONSTRAINT `job_skill` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_job` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- ----------------------------
-- Table structure for jobs
-- ----------------------------
DROP TABLE IF EXISTS `jobs`;
CREATE TABLE `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- ----------------------------
-- Table structure for skill_candidates
-- ----------------------------
DROP TABLE IF EXISTS `skill_candidates`;
CREATE TABLE `skill_candidates` (
  `candidate_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`candidate_id`,`skill_id`),
  KEY `skill_candidate` (`skill_id`),
  CONSTRAINT `candidate_skill` FOREIGN KEY (`candidate_id`) REFERENCES `candidates` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_candidate` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- ----------------------------
-- Table structure for skills
-- ----------------------------
DROP TABLE IF EXISTS `skills`;
CREATE TABLE `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(30) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `field` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
