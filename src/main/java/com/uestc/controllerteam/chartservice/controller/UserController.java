package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.Room;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.model.UserResponse;
import com.uestc.controllerteam.chartservice.service.UserService;
import com.uestc.controllerteam.chartservice.utils.BizCheckUtils;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController  extends AbstractController{

	private Logger      logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@RequestMapping(value="/user")
	public boolean createUser(@RequestBody UserRequest request) {
		boolean res = userService.registryUser(request);
		BizCheckUtils.check(res,"保存用户失败");
		return true;
	}

	@RequestMapping(value="/userLogin",method = {GET})
	public void room(String username,String password) {
		String token = userService.userLogin(username, password);
		BizCheckUtils.checkNull(token,"用户信息异常,无法登陆");
	}

	@RequestMapping(value="/user/{username}",method = {GET})
	public String room( @PathVariable String username) {
		UserDto userDto = userService.queryUserByName(username);
		UserResponse userResponse = new UserResponse(userDto.getFirstName(),userDto.getLastName(),
				userDto.getEmail(),userDto.getPhone());
		return GsonUtils.toJsonString(userResponse);
	}


}
