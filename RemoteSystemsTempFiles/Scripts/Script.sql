
use toyo_payment;
create table tbl_flyme_notify (
	id bigint auto_increment ,
	notify_time timestamp null,
	notify_id varchar(50) ,
	order_id bigint,
	app_id bigint,
	uid bigint,
	parttern_id varchar(50),
	local_order_id varchar(50),
	product_id varchar(50),
	product_unit varchar(20),
	buy_amount int,
	product_per_price decimal(5,2),
	total_price  decimal(5,2),
	trade_status varchar(50),
	pay_time timestamp,
	pay_type int,
	user_info varchar(50),
	sign varchar(200),
	sign_type varchar(20),
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	
	primary key (id)
)engine=InnoDB default charset=utf8;