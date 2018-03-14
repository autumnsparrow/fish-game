delimiter $$

CREATE TABLE ty_user_base_info (
	id bigint(20) NOT NULL AUTO_INCREMENT,

	user_id bigint(20) comment '用户id',
	gold int default 0 comment '金币',
	jewel int default 0 comment '钻石',
	exp bigint default 0 comment '经验',
	vit bigint default 0 comment '体力',
	gold_key int default 0 comment '金钥匙',
	sliver_key int default 0 comment '银钥匙',
	copper_key int default 0 comment '铜钥匙',

	rod_id bigint default 0 comment '钓杆',
	line_id bigint default 0 comment '鱼线',
	reel_id bigint default 0 comment '卷线轮',
	lure_id bigint default 0 comment '饵',
	drug_id_1 bigint default 0 comment '药水1',
	drug_id_2 bigint default 0 comment '药水2',
	drug_id_3 bigint default 0 comment '药水2',
	
	addition_damage int default 0 comment '伤害附加',
	addition_line int default 0 comment '鱼线附加',
  
  create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$



CREATE TABLE ty_consumable_item (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	user_id bigint(20) comment '用户id',

	item_id varchar(50) comment '物品id(string)',
	amount int comment '数量',
	sell_gold int comment '出售金币',
	backpack_sequence int comment '背包顺序',
	equip int  comment '1:已装备,0未装备',
	
  create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$


CREATE TABLE ty_equipment_ablility (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	consume_item_id bigint comment '物品id',
	name varchar(45) comment '装备加成类型(string)',
	value int comment '装备加成数值',
	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$



CREATE TABLE ty_repairable_item(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	consume_item_id bigint comment '物品id',
	item_type int comment '物品类型()',
	endure int comment '耐久度',
	repairable int comment '是否可修复',
	damage int comment '伤害',
	repair_gold int comment '修复金币',
	upgrade_jewel int comment '升级钻石',
	upgrade_level int comment '当前等级',

  create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$


CREATE TABLE ty_equipment_upgrade (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	consume_item_id bigint comment '物品id',
  
	count int comment '升级金币或钻石数量',
	damage int comment '附加伤害',
	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  
	PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$





CREATE TABLE ty_backpack (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	
	total_capcity int comment '背包总格数',
	used_capcity int comment '已使用格数',
	upgrade_gold int comment '升级金币',
	upgrade_jewel int comment '升级钻石',
	upgrade_capcity int comment '升级后格数',
	
	
	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  
	PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8$$






	




