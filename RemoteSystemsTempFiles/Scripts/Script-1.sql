

create table tbl_vivo_get_order (
	id bigint auto_increment ,
	order_time varchar(20) not null,
	order_amount decimal(5,2) not null,
	order_title varchar(50) not null,
	order_description varchar(50) not null,
	vivo_order varchar(50) not null,
	vivo_signature varchar(100) not null,
	
	create_time timestamp not null default current_timestamp comment '',
	update_time timestamp  null default null on update current_timestamp comment '',

	
	primary key (id)
)engine=InnoDB default charset=utf8;

