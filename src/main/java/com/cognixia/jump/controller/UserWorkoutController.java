package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.UserWorkout;
import com.cognixia.jump.repository.UserWorkoutRepository;

@RestController
@RequestMapping("/api")
public class UserWorkoutController {

	@Autowired
	private UserWorkoutRepository repository;
	
	@GetMapping("/userWorkouts")
	public List<UserWorkout> getAllUserWorkouts() {
		return repository.findAll();
	}
	
	@GetMapping("/userWorkouts/{id}")
	public ResponseEntity<UserWorkout> getUserWorkoutById(@PathVariable Integer id) {
		Optional<UserWorkout> userWorkout = repository.findById(id);
		if (userWorkout.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(200).body(userWorkout.get());
	}
	
	@PostMapping("/userWorkouts")
	public ResponseEntity<UserWorkout> createUserWorkout(@RequestBody UserWorkout userWorkout) {
		UserWorkout created = repository.save(userWorkout);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@PutMapping("/userWorkouts/{id}")
	public ResponseEntity<UserWorkout> updateUserWorkout(@PathVariable Integer id, @RequestBody UserWorkout userWorkout) {
		Optional<UserWorkout> existingUserWorkout = repository.findById(id);
		if (existingUserWorkout.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		UserWorkout updated = repository.save(userWorkout);
		return new ResponseEntity<UserWorkout>(updated, HttpStatus.OK);
	}
	
	@DeleteMapping("/userWorkouts/{id}")
	public ResponseEntity<Void> deleteUserWorkout(@PathVariable Integer id) {
		UserWorkout existingUserWorkout = repository.findById(id).orElse(null);
		if (existingUserWorkout == null) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}