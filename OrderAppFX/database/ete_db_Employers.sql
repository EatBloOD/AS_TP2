CREATE DATABASE  IF NOT EXISTS `ete_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ete_db`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: ete_db
-- ------------------------------------------------------
-- Server version	5.7.17

DROP TABLE IF EXISTS `Employers`;

CREATE TABLE `Employers` (
  `idEmployers` int(11) NOT NULL AUTO_INCREMENT,
  `employers_Name` varchar(100) NOT NULL,
  `employers_Password` varchar(100) NOT NULL,
  `employers_Location` varchar(250) NOT NULL,
  `employers_Telephone` varchar(15) NOT NULL,
  `employers_Email` varchar(100) NOT NULL,
  PRIMARY KEY (`idEmployers`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `Employers`
--

LOCK TABLES `Employers` WRITE;
/*!40000 ALTER TABLE `Employers` DISABLE KEYS */;
/*!40000 ALTER TABLE `Employers` ENABLE KEYS */;
UNLOCK TABLES;


-- Dump completed on 2017-02-16 18:54:05
