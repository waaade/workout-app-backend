package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public UserWorkout getUserWorkoutById(@PathVariable Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	@PostMapping("/userWorkouts")
	public ResponseEntity<UserWorkout> createUserWorkout(@RequestBody UserWorkout userWorkout) {
		UserWorkout created = repository.save(userWorkout);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@PutMapping("/userWorkouts/{id}")
	public ResponseEntity<UserWorkout> updateUserWorkout(@PathVariable Integer id, @RequestBody UserWorkout userWorkout) {
		UserWorkout updated = repository.save(userWorkout);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@DeleteMapping("/userWorkouts/{id}")
	public ResponseEntity<Void> deleteUserWorkout(@PathVariable Integer id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}