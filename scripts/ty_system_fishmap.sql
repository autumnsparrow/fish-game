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

/*Table structure for table `ty_fishmap_fish` */

DROP TABLE IF EXISTS `ty_fishmap_fish`;

CREATE TABLE `ty_fishmap_fish` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图鉴鱼id',
  `fishmap_item_id` int(11) NOT NULL COMMENT '所属图鉴任务id',
  `fish_id` varchar(30) DEFAULT NULL COMMENT '鱼类id',
  `fish_init_grade` int(11) DEFAULT '0' COMMENT '鱼初始级别',
  `fish_sequence` int(11) DEFAULT NULL COMMENT '鱼排序',
  PRIMARY KEY (`id`),
  KEY `fk_fishmap_item_id` (`fishmap_item_id`),
  CONSTRAINT `fk_fishmap_item_id` FOREIGN KEY (`fishmap_item_id`) REFERENCES `ty_fishmap_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_fish` */

/*Table structure for table `ty_fishmap_item` */

DROP TABLE IF EXISTS `ty_fishmap_item`;

CREATE TABLE `ty_fishmap_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图鉴任务Id',
  `scenes_id` int(11) DEFAULT NULL COMMENT '所属图鉴场景id',
  `name` varchar(30) DEFAULT NULL COMMENT '图鉴任务名',
  `sequence` int(11) DEFAULT NULL COMMENT '图鉴任务在场景中排序',
  `reward_sequence` int(11) DEFAULT '0' COMMENT '显示领取奖励的顺序,默认没完成奖励',
  PRIMARY KEY (`id`),
  KEY `fk_fishmap_id` (`scenes_id`),
  CONSTRAINT `fk_fishmap_id` FOREIGN KEY (`scenes_id`) REFERENCES `ty_fishmap_scenes` (`scenes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_item` */

/*Table structure for table `ty_fishmap_reward` */

DROP TABLE IF EXISTS `ty_fishmap_reward`;

CREATE TABLE `ty_fishmap_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖励id',
  `fishmap_item_id` int(11) DEFAULT NULL COMMENT '所属图鉴任务id',
  `item_id` int(11) DEFAULT NULL COMMENT '物品id',
  `item_count` int(11) DEFAULT NULL COMMENT '物品数量',
  `sequence` int(11) DEFAULT NULL COMMENT '奖励排序',
  PRIMARY KEY (`id`),
  KEY `fk_reward_item_id` (`fishmap_item_id`),
  CONSTRAINT `fk_reward_item_id` FOREIGN KEY (`fishmap_item_id`) REFERENCES `ty_fishmap_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_reward` */

/*Table structure for table `ty_fishmap_scenes` */

DROP TABLE IF EXISTS `ty_fishmap_scenes`;

CREATE TABLE `ty_fishmap_scenes` (
  `scenes_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场景id',
  `total` int(11) DEFAULT NULL COMMENT '总数量',
  `sequence` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`scenes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ty_fishmap_scenes` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
