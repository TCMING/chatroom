package com.uestc.controllerteam.chartservice.model;

import lombok.Data;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 15:25
 * @Version 1.0
 */
@Data
public class UserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

}
