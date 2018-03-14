delimiter $$

CREATE TABLE ty_item_lure (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	item_id varchar(50) comment '物品id(key)',
	count int comment '数量',
	
	
	fish_size_radio int comment '鱼大小',
	star int comment '星级',
	rare int comment '稀有度',
	addition int comment '加成',
	lure_type int comment '铒类型',
	open_level int comment '开放等级',
	
	buy_gold int comment '购买金币',
	buy_jewel int comment '购买钻石',
	shop_sequence int comment '商店顺序',
	show_sequence int comment '显示顺序',
	
	sell_gold int comment '出售金币',


	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$




CREATE TABLE ty_item_line (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	item_id varchar(50) comment '物品id(key)',
	count int comment '数量',
	buy_gold int comment '购买金币',
	buy_jewel int comment '购买钻石',
	sell_gold int comment '出售金币',
	fish_size_radio int comment '鱼大小',
	star int comment '星级',
	rare int comment '稀有度',
	addition int comment '加成',
	lure_type int comment '铒类型',
	open_level int comment '开放等级',
	shop_sequence int comment '商店顺序',
	show_sequence int comment '显示顺序',
	line_length int comment '鱼线长度',
	line_tension int comment '鱼线张力',
	line_color int comment '鱼线颜色',
	priority int comment '优先级',



	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$



CREATE TABLE ty_item_reel (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	item_id varchar(50) comment '物品id(key)',
	count int comment '数量',
	buy_gold int comment '购买金币',
	buy_jewel int comment '购买钻石',
	sell_gold int comment '出售金币',
	fish_size_radio int comment '鱼大小',
	star int comment '星级',
	rare int comment '稀有度',
	addition int comment '加成',
	lure_type int comment '铒类型',
	open_level int comment '开放等级',
	shop_sequence int comment '商店顺序',
	show_sequence int comment '显示顺序',

	repairable int comment '是否可修复',
	upgrade_gold int comment '升级金币',
	upgrade_jewel int comment '升级钻石',
	link_chest int comment '关联宝箱',
	open_chest_level int comment '开宝箱等级',
	damage int comment '伤害',


	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$




CREATE TABLE ty_item_rod (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	item_id varchar(50) comment '物品id(key)',
	count int comment '数量',
	buy_gold int comment '购买金币',
	buy_jewel int comment '购买钻石',
	sell_gold int comment '出售金币',
	fish_size_radio int comment '鱼大小',
	star int comment '星级',
	rare int comment '稀有度',
	addition int comment '加成',
	lure_type int comment '铒类型',
	open_level int comment '开放等级',
	shop_sequence int comment '商店顺序',
	show_sequence int comment '显示顺序',

	repairable int comment '是否可修复',
	upgrade_gold int comment '',
	upgrade_jewel int comment '',
	link_chest int comment '',
	open_chest_level int comment '',
	damage int comment '',


	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$



CREATE TABLE ty_user_level (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	level int comment '等级',
	next_level_exp int comment '下级经验',
	this_level_exp int comment '本级经验',
	damage int comment '伤害',
	line_length_addition  int comment '鱼线长度加成',


	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$

CREATE TABLE ty_inventory_extend_data(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	current_capcity int comment '当前容量',
	upgrade_capcity int comment '升级后容量',
	upgrade_jewel int comment '升级钻石',
	upgrade_gold int comment '升级金币',


	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MyISAM  CHARSET=utf8$$



CREATE TABLE `ty_resource_update` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` bigint(20) NOT NULL COMMENT 'channel id',
  `version` varchar(100) NOT NULL COMMENT '用户登录token',
  `url` varchar(100) NOT NULL COMMENT '用户设备id',
  `md5` varchar(100) NOT NULL COMMENT 'md5',
  `active` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `force_update` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8$$
