package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController  extends AbstractController{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RoomService roomService;

	@RequestMapping(value="/user")
	public String hello() {
		List<RoomDto> list = roomService.queryAll();
		if(list!=null && !list.isEmpty()) {
			return list.get(0).getName();
		}else {
			logger.info("----------print hello");
			return "hello";
		}
	}

}
