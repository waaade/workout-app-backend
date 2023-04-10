package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
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
	public ResponseEntity<User> getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
		
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(200).body(user.get());
	}

	@GetMapping("/users/name/{name}")
	public Optional<User> getUserByName(@PathVariable String name) throws ResourceNotFoundException {
		return userRepo.findByUsername(name);
	}

	@PostMapping("/users")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
		Optional<User> foundUser = userRepo.findByUsername(user.getUsername());
		if (foundUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		}
		// user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setId(null);
		user.setRole(Role.ROLE_USER);
		user.setEnabled(true);
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("Account successfully created");
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
		// existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
