package com.uestc.controllerteam.chartservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDto implements Serializable{

	private String id;
	private String  text;

	private String username;
	private int roomId;

	private long timestamp;

	public MessageDto(String id, String text, String username, int roomId, Long timestamp) {
		this.id = id;
		this.text = text;
		this.username = username;
		this.roomId = roomId;
		this.timestamp = timestamp;
	}

	public MessageDto() {
	}
}
