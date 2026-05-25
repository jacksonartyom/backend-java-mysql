package com.example.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	private final PasswordEncoder passwordEncoder;


	private final UserRepository repo;
	private final UserMapper mapper;
	
	private Key getSigningKey() {
	    return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public List<UserResponse> getAll() {
		return repo.findAll().stream().map(mapper::toResponse).toList();
	}

	public String createUser(UserRequest req) {
		User user = mapper.toEntity(req);
		mapper.toResponse(repo.save(user));
		return "create success";
	}

	public UserResponse login(UserRequest req) {
	    // 1. หา user
		User user = repo.findByEmail(req.getEmail())
		        .orElseThrow(() -> new RuntimeException("Invalid email or password"));

	    // 2. เช็ค password
	    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
	        throw new RuntimeException("Invalid password");
	    }

	    // 3. map response
	    UserResponse res = mapper.toResponse(user);

	    // 4. generate token
	    res.setToken(generateToken(user.getUserId()));

	    return res;
	}

	public String generateToken(String userId) {
		long EXPIRATION_TIME = 1000 * 60 * 60; // 60 นาที

		return Jwts.builder().setSubject(userId).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey()).compact();
	}

}