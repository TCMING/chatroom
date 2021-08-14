 2021.7.23：  
 待确认/优化：  
 sql分页:order by 、 limit  目前没有发现可以优化的点  
 （1）SELECT * FROM room WHERE 1=1 ORDER BY room.id asc LIMIT 100000,100; 未优化，通过expalin看,使用了主键、也没有额外排序。
  但extra字段为null，同类型的sql如果order by普通索引，extra字段为using index。老田要去确认下
 （2）select id, text, CONCAT(timestamp,'') from message where roomId = 1 order by timestamp desc LIMIT 100000,100;问题同上  
 token 目前验证token有问题  
 貌似压测工具比较呆板，目前基本没考虑并发安全，但是没遇到问题，所以考虑1.要不要去掉syn 2.业务逻辑是不是可以不做某些检查    
 缓存：  
 （1）enterRoom、roomLeave涉及到是否缓存在线用户的问题、目前还没使用到缓存    
 enterRoom中缓存用户列表线程安全问题，原hash,暂改concurrentHash ; 内存or redis
 接口幂等、并发安全  
 内存、redis、mysql数据一致性  


//缓存
1.load全量 user信息，room信息   room-user关系
2.conhashmap 中存放集合线程安全吗  
3.redis 事务  

初赛总结：
1.架构：tomcat-springboot-luttuce(nio单连接)-redis(aof-always)  
2.性能优化点：  
(1)redis作为db，开启aof-always，基准测试写3w/s，实测接口9k/s，远远好于mysql  
(2)luttuce配套jackson2JsonRedisSerializer序列化，性能和空间表现都比较好，接口性能实测比GenericJackson2JsonRedisSerializer快2-3倍  
(3)内存中concurrentHashMap 初始化容量寻找最佳值，性能差距在2-3倍  
(4)tomcat参数调优、controller接口json序列化方式调整、批量落库、redis pipeline等对性能提升不明显（可能瓶颈不在此）