package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.RoomDao;
import com.uestc.controllerteam.chartservice.dao.RoomRedisDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveRoom(String name){
        roomDao.insert(name);
    }

    public List<RoomDto> queryRoomRecord(QueryControlData controlData){
        return roomDao.queryRoomRecord(controlData);
    }

    public List<RoomDto> queryAll(){
        return roomDao.queryAll();
    }

    public RoomDto queryRoomById(int roomId){
        return roomDao.queryRoomById(roomId);
    }

}
