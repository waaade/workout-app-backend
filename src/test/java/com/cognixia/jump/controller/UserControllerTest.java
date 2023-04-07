package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;

// @WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser(username = "user1", password = "pw123", roles = "USER")
// @RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	private static final String STARTING_URI = "http://localhost:8080/api";
    
    @Autowired
    private MockMvc mvc;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "user1", "pw123", Role.ROLE_USER, true));
        userList.add(new User(2, "user2", "pw1234", Role.ROLE_ADMIN, true));

        when(userRepo.findAll()).thenReturn(userList);

        String uri = STARTING_URI + "/users";
        mvc.perform(get("/api/users"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(userList.size()));

        //    verify(userRepo, times(1)).findAll();
        //    verifyNoMoreInteractions(userRepo);

        // List<User> users = userController.getAllUsers();
        // assertEquals(userList.size(), users.size());

        // int index = 0;
        // for (User u : userList) {
        //     assertEquals(u, users.get(index));
        //     index++;
        // }
    }

    @Test
    void testGetUserById() throws Exception {
        int id = 1;
        User foundUser = new User(1, "user1", "pw123", Role.ROLE_USER, true);
        when(userRepo.findById(1)).thenReturn(Optional.of(foundUser));

        ResponseEntity<User> result = userController.getUserById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(foundUser, result.getBody());
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        Integer id = 1;
        when(userRepo.findById(1)).thenThrow(new ResourceNotFoundException("User", id));

        ResponseEntity<User> result = userController.getUserById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
    }
    
    @Test
    void testAddUser() throws Exception {

    }
}
