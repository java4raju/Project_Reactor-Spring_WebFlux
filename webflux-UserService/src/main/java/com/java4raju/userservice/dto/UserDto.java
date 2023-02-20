package com.java4raju.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private Integer balance;
	
    public UserDto(String name, Integer balance) {
		this.name = name;
		this.balance = balance;
	}
}
