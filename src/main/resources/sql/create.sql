create DATABASE  chatService;
use chatService;

drop table room;
drop table user;
drop table message;

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
    roomId int NULL default 0 ,
    username VARCHAR(200) NOT NULL,
    firstName VARCHAR(200) NOT NULL,
    lastName VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    phone VARCHAR(200) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE KEY(username),
    key(roomId)
)ENGINE = innoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '用户';



create table message(
    id VARCHAR(200) NOT NULL,
    text varchar(200) NOT NULL,
    timestamp bigint NOT NULL,
    roomId int not null,
    username VARCHAR(200) NOT NULL,
    primary key(id),
    KEY(roomId,timestamp)
)ENGINE = innoDB
DEFAULT CHARSET = utf8
COMMENT = '消息';





