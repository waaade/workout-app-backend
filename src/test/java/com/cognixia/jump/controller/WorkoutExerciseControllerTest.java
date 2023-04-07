package com.cognixia.jump.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


import com.cognixia.jump.model.WorkoutExercise;
import com.cognixia.jump.repository.WorkoutExerciseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
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


}
