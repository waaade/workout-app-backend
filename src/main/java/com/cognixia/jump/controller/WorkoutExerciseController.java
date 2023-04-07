package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognixia.jump.model.WorkoutExercise;
import com.cognixia.jump.repository.WorkoutExerciseRepository;

@RestController
@RequestMapping("/api")
public class WorkoutExerciseController {

@Autowired
WorkoutExerciseRepository workoutExerciseRepo;

@GetMapping("/workoutExercises")
public List<WorkoutExercise> getAllWorkoutExercises() {
	return workoutExerciseRepo.findAll();
}

@GetMapping("/workoutExercises/{id}")
public ResponseEntity<WorkoutExercise> getWorkoutExerciseById(@PathVariable Integer id) {
    WorkoutExercise workoutExercise = workoutExerciseRepo.findById(id).orElse(null);
    if (workoutExercise == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(workoutExercise);
}


@PostMapping("/workoutExercises")
public ResponseEntity<String> addWorkoutExercise(@RequestBody WorkoutExercise workoutExercise) {
	workoutExerciseRepo.save(workoutExercise);
	return ResponseEntity.status(HttpStatus.CREATED).build();
}

@PutMapping("/workoutExercises/{id}")
public ResponseEntity<String> updateWorkoutExercise(@PathVariable Integer id,
		@RequestBody WorkoutExercise workoutExercise) {
	WorkoutExercise existingWorkoutExercise = workoutExerciseRepo.findById(id).orElse(null);
	if (existingWorkoutExercise == null) {
		return ResponseEntity.notFound().build();
	}
	workoutExercise.setId(id);
	workoutExerciseRepo.save(workoutExercise);
	return ResponseEntity.status(HttpStatus.OK).build();
}

@DeleteMapping("/workoutExercises/{id}")
public ResponseEntity<String> deleteWorkoutExercise(@PathVariable Integer id) {
	WorkoutExercise existingWorkoutExercise = workoutExerciseRepo.findById(id).orElse(null);
	if (existingWorkoutExercise == null) {
		return ResponseEntity.notFound().build();
	}
	workoutExerciseRepo.delete(existingWorkoutExercise);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}