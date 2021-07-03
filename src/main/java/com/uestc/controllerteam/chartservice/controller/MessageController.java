package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.model.User;
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
		User user = new User();
		return messageService.recvMessage(user,message.getId(),message.getText());
	}

	@RequestMapping(value="/message/retrieve")
	public String pullMessage(@RequestBody QueryControlData queryControlData){
		//获取人信息
		User user = new User();
		List<MessageRetrive> messageRetrives = messageService.pullMessage(user,queryControlData);
		return GsonUtils.toJsonString(messageRetrives);
	}

}
