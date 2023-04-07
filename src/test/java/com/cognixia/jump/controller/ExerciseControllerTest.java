package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Exercise;
import com.cognixia.jump.repository.ExerciseRepository;

@ExtendWith(MockitoExtension.class)
public class ExerciseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExerciseRepository exerciseRepo;

    @InjectMocks
    private ExerciseController exerciseController;

    @Test
    void testGetAllExercises() throws Exception {
        // Arrange
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(1, "Squats", "Legs", 3, 8, 80));
        exercises.add(new Exercise(2, "Bench Press", "Chest", 3, 10, 100));

        when(exerciseRepo.findAll()).thenReturn(exercises);

        // Act
        List<Exercise> result = exerciseController.getAllExercises();

        // Assert
        assertEquals(exercises.size(), result.size());
        for (int i = 0; i < exercises.size(); i++) {
            assertEquals(exercises.get(i), result.get(i));
        }
    }

    @Test
    void testGetExerciseById() throws Exception {
        // Arrange
        Integer exerciseId = 1;
        Exercise exercise = new Exercise(1, "Squats", "Legs", 3, 8, 80);
        when(exerciseRepo.findById(exerciseId)).thenReturn(Optional.of(exercise));

        // Act
        ResponseEntity<Exercise> result = exerciseController.getExerciseById(exerciseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(exercise, result.getBody());
    }

    @Test
    void testGetExerciseByIdNotFound() throws Exception {
        // Arrange
        Integer exerciseId = 1;
        when(exerciseRepo.findById(exerciseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Exercise> result = exerciseController.getExerciseById(exerciseId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
    }

    @Test
	void testAddExercise() {
		Exercise newExercise = new Exercise(1, "Running", null, 0, 0, 0);
		
		when(exerciseRepo.save(Mockito.any(Exercise.class))).thenReturn(newExercise);
		
		ResponseEntity<String> response = exerciseController.addExercise(newExercise);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void testUpdateExercise() {
		int exerciseId = 1;
		Exercise existingExercise = new Exercise(exerciseId, "Running", null, exerciseId, exerciseId, exerciseId);
		Exercise updatedExercise = new Exercise(exerciseId, "Cycling", null, exerciseId, exerciseId, exerciseId);
		
		when(exerciseRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(existingExercise));
		when(exerciseRepo.save(Mockito.any(Exercise.class))).thenReturn(updatedExercise);
		
		ResponseEntity<String> response = exerciseController.updateExercise(exerciseId, updatedExercise);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void testUpdateExerciseNotFound() {
		int exerciseId = 1;
		Exercise updatedExercise = new Exercise(exerciseId, "Cycling", null, exerciseId, exerciseId, exerciseId);
		
		when(exerciseRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		ResponseEntity<String> response = exerciseController.updateExercise(exerciseId, updatedExercise);
		
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void testDeleteExercise() {
		int exerciseId = 1;
		Exercise existingExercise = new Exercise(exerciseId, "Running", null, exerciseId, exerciseId, exerciseId);
		
		when(exerciseRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(existingExercise));
		
		ResponseEntity<String> response = exerciseController.deleteExercise(exerciseId);
		
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	void testDeleteExerciseNotFound() {
		int exerciseId = 1;
		
		when(exerciseRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		ResponseEntity<String> response = exerciseController.deleteExercise(exerciseId);
		
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}





