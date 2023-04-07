-- MySQL dump 10.13  Distrib 5.7.34, for Win64 (x86_64)
--
-- Host: 3.34.191.232    Database: B101
-- ------------------------------------------------------
-- Server version	5.5.5-10.5.10-MariaDB-1:10.5.10+maria~bionic

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `personal_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7rxjg2wsysqv910tt3h6r24jl` (`personal_info_id`),
  CONSTRAINT `FK7rxjg2wsysqv910tt3h6r24jl` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (30,'교육 이수','뿌루루루','쀼쀼쀼류','\"2021-10-19T15:00:00.000Z\" ~ \"2021-10-22T15:00:00.000Z\"',35,33),(36,'정규직','테스트','테스트','\"2021-10-11T15:00:00.000Z\" ~ \"2021-10-29T15:00:00.000Z\"',27,25);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `certificate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acquisition_date` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `sortation` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `personal_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtby12dgr79njy4saopi1r01lj` (`personal_info_id`),
  CONSTRAINT `FKtby12dgr79njy4saopi1r01lj` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (8,'2021-10-11T15:00:00.000Z','ㄴㅇㅀ','1','3',35,33);
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `final_education`
--

DROP TABLE IF EXISTS `final_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final_education` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grades` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sortation` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `personal_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb1gcglq099ld8j6qlbu2h1e69` (`personal_info_id`),
  CONSTRAINT `FKb1gcglq099ld8j6qlbu2h1e69` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_education`
--

LOCK TABLES `final_education` WRITE;
/*!40000 ALTER TABLE `final_education` DISABLE KEYS */;
INSERT INTO `final_education` VALUES (1,'4.0','서울대학교','대학교',1,1),(2,'3 / 4.5','서울대학교 물리학과','대학교',5,3),(3,'4.5/4.5','명지대학교 자연캠퍼스 경영정보과','대학교',6,4),(11,'3.8 / 4.5','남서울대학교 천문학과','대학교',2,2),(23,'1.7 / 4.5','경기대학교 물리교육과','대학교',27,25),(24,'0.0 / undefined','undefined undefined','',35,33);
/*!40000 ALTER TABLE `final_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery`
--

DROP TABLE IF EXISTS `gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_path` text DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `sid` bigint(20) DEFAULT NULL,
  `sortation` varchar(255) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery`
--

LOCK TABLES `gallery` WRITE;
/*!40000 ALTER TABLE `gallery` DISABLE KEYS */;
INSERT INTO `gallery` VALUES (1,'test.PNG-20211006001014',1,1,'edu','테스트'),(2,'test.PNG-20214206014259',1,1,'act','test'),(4,'test.PNG-20210406160402',1,1,'edu','테스t'),(5,'test.PNG-20210807180813',1,1,'1','844442992f6f4eaf6ecb4e4730f07d351cfc89c477a02b08d60430cbd4e6fa53'),(24,'조웅현.jpg-20210408040442',25,NULL,'prop','해당사항없음'),(30,'정세원.png-20211408051455',33,NULL,'prop','해당사항없음'),(32,'logo2.png-20215908045959',33,30,'act','c9a926390d0e7229ac4cd6cb0050504c5e562de8ef0efa7009baef32a839f942'),(33,'blocket_main.PNG-20210008050022',33,8,'cert','28ded2657f10abbe1c1ca66a38b415f21c2e600a5173b2fd2f923a376461e93d'),(34,'system_architecture.png-20211408051401',33,24,'edu','55ab47c6b1b3deb20564ed75b7de2d84d3c738707ad211671aac8b046f79475c'),(59,'image2.jpg-20215008065032',25,23,'edu','0e551e85362776c23f110699f76c7873f3056470e5c59db8a6ee9c3f326ad53e'),(63,'image2.jpg-20212608082629',25,36,'act','0e551e85362776c23f110699f76c7873f3056470e5c59db8a6ee9c3f326ad53e');
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_info`
--

DROP TABLE IF EXISTS `personal_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `date_birth` date DEFAULT NULL,
  `disabled` varchar(255) DEFAULT NULL,
  `english_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `military_service` varchar(255) DEFAULT NULL,
  `veterans_affairs` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9lk7kc3eams2ytqcpiu72h0fw` (`user_id`),
  CONSTRAINT `FK9lk7kc3eams2ytqcpiu72h0fw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_info`
--

LOCK TABLES `personal_info` WRITE;
/*!40000 ALTER TABLE `personal_info` DISABLE KEYS */;
INSERT INTO `personal_info` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,'대전광역시 서구 월평동로 83','2021-10-20',NULL,'SSAFY, Kim','여성',NULL,NULL,2),(3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6),(25,'대전광역시 서구 관저북로 14 (원앙마을 4단지)','2021-10-07',NULL,'Woonghyun, Cho','남성',NULL,NULL,27),(26,NULL,NULL,NULL,NULL,NULL,NULL,NULL,28),(27,NULL,NULL,NULL,NULL,NULL,NULL,NULL,29),(28,NULL,NULL,NULL,NULL,NULL,NULL,NULL,30),(29,NULL,NULL,NULL,NULL,NULL,NULL,NULL,31),(30,NULL,NULL,NULL,NULL,NULL,NULL,NULL,32),(31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,33),(32,NULL,NULL,NULL,NULL,NULL,NULL,NULL,34),(33,NULL,NULL,NULL,NULL,NULL,NULL,NULL,35),(34,NULL,NULL,NULL,NULL,NULL,NULL,NULL,36);
/*!40000 ALTER TABLE `personal_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `belong` varchar(255) DEFAULT NULL,
  `brn` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `withdrawal` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'관리자',0,'test@naver.com','test','$2a$10$dpx.YsXGxtDcpCmHQ5dDIeo8Ak.eeo5.1vrz0p4sSAcTNPhsjUiSi','01012345678',2,_binary '\0'),(2,'SSAFY 교육생',0,'user@naver.com','조웅현','$2a$10$HhZzhBKr6saY4cYfA8GHWOjRs/ftEqLJCpXV8sXtbLZOp8lKqXW/e','01099508978',0,_binary '\0'),(5,'test',0,'test2@naver.com','조웅현','$2a$10$rxXUvha9RoHljFYNdY.8z.a3MgM9SME5YKrO0a5zN1Z7SW4k14aGS','01099508978',0,_binary '\0'),(6,'깐부치킨',0,'123@naver.com','깐부','$2a$10$ONDyqgnKrH03oDXhB8cmwOSwHnddPHS7fPBQQrWcETHTqqwsT4u/W','01012163164',0,_binary '\0'),(27,'SSAFY 교육생',0,'tofan@naver.com','조웅현','$2a$10$wAeU5ZY5J3ReohJ8muX2KORBH4ZPV52VbK1vaDhf8od8Al1jnLEia','01099508978',0,_binary '\0'),(28,'SSAFY 교육생',0,'3@naver.com','조웅현','$2a$10$D4NTKIC.BBFYADUNGT7YQeFGVNP8jA6uxbFEJfgGBhcXlqk.YSrb6','01099508978',0,_binary '\0'),(29,'SSAFY 교육생',0,'1@naver.com','조웅현','$2a$10$aW7IvHYNNR8pfIWllk1ssOqznJTUBI4QuD0gMGUektJULTVas.6py','01099508978',0,_binary '\0'),(30,'SSAFY 교육생',0,'12345@naver.com','안창호','$2a$10$QEsQS4I0QImJSYd4./fg3OCDFCuUUKLWMgkjARpDe82YKlE2MV7fG','01012345678',0,_binary '\0'),(31,'123',0,'toast@naver.com','123','$2a$10$O4eq6nrDhq3MKHbCawBTeOSlg95fFy3qJymK6rpZ3uka/3ivDiio2','123',0,_binary '\0'),(32,'와우와우와우',156156156,'brn@naver.com','테스트123123','$2a$10$O41GgvKWmV0KsCEIOtENvuOUqiwNpJ5jQsJCreBHs6mRbs98occom','01015156156',0,_binary '\0'),(33,'1231',0,'1231@naver.com','1231','$2a$10$sLSRvMadoc8kURvGLIb4AObRJXD1f/fABexH6PJWhOdteSKtmu1M6','123',0,_binary '\0'),(34,'경북대학교',0,'gywls@naver.com','노효진','$2a$10$/YQgEMNHiU77W2Nk19QWKOlaMdH5px192Cr/AGk7DFI6wj9ysesgq','01012345678',0,_binary '\0'),(35,'푸드덕',0,'bird1@naver.com','1122qq','$2a$10$x79MrAI.N3J05X7FV07zd.dxSlIYDnjW7AKfzqa9qGvFA8lRFw8qq','01022463628',0,_binary '\0'),(36,'취준진담',0,'bird2@naver.com','쩡쎈','$2a$10$5yLkF.5FAsIbZRZEjwcGWOyjU4SkgqC7B9Xyffm.egOCBn4KzwkmG','0123456789',0,_binary '\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wallet`
--

DROP TABLE IF EXISTS `user_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `balance` varchar(255) DEFAULT NULL,
  `receiving_count` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5wbfrr7pvvooqb01ko72t6ewg` (`user_id`),
  CONSTRAINT `FK5wbfrr7pvvooqb01ko72t6ewg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wallet`
--

LOCK TABLES `user_wallet` WRITE;
/*!40000 ALTER TABLE `user_wallet` DISABLE KEYS */;
INSERT INTO `user_wallet` VALUES (64,'0xf255FC9eF3778E688950649547D398B027D8b999',NULL,NULL,1),(65,NULL,NULL,NULL,NULL),(66,NULL,NULL,NULL,NULL),(67,NULL,NULL,NULL,NULL),(68,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification`
--

DROP TABLE IF EXISTS `verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_status` varchar(255) DEFAULT NULL,
  `reasons_rejection` longtext DEFAULT NULL,
  `registration_date` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `personal_info_id` bigint(20) DEFAULT NULL,
  `gallery_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrxo35ity89q1d0tssw21j4peg` (`personal_info_id`),
  KEY `FKhaujk77y2q8wsh3h7t3g4mt9i` (`gallery_id`),
  CONSTRAINT `FKhaujk77y2q8wsh3h7t3g4mt9i` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`),
  CONSTRAINT `FKrxo35ity89q1d0tssw21j4peg` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification`
--

LOCK TABLES `verification` WRITE;
/*!40000 ALTER TABLE `verification` DISABLE KEYS */;
INSERT INTO `verification` VALUES (1,'승인완료','','2021-10-05 15:42:19.000000',1,1,1),(15,'거절','기간만료','2021-10-07 01:43:59.000000',1,1,2),(17,'거절','기간만료','2021-10-07 01:46:09.000000',1,1,4),(22,'거절','기간만료','2021-10-07 01:51:30.000000',1,1,5),(42,'거절','내맘이야!!!','2021-10-08 04:59:59.974000',35,33,32),(43,'승인대기',NULL,'2021-10-08 05:00:22.566000',35,33,33),(44,'거절','ssss','2021-10-08 05:14:01.306000',35,33,34),(69,'승인대기',NULL,'2021-10-08 06:50:32.202000',27,25,59),(73,'승인대기',NULL,'2021-10-08 08:26:29.630000',27,25,63);
/*!40000 ALTER TABLE `verification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'B101'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-08  8:27:10
