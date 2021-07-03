

## 数据库
我到底更爱Spurs还是Eason:
create DATABASE  chatService;
use chatService;

drop table room;
drop TABLE user;
drop TABLE message;


select * from room;
select * from user;
select * from message;


create table room(
  id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  PRIMARY KEY(id),
  key(name)
)ENGINE = innoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '房间';



create table user(
    id int NOT NULL AUTO_INCREMENT,
    username VARCHAR(200) NOT NULL,
    firstName VARCHAR(200) NOT NULL,
    lastName VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    phone VARCHAR(200) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE KEY(username)
)ENGINE = innoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '用户';



create table message(
    id BIGINT NOT NULL,
    text varchar(200) NOT NULL,
    timestamp varchar(200) NOT NULL,
    userId int not null,
    roomId int not null,
    primary key(id),
    UNIQUE KEY(userId,roomId)
)ENGINE = innoDB
DEFAULT CHARSET = utf8
COMMENT = '消息';

insert into room (name) 
  value('one');

