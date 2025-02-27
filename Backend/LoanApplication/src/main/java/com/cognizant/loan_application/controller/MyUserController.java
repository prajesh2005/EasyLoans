package com.cognizant.loan_application.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.loan_application.entities.MyUser;
import com.cognizant.loan_application.exception.UnauthorizedException;
import com.cognizant.loan_application.exception.UserAlreadyExistsException;
import com.cognizant.loan_application.helper.AuthRequest;
import com.cognizant.loan_application.helper.MyToken;
import com.cognizant.loan_application.service.JwtService;
import com.cognizant.loan_application.service.MyUserDetailsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
public class MyUserController {
	private static final Logger logger = LoggerFactory.getLogger(MyUserController.class);

	@Autowired
	private MyUserDetailsService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/signup")
	public MyUser signup(Authentication authentication,@Valid @RequestBody MyUser myUser) {
		if (userService.userExists(myUser.getUsername())) {
			logger.warn("User already exists with username: {}", myUser.getUsername());
			throw new UserAlreadyExistsException("User already exists with username: " + myUser.getUsername());
		}
		return userService.addNewUser(myUser);
	}

//	@PostMapping("/login")
//	public MyToken login(@RequestBody AuthRequest authRequest) {
//		MyToken token = new MyToken();
//		try {
//			Authentication auth = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//			if (auth.isAuthenticated()) {
//				String jwt = jwtService.generateToken(authRequest.getUsername());
//				token.setUsername(authRequest.getUsername());
//				token.setToken(jwt);
//				token.setAuthorities(auth.getAuthorities());
//			} else {
//				throw new UnauthorizedException("Login failed: Unauthorized access");
//			}
//		} catch (Exception e) {
//			throw new UnauthorizedException("Login failed: " + e.getMessage());
//		}
//		return token;
//	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody AuthRequest authRequest) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Authentication auth = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
	        if (auth.isAuthenticated()) {
	            String jwt = jwtService.generateToken(authRequest.getUsername());
	            String role = userService.getUserRole(authRequest.getUsername()); // Fetching user role

	            response.put("username", authRequest.getUsername());
	            response.put("token", jwt);
	            response.put("role", role);
	        } else {
	            throw new UnauthorizedException("Login failed: Unauthorized access");
	        }
	    } catch (Exception e) {
	        throw new UnauthorizedException("Login failed: " + e.getMessage());
	    }
	    return response;
	}
}
