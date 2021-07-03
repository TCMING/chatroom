package com.uestc.controllerteam.chartservice.service.impl;

import java.util.List;

import com.uestc.controllerteam.chartservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uestc.controllerteam.chartservice.dao.RoomDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Service
public class RoomServiceImpl implements RoomService {
	@Autowired
	private RoomDao roomDao;

	public List<RoomDto> queryAll(){
		return roomDao.queryAll();
	}

}
