/*
SQLyog v10.2 
MySQL - 5.6.22-log : Database - ty_zone
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ty_zone` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ty_zone`;

/*Table structure for table `ty_fishmap_fish_record` */

DROP TABLE IF EXISTS `ty_fishmap_fish_record`;

CREATE TABLE `ty_fishmap_fish_record` (
  `item_id` int(11) DEFAULT NULL COMMENT '所属图鉴item',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `fish_id` int(11) DEFAULT NULL COMMENT '鱼id',
  `fish_grade` int(11) DEFAULT NULL COMMENT '调到的鱼的最大级别',
  `fish_record_length` int(11) DEFAULT NULL COMMENT '钓上鱼的最大长度',
  `fish_record_date` timestamp NULL DEFAULT NULL COMMENT '最大鱼钓上时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_fish_record` */

/*Table structure for table `ty_fishmap_progress` */

DROP TABLE IF EXISTS `ty_fishmap_progress`;

CREATE TABLE `ty_fishmap_progress` (
  `scenes_id` int(11) DEFAULT NULL COMMENT '图鉴场景Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `finished` int(11) DEFAULT NULL COMMENT '完成图鉴鱼类数',
  `rewarding` int(11) DEFAULT NULL COMMENT '需领取奖励数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_progress` */

/*Table structure for table `ty_fishmap_reward_record` */

DROP TABLE IF EXISTS `ty_fishmap_reward_record`;

CREATE TABLE `ty_fishmap_reward_record` (
  `item_id` int(11) DEFAULT NULL COMMENT '图鉴任务id',
  `user_id` bigint(20) DEFAULT NULL,
  `reward_id` int(11) DEFAULT NULL COMMENT '奖励id',
  `state` int(11) DEFAULT NULL COMMENT '0:未完成1:已完成2:已领取',
  `record_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_reward_record` */

/*Table structure for table `ty_fishmap_reward_record_log` */

DROP TABLE IF EXISTS `ty_fishmap_reward_record_log`;

CREATE TABLE `ty_fishmap_reward_record_log` (
  `item_id` int(11) DEFAULT NULL COMMENT '图鉴itemid',
  `user_id` bigint(20) DEFAULT NULL,
  `reward_id` int(11) DEFAULT NULL COMMENT '奖励id',
  `reward_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取奖励时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_reward_record_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
