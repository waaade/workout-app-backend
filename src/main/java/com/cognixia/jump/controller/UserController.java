package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userRepo.findById(id).orElse(null);
	}

	@PostMapping("/users")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		// if (userRepo.existsByUsername(user.getUsername())) {
		// 	return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		// }
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_USER);
		user.setEnabled(true);
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody User user) {
		User existingUser = userRepo.findById(id).orElse(null);
		if (existingUser == null) {
			return ResponseEntity.notFound().build();
		}
		// if (!existingUser.getUsername().equals(user.getUsername())
		// 		&& userRepo.existsByUsername(user.getUsername())) {
		// 	return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		// }
		existingUser.setUsername(user.getUsername());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(existingUser);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
		User existingUser = userRepo.findById(id).orElse(null);
		if (existingUser == null) {
			return ResponseEntity.notFound().build();
		}
		userRepo.delete(existingUser);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
