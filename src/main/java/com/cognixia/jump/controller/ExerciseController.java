package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognixia.jump.model.Exercise;
import com.cognixia.jump.repository.ExerciseRepository;

@RestController
@RequestMapping("/api")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepo;

    @GetMapping("/exercises")
    public List<Exercise> getAllExercises() {
        return exerciseRepo.findAll();
    }

    @GetMapping("/exercises/{id}")
    public Exercise getExerciseById(@PathVariable Integer id) {
        return exerciseRepo.findById(id).orElse(null);
    }

    @PostMapping("/exercises")
    public ResponseEntity<String> addExercise(@RequestBody Exercise exercise) {
        exerciseRepo.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/exercises/{id}")
    public ResponseEntity<String> updateExercise(@PathVariable Integer id, @RequestBody Exercise exercise) {
        Exercise existingExercise = exerciseRepo.findById(id).orElse(null);
        if (existingExercise == null) {
            return ResponseEntity.notFound().build();
        }
        existingExercise.setExerciseType(exercise.getExerciseType());
        exerciseRepo.save(existingExercise);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable Integer id) {
        Exercise existingExercise = exerciseRepo.findById(id).orElse(null);
        if (existingExercise == null) {
            return ResponseEntity.notFound().build();
        }
        exerciseRepo.delete(existingExercise);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
