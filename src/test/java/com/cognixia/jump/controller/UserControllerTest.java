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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;

// @WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
// @WithMockUser(username = "user1", password = "pw123", roles = "USER")
// @RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    
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

        // mvc.perform(get("/api/users"))
        //    .andDo(print())
        //    .andExpect(status().isOk())
        //    .andExpect(jsonPath("$.length()").value(userList.size()));

        //    verify(userRepo, times(1)).findAll();
        //    verifyNoMoreInteractions(userRepo);

        List<User> users = userController.getAllUsers();
        assertEquals(userList.size(), users.size());

        int index = 0;
        for (User u : userList) {
            assertEquals(u, users.get(index));
            index++;
        }
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
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.getUserById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
    }
    
    @Test
    void testAddUser() throws Exception {
        User createdUser = new User(1, "user1", "pw123", Role.ROLE_USER, true);

        when(userRepo.findByUsername(createdUser.getUsername())).thenReturn(Optional.empty());
        when(userRepo.save(Mockito.any(User.class))).thenReturn(createdUser);

        ResponseEntity<String> response = userController.addUser(createdUser);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User(1, "user1", "pw123", Role.ROLE_USER, true);
        User updatedUser = new User(1, "user2", "pw1234", Role.ROLE_USER, true);

        when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        when(userRepo.save(Mockito.any(User.class))).thenReturn(updatedUser);

        ResponseEntity<String> response = userController.updateUser(user.getId(), updatedUser);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        int id = 1;
        User updatedUser = new User(1, "user2", "pw1234", Role.ROLE_USER, true);

        when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        ResponseEntity<String> response = userController.updateUser(id, updatedUser);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser() throws Exception {
        int id = 1;
        User user = new User(1, "user2", "pw1234", Role.ROLE_USER, true);
        
        when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.deleteUser(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        int id = 1;

        when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.deleteUser(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}