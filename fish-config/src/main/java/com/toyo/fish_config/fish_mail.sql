create table ty_mail(
	
    id bigint auto_increment comment 'primary key id' ,
    title varchar(50) comment 'mail title',
    sender bigint comment 'foreign key of user',
    content varchar(200) comment 'mail content',
    category int comment 'mail category(0=mail,1=vit mail,2=friend request mail)',
    attachment_state int comment ' mail attachment state(0=normal mail,1=mail with attachment)',
    attachments varchar(200) comment 'mail attachments List<Attachment{id,type=(1=item,2=action),value}>',
    
    time_trigger_expression varchar(50) comment 'mail cron expression of active',
    active_hours int comment 'mail active life  N hours',
    
    trigger_expressionty_mail varchar(100) comment 
    'trigger expression json format. {userLevel:([:5],[1:5],[5:]),apk_version:true,login_after:(5)}',
    
    filter_state int default 0 comment 
    '0= need to schedule trigger_expression analysize,1=execute trigger_expression,2=expired,3,cleaned up',
    cleanup_state int default 0 comment 
    '0,do not deal with MailTriggerLog ,1=need to deal with MailTriggerLog clean,2=cleaned MailTriggerLog',
    
    update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
    primary key (id),
    index idx_time_trigger_exp (time_trigger_expression),
    index idx_filter_state (filter_state),
    index idx_clean_state (cleanup_state)
    
    
)engine=NDB default charset=utf8;


create table ty_mail_trigger_log (
id bigint auto_increment comment 'primary key id' ,
mail_id bigint comment 'mail id',
reciever  bigint comment 'user_id',
state int comment 'mail state (0=unread,1=read)',
category int comment 'mail category',

update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
primary key (id),
index idx_mail_id (mail_id),
index idx_reciever(reciever,state,category)
)engine=NDB default charset=utf8;



alter table ty_mail add expired_state int default 0 ;


create table ty_mail_trigger_user_information (
id bigint auto_increment comment 'primary key id' ,
user_id bigint comment 'user id',
last_login timestamp comment 'user last login',
last_apk_version int comment 'last apk version',

update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
create_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
primary key (id),
index idx_mail_user_id (user_id)
)engine=NDB default charset=utf8;







