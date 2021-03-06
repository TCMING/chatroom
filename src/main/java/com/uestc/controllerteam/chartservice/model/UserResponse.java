package com.uestc.controllerteam.chartservice.model;

import lombok.Data;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 15:23
 * @Version 1.0
 */
@Data
public class UserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    public UserResponse(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
