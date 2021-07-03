package com.uestc.controllerteam.chartservice.dto;

import lombok.Data;

@Data
public class UserDto{

	private int id;

	private String username;

	private int roomId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String phone;

}
