/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.28 : Database - ktv
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ktv` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ktv`;

/*Table structure for table `ktv_item` */

DROP TABLE IF EXISTS `ktv_item`;

CREATE TABLE `ktv_item` (
  `item_id` varchar(32) NOT NULL COMMENT '商品id',
  `item_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `item_price` decimal(24,6) DEFAULT NULL COMMENT '商品价格',
  `item_endtime` datetime DEFAULT NULL COMMENT '商品到期时间',
  `item_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '商品创建时间',
  `item_count` int DEFAULT NULL COMMENT '商品数量',
  `item_updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品更新时间',
  `item_state` tinyint(1) DEFAULT NULL COMMENT '商品状态',
  `item_unit` varchar(32) DEFAULT NULL COMMENT '商品单位',
  `item_typeid` varchar(32) DEFAULT NULL COMMENT '商品类型id',
  `item_consume` int DEFAULT NULL COMMENT '商品消耗数量',
  `item_img` longtext COMMENT '商品图片',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv商品表';

/*Data for the table `ktv_item` */

insert  into `ktv_item`(`item_id`,`item_name`,`item_price`,`item_endtime`,`item_createtime`,`item_count`,`item_updatetime`,`item_state`,`item_unit`,`item_typeid`,`item_consume`,`item_img`) values 
('1528993698853978114','包子',3.000000,'2022-05-26 00:00:00','2022-05-24 14:57:59',3,'2022-05-24 17:03:42',1,NULL,'1523546666353823745',1,'http://192.168.146.141:9000/image/1528993698853978114.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T065759Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a57cf32c5db77591051c0ed6ff721175b7f138ebedd1baa792a92de3f771cbac'),
('1529035597014892545','芬达',3.000000,'2022-05-28 00:00:00','2022-05-24 17:44:29',10,'2022-05-24 17:44:29',1,NULL,'1529034540834619393',0,'http://192.168.146.141:9000/image/1529035597014892545.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T094429Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=405abea441a5a841460ac69f95d36c3d0ebd193845a657fa76928075a6ee8139'),
('1529035699276218370','可乐',3.000000,'2022-05-28 00:00:00','2022-05-24 17:44:53',20,'2022-05-24 17:44:53',1,NULL,'1529034540834619393',0,'http://192.168.146.141:9000/image/1529035699276218370.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T094453Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=ab449aa11fb4e73ab646133d7f444101869ea728b2cfe526385e02d97584df49'),
('1529035951207088129','二锅头',10.000000,'2022-05-30 00:00:00','2022-05-24 17:45:53',25,'2022-05-24 17:45:53',1,NULL,'1529027004844748801',0,'http://192.168.146.141:9000/image/1529035951207088129.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T094553Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=602febb08e3527ffef3c6e7a3245a29758988a9daaa129097815973861829710'),
('1529036024905203713','香烟',20.000000,'2022-05-30 00:00:00','2022-05-24 17:46:11',25,'2022-05-24 17:46:11',1,NULL,'1529027942217166849',0,'http://192.168.146.141:9000/image/1529036024905203713.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T094611Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=48beccff472965a6de649e6d25cd22e5d8a2fb4cbffab120c9731111f9e1945d'),
('1529136215658360833','鸡腿',3.000000,'2022-05-31 00:00:00','2022-05-25 00:24:18',34,'2022-05-25 00:24:18',1,NULL,'1523546666353823745',0,'http://192.168.146.141:9000/image/1529136215658360833.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220524%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220524T162418Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=f9424abc90e77ffda67e73ff35a67a05cd9b91a0ff3576b96e759738d4b381d0'),
('1530973514042040321','羊肉串',10.000000,'2022-05-31 00:00:00','2022-05-30 02:05:04',10,'2022-05-30 02:05:04',1,NULL,'1523546666353823745',0,'http://192.168.146.141:9000/image/1530973514042040321.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220529%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220529T180504Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b65335f77b358130f0e240b9de8ee0684fae8d37fa07adfbfddbbe07aab33dd3');

/*Table structure for table `ktv_item_order` */

DROP TABLE IF EXISTS `ktv_item_order`;

CREATE TABLE `ktv_item_order` (
  `item_order_id` varchar(255) NOT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP,
  `ktv_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ktv_item_order` */

insert  into `ktv_item_order`(`item_order_id`,`room_id`,`createtime`,`ktv_user_id`) values 
('1529686500684349442','1a4e2a36f23f4748b9a6fdbcd16802a1','2022-05-26 12:50:56','1515247727770865665'),
('1530972728251768834','1a4e2a36f23f4748b9a6fdbcd16802a1','2022-05-30 02:01:57','1515247727770865665'),
('1531125920339980289','1a4e2a36f23f4748b9a6fdbcd16802a1','2022-05-30 12:10:41','1515247727770865665');

/*Table structure for table `ktv_item_type` */

DROP TABLE IF EXISTS `ktv_item_type`;

CREATE TABLE `ktv_item_type` (
  `type_id` varchar(32) NOT NULL,
  `type_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ktv_item_type` */

insert  into `ktv_item_type`(`type_id`,`type_name`) values 
('1523546666353823745','零食'),
('1529027004844748801','酒水'),
('1529027942217166849','香烟'),
('1529034540834619393','饮料');

/*Table structure for table `ktv_itemconsume` */

DROP TABLE IF EXISTS `ktv_itemconsume`;

CREATE TABLE `ktv_itemconsume` (
  `consume_id` varchar(32) NOT NULL COMMENT '消费id',
  `item_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名',
  `consume_order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消费订单id',
  `consume_count` int DEFAULT NULL COMMENT '消费数量',
  `consume_userid` varchar(255) DEFAULT NULL COMMENT '消费用户id',
  `consume_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消费记录创建时间',
  `consume_price` decimal(24,6) DEFAULT NULL COMMENT '消费价格',
  `consume_state` tinyint(1) DEFAULT NULL COMMENT '配送状态',
  `consume_endtime` datetime DEFAULT NULL COMMENT '送达时间',
  PRIMARY KEY (`consume_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv消费记录';

/*Data for the table `ktv_itemconsume` */

insert  into `ktv_itemconsume`(`consume_id`,`item_id`,`item_name`,`consume_order_id`,`consume_count`,`consume_userid`,`consume_createtime`,`consume_price`,`consume_state`,`consume_endtime`) values 
('1529686504236924930','1528993698853978114','包子','1529686500684349442',1,'1515247727770865665','2022-05-26 12:50:57',3.000000,1,NULL),
('1529686504236924931','1529136215658360833','鸡腿','1529686500684349442',1,'1515247727770865665','2022-05-26 12:50:57',3.000000,1,NULL),
('1529686504304033793','1529035597014892545','芬达','1529686500684349442',1,'1515247727770865665','2022-05-26 12:50:57',3.000000,1,NULL),
('1529686504304033794','1529035699276218370','可乐','1529686500684349442',2,'1515247727770865665','2022-05-26 12:50:57',3.000000,1,NULL),
('1530972765484605442','1528993698853978114','包子','1530972728251768834',3,'1515247727770865665','2022-05-30 02:02:06',3.000000,1,NULL),
('1530972765547520001','1529136215658360833','鸡腿','1530972728251768834',4,'1515247727770865665','2022-05-30 02:02:06',3.000000,1,NULL),
('1530972765547520002','1529036024905203713','香烟','1530972728251768834',2,'1515247727770865665','2022-05-30 02:02:06',20.000000,1,NULL),
('1531125946566963202','1528993698853978114','包子','1531125920339980289',2,'1515247727770865665','2022-05-30 12:10:47',3.000000,1,NULL),
('1531125946566963203','1529136215658360833','鸡腿','1531125920339980289',2,'1515247727770865665','2022-05-30 12:10:47',3.000000,1,NULL),
('1531125946634072066','1530973514042040321','羊肉串','1531125920339980289',1,'1515247727770865665','2022-05-30 12:10:47',10.000000,1,NULL);

/*Table structure for table `ktv_recharge` */

DROP TABLE IF EXISTS `ktv_recharge`;

CREATE TABLE `ktv_recharge` (
  `recharge_id` varchar(32) NOT NULL COMMENT '充值编号',
  `rechargeuser_id` varchar(90) DEFAULT NULL COMMENT '充值用户编号',
  `recharge_time` datetime DEFAULT NULL COMMENT '充值时间',
  `recharge_amount` decimal(24,6) DEFAULT NULL COMMENT '充值金额',
  `recharge_username` varchar(90) DEFAULT NULL COMMENT '充值用户名',
  PRIMARY KEY (`recharge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv充值记录表';

/*Data for the table `ktv_recharge` */

insert  into `ktv_recharge`(`recharge_id`,`rechargeuser_id`,`recharge_time`,`recharge_amount`,`recharge_username`) values 
('1501105001844441090','1','2022-03-08 15:58:16',290.000000,'467241156'),
('1501105147076411393','1','2022-03-08 15:58:51',22.200000,'467241156'),
('1501105606629523458','1','2022-03-08 16:00:40',1.800000,'467241156'),
('1501105984649560065','1','2022-03-08 16:02:11',1.000000,'467241156'),
('1501106046339383297','1','2022-03-08 16:02:25',1.000000,'467241156'),
('1501106529481261058','1','2022-03-08 16:04:21',1.000000,'467241156'),
('1501106710129934338','1','2022-03-08 16:05:04',1.000000,'467241156'),
('1501107366307823618','1','2022-03-08 16:07:40',1.000000,'467241156'),
('1501107390588649474','1','2022-03-08 16:07:46',1.000000,'467241156'),
('1501107506196250625','1','2022-03-08 16:08:13',1.000000,'467241156'),
('1501109287240335361','1','2022-03-08 16:15:18',2.000000,'467241156'),
('1501109963219533825','1','2022-03-08 16:17:59',2.000000,'467241156'),
('1501110161761107970','1','2022-03-08 16:18:47',1.000000,'467241156'),
('1501110170946633730','1','2022-03-08 16:18:49',2.000000,'467241156'),
('1529535258263826433','1515247727770865665','2022-05-26 02:49:58',100.000000,'岚'),
('1529538472480616449','1515247727770865665','2022-05-26 03:02:44',1.000000,'岚'),
('1530973103830720514','1515247727770865665','2022-05-30 02:03:27',10.000000,'岚');

/*Table structure for table `ktv_room` */

DROP TABLE IF EXISTS `ktv_room`;

CREATE TABLE `ktv_room` (
  `room_id` varchar(32) NOT NULL COMMENT '房间id',
  `room_name` varchar(32) DEFAULT NULL COMMENT '房间名',
  `room_opentime` datetime DEFAULT NULL COMMENT '房间开通时间',
  `room_endtime` datetime DEFAULT NULL COMMENT '房间结束时间',
  `room_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '房间创建时间',
  `room_updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '房间上次使用时间',
  `room_usetime` int DEFAULT NULL COMMENT '房间总使用时间',
  `room_isopen` tinyint(1) DEFAULT NULL COMMENT '房间是否开启',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv房间表';

/*Data for the table `ktv_room` */

insert  into `ktv_room`(`room_id`,`room_name`,`room_opentime`,`room_endtime`,`room_createtime`,`room_updatetime`,`room_usetime`,`room_isopen`) values 
('1a4e2a36f23f4748b9a6fdbcd16802a1','房间1','2022-05-30 12:08:00','2022-06-04 01:08:00','2022-03-23 13:54:06','2022-05-30 02:09:04',392400000,1),
('50bc3bad97ce4334a37e228ea6f4ad43','房间3',NULL,NULL,'2022-05-30 12:06:53','2022-05-30 12:06:53',0,0),
('a8c3f142b70e4f169270d1b65177c70b','房间2',NULL,NULL,'2022-03-25 07:27:47','2022-03-25 07:27:47',NULL,NULL);

/*Table structure for table `ktv_song` */

DROP TABLE IF EXISTS `ktv_song`;

CREATE TABLE `ktv_song` (
  `song_id` varchar(32) NOT NULL COMMENT '歌曲id',
  `song_name` varchar(255) DEFAULT NULL COMMENT '歌曲名',
  `song_url` varchar(255) DEFAULT NULL COMMENT '歌曲地址',
  `song_length` int DEFAULT NULL COMMENT '歌曲时长',
  `song_author` varchar(255) DEFAULT NULL COMMENT '歌曲作者',
  `song_album` varchar(255) DEFAULT NULL COMMENT '歌曲专辑',
  `song_type` varchar(255) DEFAULT NULL COMMENT '歌曲类型',
  `song_language` varchar(255) DEFAULT NULL COMMENT '歌曲语种',
  `song_singer` varchar(255) DEFAULT NULL COMMENT '歌曲演唱者',
  `song_lyricist` varchar(255) DEFAULT NULL COMMENT '歌词作者',
  `song_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '歌曲上传时间',
  `song_updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '歌曲更新时间',
  PRIMARY KEY (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv歌曲表';

/*Data for the table `ktv_song` */

insert  into `ktv_song`(`song_id`,`song_name`,`song_url`,`song_length`,`song_author`,`song_album`,`song_type`,`song_language`,`song_singer`,`song_lyricist`,`song_createtime`,`song_updatetime`) values 
('1511200016293724162','五月天-噢买尬(MTV)[国语].mp4','',209,'阿信','后青春期的诗','流行','chinese','五月天','五月天','2022-04-05 12:32:15','2022-04-05 12:32:15'),
('1512774605994041346','无印良品-猜测(MTV)[国语].mp4','',281,'无印良品','无印良品','流行','chinese','无印良品','无印良品','2022-04-09 20:49:06','2022-04-09 20:49:06'),
('1512774757756542977','香香-香飘飘(MTV)[国语].mp4','',228,'香香','香香','流行','chinese','香香','香香','2022-04-09 20:49:42','2022-04-09 20:49:42'),
('1512774958672093185','信乐团-爱情三十六计[国语].mp4','',225,'信乐团','信乐团','流行','chinese','信乐团','信乐团','2022-04-09 20:50:30','2022-04-09 20:50:30'),
('1512775423866544130','小沈阳-大海-国语-531703.mp4','',134,'小沈阳','小沈阳','流行','chinese','小沈阳','小沈阳','2022-04-09 20:52:21','2022-04-09 20:52:21'),
('1512775507282862082','伍佰-挪威的森林.mp4','',389,'伍佰','伍佰','流行','chinese','伍佰','伍佰','2022-04-09 20:52:41','2022-04-09 20:52:41'),
('1512775867653267458','徐海涛-不想再说我爱你.mp4','',307,'徐海涛','徐海涛','流行','chinese','徐海涛','徐海涛','2022-04-09 20:54:07','2022-04-09 20:54:07'),
('1512775959449804802','吴淑敏-阮是用心来爱你[国语].mp4','',209,'吴淑敏','吴淑敏','摇滚','chinese','吴淑敏','吴淑敏','2022-04-09 20:54:29','2022-04-09 20:54:29'),
('1512776009533988866','伍佰-我不是天使(演唱会)[国语].mp4','',298,'伍佰','伍佰','摇滚','chinese','伍佰','伍佰','2022-04-09 20:54:41','2022-04-09 20:54:41'),
('1512832968224251905','吴听撤-怎么可能会寂寞[国语].mp4','',267,'吴听撤','吴听撤','流行','chinese','吴听撤','吴听撤','2022-04-10 00:41:01','2022-04-10 00:41:01'),
('1512833061664956418','吴克群-将军令(MTV)[国语].mp4','',230,'吴克群','吴克群','流行','chinese','吴克群','吴克群','2022-04-10 00:41:23','2022-04-10 00:41:23'),
('1512833126366289921','五月天-倔强(MTV)[国语].mp4','',264,'五月天','五月天','流行','chinese','五月天','五月天','2022-04-10 00:41:39','2022-04-10 00:41:39'),
('1512833241130835970','萧煌奇-只能勇敢-国语-905173.mp4','',320,'萧煌奇','萧煌奇','流行','chinese','萧煌奇','萧煌奇','2022-04-10 00:42:06','2022-04-10 00:42:06'),
('1512833312731799553','弦子-醉清风(MTV)[国语].mp4','',263,'弦子','弦子','流行','chinese','弦子','弦子','2022-04-10 00:42:23','2022-04-10 00:42:23'),
('1512833371561107457','五月天-孙悟空(MTV)[国语].mp4','',254,'五月天','五月天','流行','chinese','五月天','五月天','2022-04-10 00:42:37','2022-04-10 00:42:37'),
('1512833570824101890','香香-猪之歌(MTV)[国语].mp4','',200,'香香','香香','流行','chinese','香香','香香','2022-04-10 00:43:25','2022-04-10 00:43:25'),
('1512833652273291266','信乐团-死了都要爱[国语].mp4','',274,'信乐团','信乐团','流行','chinese','信乐团','信乐团','2022-04-10 00:43:44','2022-04-10 00:43:44'),
('1512833707487109121','吴克群-为你写诗(MTV)[国语].mp4','',298,'吴克群','吴克群','流行','chinese','吴克群','吴克群','2022-04-10 00:43:57','2022-04-10 00:43:57'),
('1512833816698396674','吴建豪-想像十个你(演唱会)[国语].mp4','',321,'吴建豪','吴建豪','流行','chinese','吴建豪','吴建豪','2022-04-10 00:44:23','2022-04-10 00:44:23'),
('1512833927386079233','伍思凯-分享(演唱会)[国语](VCD).mp4','',197,'伍思凯','伍思凯','流行','chinese','伍思凯','伍思凯','2022-04-10 00:44:50','2022-04-10 00:44:50'),
('1530607768724078594','《M八七》米津玄師.mp4','',279,'米津玄師','米津玄師','流行','japanese','米津玄師','米津玄師','2022-05-29 01:51:44','2022-05-29 01:51:44'),
('1530611099215364097','Wonder Girls《 Nobody》','',334,'Wonder Girls','Wonder Girls','流行','korean','Wonder Girls','Wonder Girls','2022-05-29 02:04:58','2022-05-29 02:04:58'),
('1530611204177821697','《死一样痛过》MC梦_Jamie','',268,'MC梦_Jamie','MC梦_Jamie','流行','korean','MC梦_Jamie','MC梦_Jamie','2022-05-29 02:05:23','2022-05-29 02:05:23'),
('1530611355806105601','艾薇儿《Girlfriend》','',227,'艾薇儿','艾薇儿','流行','english','艾薇儿','艾薇儿','2022-05-29 02:05:59','2022-05-29 02:05:59'),
('1530611565085097985','《Insomnia》','',206,'克雷格·大卫','Greatest Hits','说唱','english','克雷格·大卫','克雷格·大卫','2022-05-29 02:06:49','2022-05-29 02:06:49'),
('1530611858992562178','Fort Minor - 《Remember The Name》','',227,'Fort Minor','Fort Minor','说唱','english','Fort Minor','Fort Minor','2022-05-29 02:07:59','2022-05-29 02:07:59'),
('1530612507725561858','卡农','',367,'乔治温斯顿','乔治温斯顿','纯音乐','english','乔治温斯顿','乔治温斯顿','2022-05-29 02:10:33','2022-05-29 02:10:33'),
('1530612628378910721','少女时代《Gee》','',197,'少女时代','少女时代','流行','korean','少女时代','少女时代','2022-05-29 02:11:02','2022-05-29 02:11:02'),
('1531106518202707970','吴克群-爱我恨我[国语].mp4','',315,'吴克群','吴克群','流行','chinese','吴克群','吴克群','2022-05-30 10:53:35','2022-05-30 10:53:35'),
('1531124817858785281','吴鑫桐-完美绝配[国语].mp4','',218,'吴鑫桐','吴鑫桐','流行','chinese','吴鑫桐','吴鑫桐','2022-05-30 12:06:18','2022-05-30 12:06:18');

/*Table structure for table `ktv_user` */

DROP TABLE IF EXISTS `ktv_user`;

CREATE TABLE `ktv_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `user_nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `user_username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `user_registertime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
  `user_updatetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户最后登录时间',
  `user_balance` decimal(24,6) DEFAULT NULL COMMENT '用户余额',
  `user_playtime` decimal(24,6) DEFAULT NULL COMMENT '用户游玩时间',
  `user_birthday` date DEFAULT NULL COMMENT '用户生日',
  `user_status` varchar(255) DEFAULT NULL COMMENT '用户权限(-1封禁，0普通用户，1管理员)',
  `user_wechat_openid` varchar(255) DEFAULT NULL COMMENT '用户绑定微信id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv用户表';

/*Data for the table `ktv_user` */

insert  into `ktv_user`(`user_id`,`user_nickname`,`user_username`,`user_password`,`user_registertime`,`user_updatetime`,`user_balance`,`user_playtime`,`user_birthday`,`user_status`,`user_wechat_openid`) values 
('1','张三','467241156','vbhgnmccw4672','2022-02-23 17:17:47','2022-02-23 17:17:50',428.000000,10.000000,'2022-02-23','1',NULL),
('1515247727770865665','岚',NULL,NULL,'2022-04-16 16:36:24','2022-05-30 12:07:26',121.000000,0.000000,'2000-04-25',NULL,'oeb-85ApADMSWSQKRMw_x3_qA9tE'),
('2','游客','guest','guest','2022-03-29 23:06:13','2022-03-29 23:06:18',0.000000,0.000000,'2022-03-29','0',NULL),
('3','李四','lisi','lisi','2022-04-28 08:43:12','2022-04-28 08:43:12',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `ktv_user_song` */

DROP TABLE IF EXISTS `ktv_user_song`;

CREATE TABLE `ktv_user_song` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `userid` varchar(255) DEFAULT NULL COMMENT '用户id',
  `songid` varchar(255) DEFAULT NULL COMMENT '歌曲id',
  `times` int DEFAULT NULL COMMENT '用户播放次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv用户歌曲表';

/*Data for the table `ktv_user_song` */

insert  into `ktv_user_song`(`id`,`userid`,`songid`,`times`) values 
('1516919729590128642','1515247727770865665','1511200016293724162',3),
('1517163000341987330','1515247727770865665','1512774605994041346',1),
('1517218842827292674','2','1511200016293724162',2),
('1519477934853804034','3','1512832968224251905',1),
('1519477955921793026','3','1512833126366289921',1),
('1519477972426379265','3','1512833371561107457',1),
('1519492486408519682','1515247727770865665','1512832968224251905',1),
('1519492504121065473','1515247727770865665','1512775715240648705',1),
('1519837275448475650','2','1512775715240648705',1),
('1519837320990228481','2','1512833927386079233',1),
('1519837341613621249','2','1512775959449804802',1);

/*Table structure for table `ktv_wechat_user` */

DROP TABLE IF EXISTS `ktv_wechat_user`;

CREATE TABLE `ktv_wechat_user` (
  `id` varchar(255) NOT NULL COMMENT '用户编号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ktv微信用户表';

/*Data for the table `ktv_wechat_user` */

/*Table structure for table `user_room_usering` */

DROP TABLE IF EXISTS `user_room_usering`;

CREATE TABLE `user_room_usering` (
  `user_room_id` varchar(32) NOT NULL COMMENT '用户房间id',
  `user_room_userid` varchar(255) DEFAULT NULL COMMENT '用户id',
  `user_room_roomid` varchar(255) DEFAULT NULL COMMENT '房间id',
  `user_room_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户房间表创建时间',
  `user_room_updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户房间表更新时间',
  `user_room_status` int DEFAULT NULL COMMENT '用户房间状态【1正在使用】【0结束使用】',
  PRIMARY KEY (`user_room_id`),
  KEY `user_room_userid_fk` (`user_room_userid`),
  KEY `user_room_roomid_fk` (`user_room_roomid`),
  CONSTRAINT `user_room_roomid_fk` FOREIGN KEY (`user_room_roomid`) REFERENCES `ktv_room` (`room_id`),
  CONSTRAINT `user_room_userid_fk` FOREIGN KEY (`user_room_userid`) REFERENCES `ktv_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户房间使用表';

/*Data for the table `user_room_usering` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
