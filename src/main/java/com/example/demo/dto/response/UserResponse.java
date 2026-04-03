package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String userId;
	private String email;
	private String firstName;
	private String midName;
	private String lastName;
	private String phoneNo;
	private String token;
}