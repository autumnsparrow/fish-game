
use toyo_payment;




alter table tbl_order add notify_id bigint ;
	
	create table tbl_opp_notify (
	id bigint auto_increment ,
	
	notify_id varchar(50) not null,
	partner_order varchar(50) not null,
	product_name varchar(50) not null,
	product_desc varchar(50)  not null,
	price int  ,
	count int ,
	attach varchar(100) not null,
	
	
	sign varchar(80) not null,
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	
	primary key (id)
)engine=InnoDB default charset=utf8;
		
create table tbl_uc_notify (
	id bigint auto_increment ,
	
	trade_id varchar(50) not null,
	game_id varchar(50) not null,
	amount varchar(50) not null,
	order_id varchar(50)  not null,
	pay_type varchar(50)  not null,
	attach_info varchar(50)  not null,
	order_status varchar(30) not null,
	failed_desc varchar(100) not null,
	trade_time varchar(50) not null,
	
	sign varchar(80) not null,
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	
	primary key (id)
)engine=InnoDB default charset=utf8;

create table tbl_safe_center_notify (
	id bigint auto_increment ,
	app_key varchar(50) not null,
	product_id varchar(50) not null,
	amount int default 0,
	app_uid varchar(50) not null,
	app_ext1 varchar(80) not null,
	app_ext2 varchar(80) not null,
	user_id bigint not null,
	order_id bigint not null,
	gateway_flag varchar(30) not null,
	sign_type varchar(20) not null,
	sign_return varchar(80) not null,
	sign varchar(80) not null,
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	
	primary key (id)
)engine=InnoDB default charset=utf8;

create table tbl_order(
	id bigint auto_increment ,
	order_id varchar(50) not null comment 'app order id',
	
	imei varchar(50) not null comment 'device identifier',
	user_id varchar(50) not null comment 'game user id',
	
	product_id varchar(50) not null comment 'prodcut id',
	amount  int not null default 0 comment 'amount of the product',
	price int not null default 0 comment 'price of order',
	
	channel_id int  comment 'payment channel id',
	expire_time int comment 'order expire time',
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	state int not null default 0 comment 'order state',
	primary key (id)
)engine=InnoDB default charset=utf8;



