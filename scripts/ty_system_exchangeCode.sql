/*
SQLyog v10.2 
MySQL - 5.6.22-log : Database - ty_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ty_system` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ty_system`;

/*Table structure for table `ty_exchange_code` */

DROP TABLE IF EXISTS `ty_exchange_code`;

CREATE TABLE `ty_exchange_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(9) DEFAULT NULL COMMENT '码值',
  `gift_id` int(11) DEFAULT NULL COMMENT '礼物包',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '兑换码生效时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '兑换码失效时间',
  `flag` int(11) DEFAULT NULL COMMENT '0-可以使用 1-不可使用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `ty_exchange_code` */

insert  into `ty_exchange_code`(`id`,`code`,`gift_id`,`begin_time`,`end_time`,`flag`) values (1,'ABC000001',1,'2015-12-10 16:45:21','2016-03-03 16:45:24',0);

/*Table structure for table `ty_exchange_code_gift` */

DROP TABLE IF EXISTS `ty_exchange_code_gift`;

CREATE TABLE `ty_exchange_code_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '礼包id',
  `name` varchar(20) DEFAULT NULL COMMENT '礼包名称',
  `state` int(11) DEFAULT NULL COMMENT '0-不可使用 1-可使用',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `ty_exchange_code_gift` */

insert  into `ty_exchange_code_gift`(`id`,`name`,`state`,`create_time`) values (1,'白给的礼包',1,'2015-12-11 16:46:29');

/*Table structure for table `ty_exchange_code_item` */

DROP TABLE IF EXISTS `ty_exchange_code_item`;

CREATE TABLE `ty_exchange_code_item` (
  `gift_id` int(11) DEFAULT NULL COMMENT '礼包Id',
  `item_id` varchar(40) DEFAULT NULL COMMENT '物品id',
  `item_total` int(11) DEFAULT NULL COMMENT '物品数'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `ty_exchange_code_item` */

insert  into `ty_exchange_code_item`(`gift_id`,`item_id`,`item_total`) values (1,'Sr_0007_01',1),(1,'Ie_gold_01',2),(1,'Ie_jewel_01',2),(1,'Ie_vit_01',2),(1,'Drink_0006_01',20);

/*Table structure for table `ty_general_exchange_code` */

DROP TABLE IF EXISTS `ty_general_exchange_code`;

CREATE TABLE `ty_general_exchange_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL COMMENT '兑换码值',
  `gift_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `begin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '生效时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效时间',
  `flag` int(11) DEFAULT NULL COMMENT '0-可使用 1-不可使用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `ty_general_exchange_code` */

insert  into `ty_general_exchange_code`(`id`,`code`,`gift_id`,`begin_time`,`end_time`,`flag`) values (1,'666666',1,'2015-12-10 16:44:36','2016-03-24 16:44:40',0);

/*Table structure for table `ty_user_general_exchange_code` */

DROP TABLE IF EXISTS `ty_user_general_exchange_code`;

CREATE TABLE `ty_user_general_exchange_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_id` int(11) DEFAULT NULL COMMENT '通用兑换码id',
  `gift_id` int(11) DEFAULT NULL COMMENT '礼品包Id',
  `code` char(6) DEFAULT NULL COMMENT '通用兑换码',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '领取时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_user_general_exchange_code` */

/*Table structure for table `ty_user_level_data` */

DROP TABLE IF EXISTS `ty_user_level_data`;

CREATE TABLE `ty_user_level_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `next_level_exp` int(11) DEFAULT NULL COMMENT '下级经验',
  `this_level_exp` int(11) DEFAULT NULL COMMENT '本级经验',
  `damage` int(11) DEFAULT NULL COMMENT '伤害',
  `line_length_addition` int(11) DEFAULT NULL COMMENT '鱼线长度加成',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `ty_user_level_data` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
