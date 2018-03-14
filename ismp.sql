-- MySQL dump 10.13  Distrib 5.6.13, for osx10.7 (x86_64)
--
-- Host: localhost    Database: ismp
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `tbl_delivery_receipt`
--

DROP TABLE IF EXISTS `tbl_delivery_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_delivery_receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `correlator` varchar(50) DEFAULT NULL,
  `delivery_status` varchar(50) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_delivery_receipt`
--

LOCK TABLES `tbl_delivery_receipt` WRITE;
/*!40000 ALTER TABLE `tbl_delivery_receipt` DISABLE KEYS */;
INSERT INTO `tbl_delivery_receipt` VALUES (1,'6','DeliveredToNetwork','1381071','2016-03-25 12:45:42',NULL),(2,'6','DeliveredToNetwork','1381071','2016-03-25 12:47:30',NULL);
/*!40000 ALTER TABLE `tbl_delivery_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_management_info`
--

DROP TABLE IF EXISTS `tbl_management_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_management_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stream_no` varchar(65) DEFAULT NULL,
  `local_id` varchar(50) DEFAULT NULL,
  `local_id_type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `result_code` int(11) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_management_info`
--

LOCK TABLES `tbl_management_info` WRITE;
/*!40000 ALTER TABLE `tbl_management_info` DISABLE KEYS */;
INSERT INTO `tbl_management_info` VALUES (1,'xxxxx','1',1,1,0,NULL,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `tbl_management_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_relationship_update`
--

DROP TABLE IF EXISTS `tbl_order_relationship_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order_relationship_update` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stream_no` varchar(65) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `user_id_type` int(11) DEFAULT NULL,
  `product_id` varchar(50) DEFAULT NULL,
  `old_product_id` varchar(50) DEFAULT NULL,
  `package_id` varchar(50) DEFAULT NULL,
  `old_package_id` varchar(50) DEFAULT NULL,
  `op_type` int(11) DEFAULT NULL,
  `result_code` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_relationship_update`
--

LOCK TABLES `tbl_order_relationship_update` WRITE;
/*!40000 ALTER TABLE `tbl_order_relationship_update` DISABLE KEYS */;
INSERT INTO `tbl_order_relationship_update` VALUES (1,'xxx6','13810715929',1,'4','5','2','3',1,0,'0000-00-00 00:00:00',NULL);
/*!40000 ALTER TABLE `tbl_order_relationship_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_black_list`
--

DROP TABLE IF EXISTS `tbl_restriction_black_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_black_list`
--

LOCK TABLES `tbl_restriction_black_list` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_black_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_black_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_config_phone`
--

DROP TABLE IF EXISTS `tbl_restriction_config_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_config_phone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `max_phone_day` int(11) DEFAULT NULL,
  `max_phone_month` int(11) DEFAULT NULL,
  `max_phone_amount` int(11) DEFAULT NULL,
  `max_phone_sub_channel` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_config_phone`
--

LOCK TABLES `tbl_restriction_config_phone` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_config_phone` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_config_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_config_province`
--

DROP TABLE IF EXISTS `tbl_restriction_config_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_config_province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `province` int(11) DEFAULT NULL,
  `max_phone_day` int(11) DEFAULT NULL,
  `max_phone_month` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_config_province`
--

LOCK TABLES `tbl_restriction_config_province` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_config_province` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_config_province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_config_sub_channel`
--

DROP TABLE IF EXISTS `tbl_restriction_config_sub_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_config_sub_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_channel` varchar(20) DEFAULT NULL,
  `max_phone_day` int(11) DEFAULT NULL,
  `max_phone_month` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_config_sub_channel`
--

LOCK TABLES `tbl_restriction_config_sub_channel` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_config_sub_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_config_sub_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_phone`
--

DROP TABLE IF EXISTS `tbl_restriction_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_phone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL,
  `day_times` int(11) DEFAULT NULL,
  `month_times` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `channel_times` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `channels` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_phone`
--

LOCK TABLES `tbl_restriction_phone` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_phone` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_province`
--

DROP TABLE IF EXISTS `tbl_restriction_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `province` int(11) DEFAULT NULL,
  `day_times` int(11) DEFAULT NULL,
  `month_times` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_province`
--

LOCK TABLES `tbl_restriction_province` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_province` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_restriction_sub_channel`
--

DROP TABLE IF EXISTS `tbl_restriction_sub_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_restriction_sub_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_channel` varchar(10) DEFAULT NULL,
  `day_times` int(11) DEFAULT NULL,
  `month_times` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_restriction_sub_channel`
--

LOCK TABLES `tbl_restriction_sub_channel` WRITE;
/*!40000 ALTER TABLE `tbl_restriction_sub_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_restriction_sub_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_send_sms`
--

DROP TABLE IF EXISTS `tbl_send_sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_send_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(50) DEFAULT NULL,
  `sender_name` varchar(50) DEFAULT NULL,
  `message` varchar(400) DEFAULT NULL,
  `receipt` int(11) DEFAULT '0' COMMENT '0:no receipt 1:receipt',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `request_identifier` varchar(50) DEFAULT NULL,
  `sms_type` int(11) DEFAULT '0' COMMENT '0:noraml sms,1:payment sms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_send_sms`
--

LOCK TABLES `tbl_send_sms` WRITE;
/*!40000 ALTER TABLE `tbl_send_sms` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_send_sms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_service_cosume`
--

DROP TABLE IF EXISTS `tbl_service_cosume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_service_cosume` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stream_no` varchar(65) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `user_id_type` int(11) DEFAULT NULL,
  `product_id` varchar(50) DEFAULT NULL,
  `link_id` varchar(50) DEFAULT NULL,
  `feature_str` varchar(50) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_service_cosume`
--

LOCK TABLES `tbl_service_cosume` WRITE;
/*!40000 ALTER TABLE `tbl_service_cosume` DISABLE KEYS */;
INSERT INTO `tbl_service_cosume` VALUES (1,'4','5',1,'3','2','1','2016-03-25 11:18:54',NULL);
/*!40000 ALTER TABLE `tbl_service_cosume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sms_config`
--

DROP TABLE IF EXISTS `tbl_sms_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_sms_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cmd` varchar(20) DEFAULT NULL,
  `wild_match` int(11) DEFAULT '0' COMMENT '0=accurency,1=wild',
  `cmd_type` int(11) DEFAULT '0' COMMENT '0=single,1=pack',
  `msg` varchar(200) DEFAULT NULL,
  `succeed_msg` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `product_id` varchar(50) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sms_config`
--

LOCK TABLES `tbl_sms_config` WRITE;
/*!40000 ALTER TABLE `tbl_sms_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_sms_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sms_reception`
--

DROP TABLE IF EXISTS `tbl_sms_reception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_sms_reception` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `registration_identifier` varchar(50) DEFAULT NULL,
  `message` varchar(400) DEFAULT NULL,
  `active_number` varchar(400) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `sender_address` varchar(50) DEFAULT NULL,
  `link_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sms_reception`
--

LOCK TABLES `tbl_sms_reception` WRITE;
/*!40000 ALTER TABLE `tbl_sms_reception` DISABLE KEYS */;
INSERT INTO `tbl_sms_reception` VALUES (1,'12','xx','1','2016-03-25 12:48:57',NULL,'138','?'),(2,'12','xx','1','2016-03-25 12:50:28',NULL,'138','?');
/*!40000 ALTER TABLE `tbl_sms_reception` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sms_restriction`
--

DROP TABLE IF EXISTS `tbl_sms_restriction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_sms_restriction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL,
  `province` int(11) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  `cmd` varchar(20) DEFAULT NULL,
  `sub_channel` varchar(50) DEFAULT NULL,
  `sub_channel_order_id` varchar(50) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `valid_type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sms_restriction`
--

LOCK TABLES `tbl_sms_restriction` WRITE;
/*!40000 ALTER TABLE `tbl_sms_restriction` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_sms_restriction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_withdraw_subscription`
--

DROP TABLE IF EXISTS `tbl_withdraw_subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_withdraw_subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stream_no` varchar(65) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `user_id_type` int(11) DEFAULT NULL,
  `local_id_type` int(11) DEFAULT NULL,
  `local_id` varchar(50) DEFAULT NULL,
  `result_code` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_withdraw_subscription`
--

LOCK TABLES `tbl_withdraw_subscription` WRITE;
/*!40000 ALTER TABLE `tbl_withdraw_subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_withdraw_subscription` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-31 13:39:09
