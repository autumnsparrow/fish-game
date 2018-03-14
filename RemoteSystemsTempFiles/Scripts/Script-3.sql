create table ty_vit_log (
	id bigint auto_increment ,
	user_id bigint,
	vit int,
	online int,
	
	
	update_time timestamp  null default null on update current_timestamp comment '',
	create_time timestamp not null default 0 comment 'order date.',
	primary key (id)
)engine=InnoDB default charset=utf8;
		