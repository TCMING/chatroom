package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.RoomDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.model.RoomControlData;
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

    public boolean saveRoom(String name){
        return roomDao.insert(name) == 1;
    }

    public List<RoomDto> queryRoomRecord(RoomControlData controlData){
        return roomDao.queryRoomRecord(controlData);
    }

    public List<RoomDto> queryAll(){
        return roomDao.queryAll();
    }

}
