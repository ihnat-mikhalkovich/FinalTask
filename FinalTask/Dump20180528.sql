-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: hosteldb
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(2) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'ru'),(2,'en');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `rooms_amount` tinyint(4) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `visibility` tinyint(4) NOT NULL,
  `number_of_persons` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,4,13.37,'image/rooms/8',1,1),(2,10,13.37,'image/rooms/1',0,1),(3,10,22.22,'image/rooms/2',1,2),(4,10,33.37,'image/rooms/3',1,2),(5,10,24,'image/rooms/4',0,1),(6,10,100,'image/rooms/5',1,5),(7,10,70,'image/rooms/6',1,4),(14,10,150,'image/rooms/7',0,3),(15,2,35,'image/rooms/9',1,5),(16,2,1.11,'qwerqwerqwer',0,2),(17,2,1.11,'qwerqwerqwer',0,2),(18,3,2.11,'qwerqwerqwer11',0,4),(19,1,13.371,'1',1,1),(20,3,12,'qazqazqaz',1,4),(21,11,12,'1',0,1),(22,2,32,'image/rooms/8',0,4),(23,4,44,'2',0,2);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms_translate`
--

DROP TABLE IF EXISTS `rooms_translate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms_translate` (
  `rooms_id` int(11) unsigned NOT NULL,
  `language_id` int(11) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(900) DEFAULT NULL,
  PRIMARY KEY (`rooms_id`,`language_id`),
  KEY `fk_rooms_has_language_language1_idx` (`language_id`),
  KEY `fk_rooms_has_language_rooms_idx` (`rooms_id`),
  CONSTRAINT `fk_rooms_has_language_language1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rooms_has_language_rooms` FOREIGN KEY (`rooms_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms_translate`
--

LOCK TABLES `rooms_translate` WRITE;
/*!40000 ALTER TABLE `rooms_translate` DISABLE KEYS */;
INSERT INTO `rooms_translate` VALUES (1,1,'Люкc','Самый лучший люкс на свете.'),(1,2,'Single','The best single.'),(2,1,'Люкс','Самый лучший люкс2 на одного человека.'),(2,2,'Single','The best single2.'),(3,1,'Двухместный номер с двумя раздельными кроватями','Самый лучший двухместный номер с двумя раздельными кроватями.'),(3,2,'Twin','The best twin.'),(4,1,'Двухместный номер с одной большой двуспальной кроватью','Самый лучший двухместный номер с одной большой двуспальной кроватью.'),(4,2,'Double','The best double.'),(5,1,'Люкс 2.0','Люкс 2.0 - это новый тип Люксов.'),(5,2,'Single 2.0','Single 2.0 - this is new type of single.'),(6,1,'Апартаменты','Всё только самое лучшее.'),(6,2,'Apartments','All the best.'),(7,1,'Четырехместный номер','Лучший четырехместный номер.'),(7,2,'Quarter','The best quarter.'),(14,1,'Трехместный номер','Лучший трехместный номер.'),(14,2,'Triple','The best triple.'),(15,1,'Люкс 3.0111','Люкс 3.0 - это СУПЕР новый тип Люксов.111'),(15,2,'Single 3.0111','Single 3.0 - this is SUPER new type of single.111'),(16,1,'йцук','йцукйцук'),(16,2,'qwer','qwerqwer'),(17,1,'йцук','йцукйцук'),(17,2,'qwer','qwerqwer'),(18,1,'йцук1','йцукйцук1'),(18,2,'qwer1','qwerqwer1'),(19,1,'Люкc1','Люкc1'),(19,2,'Single1','Single1'),(20,1,'йфяйфяйфяйфя','йфяйфя'),(20,2,'qazqazqazqazqaz','qazqaz'),(21,1,'1112','1112'),(21,2,'1112','1112'),(22,1,'фыв','фыв'),(22,2,'asd','asd'),(23,1,'вап','вап'),(23,2,'dfg','dfg');
/*!40000 ALTER TABLE `rooms_translate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffs`
--

DROP TABLE IF EXISTS `tariffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariffs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `visibility` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffs`
--

LOCK TABLES `tariffs` WRITE;
/*!40000 ALTER TABLE `tariffs` DISABLE KEYS */;
INSERT INTO `tariffs` VALUES (1,0,1),(2,10,1),(3,50,1),(4,44.51,0),(5,12,0);
/*!40000 ALTER TABLE `tariffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffs_translate`
--

DROP TABLE IF EXISTS `tariffs_translate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariffs_translate` (
  `name` varchar(255) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `description` varchar(900) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `language_id` int(11) unsigned NOT NULL,
  `tariffs_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`language_id`,`tariffs_id`),
  KEY `fk_tariffs_translate_tariffs1_idx` (`tariffs_id`),
  CONSTRAINT `fk_tariffs_translate_language1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tariffs_translate_tariffs1` FOREIGN KEY (`tariffs_id`) REFERENCES `tariffs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffs_translate`
--

LOCK TABLES `tariffs_translate` WRITE;
/*!40000 ALTER TABLE `tariffs_translate` DISABLE KEYS */;
INSERT INTO `tariffs_translate` VALUES ('Обычный.','Только уборка комнаты.',1,1),('Продвинктый.','Завтрак и уборка комнаты.',1,2),('Всё включено.','Завтрак, уборка комнаты, бар.',1,3),('изменено имя1','изменено описание1',1,4),('ннннв','ннннв',1,5),('Common.','Cleaning only.',2,1),('Advanced.','Breakfast and room cleaning.',2,2),('All inclusive.','Breakfast, room cleaning, bar.',2,3),('changed name1','changed description1',2,4),('yyyyd','yyyyd',2,5);
/*!40000 ALTER TABLE `tariffs_translate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL,
  `last_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL,
  `telephone_number` char(13) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL,
  `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL,
  `role` enum('user','administrator','moderator','banned') NOT NULL DEFAULT 'user',
  `balance` double NOT NULL DEFAULT '0',
  `registration_date` date NOT NULL,
  `discount` tinyint(4) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Ihnat','Mikhalkovich','d9eff2de5a0e0e46efad7ba4ef2e8706','+375291264166','magnat118@gmail.com','administrator',66.38,'2018-04-11',0),(2,'Mikhail','Novicki','8ce87b8ec346ff4c80635f667d1592ae','+375291234567','novicki118@gmail.com','banned',211.37,'2018-04-11',5),(3,'Amal','Kabulov','d9eff2de5a0e0e46efad7ba4ef2e8706','+375293337331','amal@gmail.com','user',36.04,'2018-04-11',6),(4,'modtest','modtest','d9eff2de5a0e0e46efad7ba4ef2e8706','+375293226543','modtest@gmail.com','moderator',112.37,'2018-04-11',5),(5,'ivan','ivanov','ivam123','+375291111111','ivan@mail.ru','moderator',125.74,'2018-04-11',5),(6,'usertest','usertest','8ce87b8ec346ff4c80635f667d1592ae','+375292222222','usertest@gmail.com','user',143.37,'2018-04-11',5),(7,'Carl','Marx','carl123','+375291111111','carl@outlook.com','moderator',112.37,'2018-04-11',0),(8,'qqq','qqq','qqq','+375291234567','qqq@gmail.com','user',112.37,'2018-04-11',0),(9,'кеккек','кеккек','qaz','+375299119110','kekkek@tut.by','user',112.37,'2018-04-11',0),(10,'Игнат','Михалкович','ihnat1234','+375291234567','magnat1180@outlook.com','user',118.37,'2018-04-11',0),(11,'fgsg','dfggs','ert','+375299999999','ert99@tut.by','user',112.37,'2018-04-11',0),(12,'asdf','asdf','asdf','+375293456781','asdf@tut.by','user',112.37,'2018-04-11',0),(13,'q1','q1','8ce87b8ec346ff4c80635f667d1592ae','31','magnat1181@outlook.com','moderator',112.37,'2018-04-11',0),(14,'qq','qq','eqw','+375291252342','qq@tut.by','user',112.37,'2018-04-11',0),(15,'Игнат','Михалкович','ihnat1234','+375291234567','magnat118@mail.com','user',112.37,'2018-04-11',7),(16,'aa','aa','ea9ac9a3e1c93943578c61b75ef2ca57','+375291111112','aa@mail.ru','user',112.37,'2018-04-11',0),(17,'testfm','testlm','test','+375291111113','test@gmail.com','banned',92.37,'2018-04-11',0),(18,'testfm','testlm','test','+375291111114','test2@gmail.com','banned',34.01,'2018-04-11',0),(19,'testfm','testlm','test','+375291234567','test3@gmail.com','user',112.37,'2018-04-11',0),(20,'test','edit','05a671c66aefea124cc08b76ea6d30bb','+375291264121','email@gmail.com','user',33.33,'2018-04-11',3),(21,'12','12','8ce87b8ec346ff4c80635f667d1592ae','+375293226541','1212@tut.by','user',112.37,'2018-04-11',0),(22,'123','123','8ce87b8ec346ff4c80635f667d1592ae','+375293226543','dddffffdd@mail.ru','user',112.37,'2018-04-11',0),(23,'123','123','f5bb0c8de146c67b44babbf4e6584cc0','+375291112312','123123@gmail.com','user',112.37,'2018-04-11',0),(24,'12','12','8ce87b8ec346ff4c80635f667d1592ae','+375299119121','qwer1212@tut.by','user',112.37,'2018-04-11',0),(25,'123','123','d9eff2de5a0e0e46efad7ba4ef2e8706','+375291231267','ssdffffd@mail.ru','user',0,'2018-05-28',0),(26,'112311','1231313','d9eff2de5a0e0e46efad7ba4ef2e8706','+375293214543','mag12t118@gmail.com','user',0,'2018-05-28',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_rooms`
--

DROP TABLE IF EXISTS `users_has_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_rooms` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `users_id` int(11) unsigned NOT NULL,
  `rooms_id` int(11) unsigned NOT NULL,
  `rooms_amount` tinyint(4) NOT NULL,
  `arrival` date NOT NULL,
  `departure` date NOT NULL,
  `approval` tinyint(4) NOT NULL DEFAULT '0',
  `paid` tinyint(4) NOT NULL DEFAULT '0',
  `total_cost` double unsigned NOT NULL DEFAULT '0',
  `tariffs_id` int(10) unsigned NOT NULL,
  `canceled` tinyint(4) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_users_has_rooms_rooms1_idx` (`rooms_id`),
  KEY `fk_users_has_rooms_users1_idx` (`users_id`),
  CONSTRAINT `fk_users_has_rooms_rooms1` FOREIGN KEY (`rooms_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_rooms_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_rooms`
--

LOCK TABLES `users_has_rooms` WRITE;
/*!40000 ALTER TABLE `users_has_rooms` DISABLE KEYS */;
INSERT INTO `users_has_rooms` VALUES (6,4,2,2,'2018-04-18','2018-04-19',1,1,100,1,0),(7,4,1,1,'2018-04-14','2018-04-15',1,1,100,1,0),(8,5,2,1,'2018-04-20','2018-04-22',1,1,100,1,0),(9,3,3,6,'2018-04-25','2018-04-26',1,1,100,1,0),(10,1,3,1,'2018-04-20','2018-04-21',1,1,100,1,0),(11,2,3,1,'2018-04-19','2018-04-20',1,1,100,1,0),(12,3,3,1,'2018-04-18','2018-04-19',1,1,100,1,0),(13,4,3,1,'2018-04-17','2018-04-18',1,1,100,1,0),(14,5,3,1,'2018-04-16','2018-04-17',1,1,100,1,0),(16,3,2,2,'2018-04-20','2018-04-21',1,1,100,1,0),(17,6,3,4,'2018-05-03','2018-05-04',1,1,100,1,0),(20,6,19,1,'2018-05-05','2018-05-06',1,1,100,1,0),(24,6,1,1,'2018-05-18','2018-05-19',1,0,13.37,1,0),(25,1,1,1,'2018-05-24','2018-05-25',1,0,23.37,2,1),(26,1,1,1,'2018-05-24','2018-05-25',0,0,23.37,2,1),(27,1,3,2,'2018-05-25','2018-05-26',1,0,54.44,2,0),(28,1,3,2,'2018-05-29','2018-05-30',1,1,44.44,1,0),(29,1,1,1,'2018-05-29','2018-05-30',1,1,13.37,1,0),(30,3,1,2,'2018-05-29','2018-05-31',1,1,50.27,1,0),(31,3,3,1,'2018-05-29','2018-05-30',1,1,20.89,1,0),(32,3,15,1,'2018-05-29','2018-05-30',1,1,31.96,1,0),(33,3,1,1,'2018-06-25','2018-06-26',1,1,12.57,1,0),(34,1,1,1,'2018-05-29','2018-05-30',1,1,13.37,1,0),(35,1,3,1,'2018-05-29','2018-05-30',1,1,22.22,1,0);
/*!40000 ALTER TABLE `users_has_rooms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-28 12:50:21
