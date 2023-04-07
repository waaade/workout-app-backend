package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.model.UserWorkout;
import com.cognixia.jump.repository.UserWorkoutRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserWorkoutControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private UserWorkoutRepository userWorkoutRepo;

    @InjectMocks
    private UserWorkoutController userWorkoutController;

    @Test
    void testGetAllUserWorkouts() throws Exception {
        List<UserWorkout> userWorkoutList = new ArrayList<UserWorkout>();
        User user1 = new User(1, "user1", "pw123", Role.ROLE_USER, true);
        User user2 = new User(2, "user2", "pw1234", Role.ROLE_ADMIN, true);
        userWorkoutList.add(new UserWorkout(1, user1, LocalDate.now()));
        userWorkoutList.add(new UserWorkout(2, user2, LocalDate.now()));

        when(userWorkoutRepo.findAll()).thenReturn(userWorkoutList);

        List<UserWorkout> userWorkouts = userWorkoutController.getAllUserWorkouts();

        assertEquals(userWorkoutList.size(), userWorkouts.size());

        int index = 0;
        for (UserWorkout uw : userWorkoutList) {
            assertEquals(uw, userWorkouts.get(index));
            index++;
        }
    }

    @Test
    void testGetUserWorkoutById() throws Exception {
        int id = 1;
        UserWorkout foundUserWorkout = new UserWorkout(1, new User(), LocalDate.now());

        when(userWorkoutRepo.findById(1)).thenReturn(Optional.of(foundUserWorkout));

        ResponseEntity<UserWorkout> result = userWorkoutController.getUserWorkoutById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(foundUserWorkout, result.getBody());
    }

    @Test
    void testGetUserWorkoutByIdNotFound() throws Exception {
        Integer id = 1;
        
        when(userWorkoutRepo.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<UserWorkout> result = userWorkoutController.getUserWorkoutById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
    }

    @Test
    void testCreateUserWorkout() throws Exception {
        UserWorkout createdUserWorkout = new UserWorkout(1, new User(), LocalDate.now());

        when(userWorkoutRepo.save(createdUserWorkout)).thenReturn(createdUserWorkout);

        ResponseEntity<UserWorkout> response = userWorkoutController.createUserWorkout(createdUserWorkout);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateUserWorkout() throws Exception {
        int id = 1;
        UserWorkout userWorkout = new UserWorkout(1, new User(), LocalDate.now());
        UserWorkout updatedUserWorkout = new UserWorkout(2, new User(), LocalDate.now());

        when(userWorkoutRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(userWorkout));
        when(userWorkoutRepo.save(Mockito.any(UserWorkout.class))).thenReturn(updatedUserWorkout);

        ResponseEntity<UserWorkout> response = userWorkoutController.updateUserWorkout(id, updatedUserWorkout);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateUserWorkoutNotFound() {
        int id = 1;
        UserWorkout updatedUserWorkout = new UserWorkout(1, new User(), LocalDate.now());

        when(userWorkoutRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseEntity<UserWorkout> response = userWorkoutController.updateUserWorkout(id, updatedUserWorkout);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUserWorkout() throws Exception {
        int id = 1;
        UserWorkout existingUserWorkout = new UserWorkout(1, new User(), LocalDate.now());

        when(userWorkoutRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(existingUserWorkout));

        ResponseEntity<Void> response = userWorkoutController.deleteUserWorkout(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUserWorkoutNotFound() throws Exception {
        int id = 1;

        when(userWorkoutRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userWorkoutController.deleteUserWorkout(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());        
    }
    
}
