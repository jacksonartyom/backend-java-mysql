package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UserRequest {

	private String email;
	private String firstName;
	private String midName;
	private String lastName;
	private String phoneNo;
	private String password;
	private String imageProfile;
}