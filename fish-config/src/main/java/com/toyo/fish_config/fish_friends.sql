


-- friends base table.

create table ty_friends (
id bigint auto_increment comment 'primary key id' ,
update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',

user_id bigint comment 'user id ',
last_access_time timestamp comment 'user last access time',
max_friends int default 100 comment 'max friends',
current_friends int default 0 comment 'current friends',
max_friends_reuqest int default 100 comment 'max friends request',
current_friends_request int default 0 comment 'current friends request',
max_give_vit int default 100 comment 'max give vit ',
current_give_vit int default 0 comment 'every day vit gives',
max_get_vit int default 100 comment 'max get vit ,',
current_get_vit int default 0 comment 'current get vit ',
last_refresh_recomment_friends timestamp comment 'last refersh recomment',

avatar varchar(20) comment 'user avatar',
nick_name varchar(50) comment 'nick name',
user_level int comment 'user level',


primary key (id),
index idx_friends_user_id (user_id),
index idx_friends_last_refersh (last_refresh_recomment_friends)

)engine=NDB default charset=utf8;



create table ty_friends_relation (

id bigint auto_increment comment 'primary key id' ,
update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
owner_user_id bigint comment 'active user id(owner)',
friend_user_id bigint comment 'pass user id (friends)',
vit_state int comment 'vit 0=can send vit,1=alread sent',

primary key (id),
index idx_friends_relation_user_id (owner_user_id,friend_user_id)

)engine=NDB default charset=utf8;


create table ty_friend_mail_template(

id bigint auto_increment comment 'primary key id' ,
update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
mail_categroy int comment 'category of mail',
mail_title varchar(100) comment 'mail title',
mail_content varchar(200) comment 'mail content',

primary key(id)

)engine=NDB default charset=utf8;







