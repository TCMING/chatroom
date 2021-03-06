package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.model.UserResponse;
import com.uestc.controllerteam.chartservice.service.UserService;
import com.uestc.controllerteam.chartservice.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController  extends AbstractController{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@PassToken
	@RequestMapping(value="/user",method = {POST})
	public void createUser(@RequestBody UserRequest request) {
		boolean res = userService.registryUser(request);
		BizCheckUtils.check(res,"保存用户失败");
	}

	@PassToken
	@RequestMapping(value="/userLogin",method = {GET})
	public String userLogin(@RequestParam(value="username")String username, @RequestParam(value="password")String password) {
		if(!userService.userPasswordCheck(username, password))
			throw new ChatException("Invalid username or password.");

		String jwtToken = JwtUtils.createToken(username);
		return GsonUtils.toJsonString(jwtToken);
	}

	@PassToken
	@RequestMapping(value="/user/{username}",method = {GET})
	public String userInfo(@PathVariable String username) {
		BizCheckUtils.checkNull(username, "Invalid username supplied");
		try {
			UserDto userDto = userService.queryUserByName(username);
			BizCheckUtils.checkNull(userDto,"Invalid username supplied");
			UserResponse userResponse = new UserResponse(userDto.getFirstName(),userDto.getLastName(),
						userDto.getEmail(),userDto.getPhone());
			return GsonUtils.toJsonString(userResponse);
		}catch (Exception e){
			throw new ChatException("Invalid username supplied");
		}
	}


}
