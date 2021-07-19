package com.uestc.controllerteam.chartservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;

}
