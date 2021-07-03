package com.uestc.controllerteam.chartservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.service.RoomService;

@RestController
public class HelloController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoomService roomService;

	@RequestMapping(value="/hello")
	public String hello() {
		List<RoomDto> list = roomService.queryAll();
		if(list!=null && !list.isEmpty()) {
			return list.get(0).getName();
		}else {
			logger.info("----------print hello");
			return "hello";
		}
	}

	@RequestMapping(value="/hello2")
	public String hello2() {
		return "hello";
	}

}
