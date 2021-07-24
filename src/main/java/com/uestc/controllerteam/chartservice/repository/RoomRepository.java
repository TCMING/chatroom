package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.RoomDao;
import com.uestc.controllerteam.chartservice.dao.RoomRedisDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.model.PageDto;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 15:35
 * @Version 1.0
 */
@Service
public class RoomRepository {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomRedisDao roomRedisDao;

    //暂时不用内存淘汰，维护全量的room信息
    private final ConcurrentHashMap<Integer,String> roomsCache = new ConcurrentHashMap<>(1000);

    /**
     * TODO 增加事务保证
     * @param name
     * @return
     */
    public String saveRoom(String name){
        RoomDto roomDto = new RoomDto();
        roomDto.setName(name);
        roomDao.insert(roomDto);
        roomsCache.put(roomDto.getId(),name);
        return String.valueOf(roomDto.getId());
    }

    public List<RoomDto> queryRoomRecord(QueryControlData controlData){
        int startIndex = controlData.getPageIndex()*controlData.getPageSize();
        return roomDao.queryRoomRecord(new PageDto(startIndex,controlData.getPageSize()));
    }

    public List<RoomDto> queryAll(){
        List<RoomDto> roomDtos = roomDao.queryAll();
//        List<RoomDto> roomDtos = new LinkedList<>();
//        roomsCache.forEachEntry(roomsCache.size(), new Consumer<Map.Entry<Integer, String>>() {
//            @Override
//            public void accept(Map.Entry<Integer, String> entry) {
//                RoomDto roomDto = new RoomDto(entry.getKey(),entry.getValue());
//                roomDtos.add(roomDto);
//            }
//        });
        return roomDtos;
    }

    public RoomDto queryRoomById(int roomId){
        String roomName = roomsCache.getOrDefault(roomId,null);
        if(StringUtils.isNotBlank(roomName)){
            return new RoomDto(roomId,roomsCache.get(roomId));
        }
        RoomDto roomDto = roomDao.queryRoomById(roomId);
        if(roomDto != null){
            roomsCache.put(roomDto.getId(),roomDto.getName());
        }
        return roomDto;
    }

    public RoomDto queryRoomByName(String name){
        return roomDao.queryRoomByName(name);
    }

}
