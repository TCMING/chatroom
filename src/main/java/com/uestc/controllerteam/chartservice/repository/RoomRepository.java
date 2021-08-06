package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.RoomDao;
import com.uestc.controllerteam.chartservice.dao.RoomRedisDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.RoomVo;
import com.uestc.controllerteam.chartservice.model.PageDto;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 15:35
 * @Version 1.0
 */
@Service
public class RoomRepository implements InitializingBean {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomRedisDao roomRedisDao;

    //暂时不用内存淘汰，维护全量的room信息
    private ConcurrentHashMap<Integer,String> roomsCache;

    /**
     * TODO 增加事务保证
     * @param name
     * @return
     */
    public String saveRoom(String name){
        RoomDto roomDto = new RoomDto();
        roomDto.setName(name);
        int roomId = roomRedisDao.createRoomId();
        roomDto.setId(roomId);
//        roomDao.insert(roomDto);
        roomRedisDao.createRoom(roomDto);
        try {
            roomsCache.put(roomId,name);
        } catch (Exception e) {
            roomsCache.put(roomId,name);
        }
        return String.valueOf(roomId);
    }

    public List<RoomDto> queryRoomRecord(QueryControlData controlData){
        int startIndex = controlData.getPageIndex()*controlData.getPageSize();
//        List<RoomVo> roomVos = new ArrayList<>();
        List<RoomDto> roomDtos = roomRedisDao.queryRoomRecord(startIndex,controlData.getPageSize());
//        if(!CollectionUtils.isEmpty(roomDtos)){
//            for(RoomDto roomDto: roomDtos){
//                roomVos.add(new RoomVo(String.valueOf(roomDto.getId()),roomDto.getName()));
//            }
//        }
//        return roomVos;
        return roomDtos;
    }


    public RoomDto queryRoomById(int roomId){
        String roomName = roomsCache.getOrDefault(roomId,null);
        if(StringUtils.isNotBlank(roomName)){
            return new RoomDto(roomId,roomName);
        }
        return null;
//        RoomDto roomDto = roomDao.queryRoomById(roomId);
//        if(roomDto != null){
//            roomsCache.put(roomDto.getId(),roomDto.getName());
//        }
//        return roomDto;
    }

    @Deprecated
    public RoomDto queryRoomByName(String name){
        return roomDao.queryRoomByName(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        roomRedisDao.initRoomId();
        roomsCache = new ConcurrentHashMap<>(2048);
//        List<RoomDto> roomDtos = roomDao.queryAll();
        List<RoomDto> roomDtos = this.queryAll();
        if(!CollectionUtils.isEmpty(roomDtos)){
            for(RoomDto roomDto: roomDtos){
                roomsCache.put(roomDto.getId(),roomDto.getName());
            }
        }
    }

    public List<RoomDto> queryAll(){
//        List<RoomDto> roomDtos = roomDao.queryAll();
        List<RoomDto> roomDtos = roomRedisDao.queryAll();
        return roomDtos;
    }

}
