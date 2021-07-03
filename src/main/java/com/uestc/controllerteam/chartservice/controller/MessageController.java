package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.service.MessageService;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MessageController  extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageService messageService;

	@RequestMapping(value="/message/send")
	public boolean receive(@RequestBody MessageRetrive message) {
		//获取人信息
		UserRequest userRequest = new UserRequest();
		return messageService.recvMessage(userRequest,message.getId(),message.getText());
	}

	@RequestMapping(value="/message/retrieve")
	public String pullMessage(@RequestBody QueryControlData queryControlData){
		//获取人信息
		UserRequest          userRequest     = new UserRequest();
		List<MessageRetrive> messageRetrives = messageService.pullMessage(userRequest,queryControlData);
		return GsonUtils.toJsonString(messageRetrives);
	}

}
