-- user account

create table ty_user_account(
id bigint not null auto_increment,

device_id  varchar(50) not null comment '设备id',
user_name varchar(50)  comment '用户名',
passwd varchar(80)  comment '密码(验证md5)',
phone varchar(20)  comment '用户手机号',
channel_id int not null comment '用户渠道',
zone_id int not null comment '用户分区',

create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key (id)
)engine=memory default charset=utf8;


-- user by channel
create table ty_third_party_account(
id bigint not null auto_increment,
user_id bigint not null comment  '',
account varchar(20) not null comment '用户名称',
create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key(id)
)engine=memory default charset=utf8;


-- user by channel
create table ty_channel(
id bigint not null auto_increment,

name varchar(20) not null comment '用户渠道名称',
create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key(id)
)engine=memory default charset=utf8;

-- user by zone
create table ty_zone(
id bigint not null auto_increment,
name varchar(20) not null,
ip varchar(45) not null,
port int not null,
active int default 0  comment '注册活跃区',
create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key(id)
)engine=memory default charset=utf8;


-- 
create table ty_login(
id bigint not null auto_increment,

user_id bigint not null comment '用户id',
token   varchar(100) not null comment '用户登录token',
device_id varchar(45) not null comment '用户设备id',
connection_id varchar(45),
des_key varchar(100) not null,

create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key(id)
)engine=memory default charset=utf8;



create table ty_valid_code	(
id bigint not null auto_increment,

user_id bigint not null comment '用户id',
valid_code varchar(45) not null comment '用户验证码id',

create_time timestamp  not null default 0 comment '',
update_time timestamp  null default null on update current_timestamp comment '',
primary key(id)
)engine=memory default charset=utf8;


insert into ty_zone (name,ip,port,create_time,active)values('zone0','127.0.0.1',9000,current_timestamp(),1);





