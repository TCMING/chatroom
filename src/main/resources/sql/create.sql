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


explain
       SELECT
            *
        FROM
            room
        WHERE 1=1
            ORDER BY room.id asc
        LIMIT 100000,100;


explain select *
        from room
        order by name desc
        LIMIT 100000,100;

explain select id
        from room
        order by name desc
        LIMIT 100000,100;


explain
    select id, text, CONCAT(timestamp,'')
        from message
        where roomId = 1
        order by timestamp desc
        LIMIT 100000,100;




