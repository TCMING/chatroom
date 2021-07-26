package com.uestc.controllerteam.chartservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

	private int id;

	private String username;

	private int roomId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String phone;

}
