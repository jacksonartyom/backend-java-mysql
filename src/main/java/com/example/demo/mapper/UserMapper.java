package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;

@Component
public class UserMapper {
	
	private final PasswordEncoder passwordEncoder;
	
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	public User toEntity(UserRequest req) {
		String uuid = UUID.randomUUID().toString().replace("-", "");

		User user = new User();
		user.setUserId(uuid);
		user.setFirstName(req.getFirstName());
		user.setMidName(req.getMidName());
		user.setLastName(req.getLastName());
		user.setEmail(req.getEmail());
		user.setPhoneNo(req.getPhoneNo());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return user;
	}

	public UserResponse toResponse(User user) {
		return UserResponse.builder().userId(user.getUserId()).email(user.getEmail()).firstName(user.getFirstName())
				.midName(user.getMidName()).lastName(user.getLastName()).phoneNo(user.getPhoneNo()).build();
	}
}