package com.cognixia.jump.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cognixia.jump.model.WorkoutExercise;
import com.cognixia.jump.repository.WorkoutExerciseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user1", password = "pw123", roles = "USER")
public class WorkoutExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Test
    public void testGetAllWorkoutExercises() throws Exception {
        WorkoutExercise exercise1 = new WorkoutExercise();
        exercise1.setId(1);
        exercise1.setReps(10);
        exercise1.setWeight(100);

        WorkoutExercise exercise2 = new WorkoutExercise();
        exercise2.setId(2);
        exercise2.setReps(12);
        exercise2.setWeight(120);

        List<WorkoutExercise> exercises = new ArrayList<>();
        exercises.add(exercise1);
        exercises.add(exercise2);

        when(workoutExerciseRepository.findAll()).thenReturn(exercises);

        mockMvc.perform(get("/api/workoutExercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].reps", is(10)))
                .andExpect(jsonPath("$[0].weight", is(100)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].reps", is(12)))
                .andExpect(jsonPath("$[1].weight", is(120)));
    }

    @Test
    public void testGetWorkoutExerciseById() throws Exception {
        WorkoutExercise exercise = new WorkoutExercise();
        exercise.setId(1);
        exercise.setReps(10);
        exercise.setWeight(100);

        when(workoutExerciseRepository.findById(1)).thenReturn(Optional.of(exercise));

        mockMvc.perform(get("/api/workoutExercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.reps", is(10)))
                .andExpect(jsonPath("$.weight", is(100)));
    }

    @Test
    public void testGetWorkoutExerciseByIdNotFound() throws Exception {
        int id = 1;
    
        // Set up the mock repository to return an empty optional when findById is called with the given id
        when(workoutExerciseRepository.findById(id)).thenReturn(Optional.empty());
    
        // Perform the GET request to the controller endpoint with the given id
        mockMvc.perform(get("/api/workoutExercises/{id}", id))
        
                // Assert that the response status code is 404
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddWorkoutExercise() throws Exception {
        WorkoutExercise exercise = new WorkoutExercise();
        exercise.setId(1);
        exercise.setReps(10);
        exercise.setWeight(100);

        mockMvc.perform(post("/api/workoutExercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exercise)))
                .andExpect(status().isCreated());

        verify(workoutExerciseRepository, times(1)).save(any(WorkoutExercise.class));
    }

    @Test
    public void testUpdateWorkoutExercise() throws Exception {
        WorkoutExercise exercise = new WorkoutExercise();
        exercise.setId(1);
        exercise.setReps(10);
        exercise.setWeight(100);
    
        when(workoutExerciseRepository.findById(1)).thenReturn(Optional.of(exercise));
    
        WorkoutExercise updatedExercise = new WorkoutExercise();
        updatedExercise.setReps(12);
        updatedExercise.setWeight(120);
    
        mockMvc.perform(put("/api/workoutExercises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedExercise)))
                .andExpect(status().isOk());
    
        ArgumentCaptor<WorkoutExercise> captor = ArgumentCaptor.forClass(WorkoutExercise.class);
        verify(workoutExerciseRepository, times(1)).save(captor.capture());
    
        WorkoutExercise savedExercise = captor.getValue();
        assertEquals(savedExercise.getId(), Integer.valueOf(1));
        assertEquals(savedExercise.getReps(), Integer.valueOf(12));
        assertEquals(savedExercise.getWeight(), Integer.valueOf(120));
    }
    
    @Test
    public void testUpdateWorkoutExerciseNotFound() throws Exception {
        when(workoutExerciseRepository.findById(1)).thenReturn(Optional.empty());

        WorkoutExercise updatedExercise = new WorkoutExercise();
        updatedExercise.setReps(12);
        updatedExercise.setWeight(120);

        mockMvc.perform(put("/api/workoutExercises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedExercise)))
                .andExpect(status().isNotFound());

        verify(workoutExerciseRepository, never()).save(any(WorkoutExercise.class));
    }


}
