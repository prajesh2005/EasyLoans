package com.cognizant.loan_application.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.loan_application.entities.MyUser;
import com.cognizant.loan_application.repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MyUserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) {
		logger.info("Loading user by username: {}", username);
		Optional<MyUser> temp = userRepository.findById(username);
		if (temp.isEmpty()) {
			logger.warn("User not found: {}", username);
			throw new UsernameNotFoundException(username);
		}
		MyUser myUser = temp.get();

		String str = myUser.getRoles();
		String[] roles = str.split(",");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		logger.info("Loaded user: {}", username);
		return new User(myUser.getUsername(), myUser.getPassword(), authorities);
	}

	public MyUser addNewUser(MyUser myUser) {
		logger.info("Adding new user: {}", myUser.getUsername());
		String plainPassword = myUser.getPassword();
		String encPassword = passwordEncoder.encode(plainPassword);
		myUser.setPassword(encPassword);
		MyUser savedUser = userRepository.save(myUser);
		logger.info("Added new user: {}", savedUser.getUsername());
		return savedUser;
	}

	public boolean userExists(String username) {
		logger.info("Checking if user exists: {}", username);
		boolean exists = userRepository.findByUsername(username).isPresent();
		logger.info("User exists: {}", exists);
		return exists;
	}
    public String getUserRole(String username) {
        MyUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getRoles();
    }

}
