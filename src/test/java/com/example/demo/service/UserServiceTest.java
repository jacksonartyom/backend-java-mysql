package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setup() {
        // inject jwt secret
        ReflectionTestUtils.setField(service, "secret", "mysecretkeymysecretkeymysecretkey");
    }
    
    @Test
    void shouldCreateUserSuccessfully() {
        UserRequest req = new UserRequest();
        User user = new User();

        when(mapper.toEntity(req)).thenReturn(user);
        when(repo.save(user)).thenReturn(user);

        String result = service.createUser(req);

        assertEquals("create success", result);

        verify(repo).save(user);
    }
    
    @Test
    void shouldReturnAllUsers() {
        User u1 = new User();
        User u2 = new User();

        UserResponse r1 = new UserResponse();
        UserResponse r2 = new UserResponse();

        when(repo.findAll()).thenReturn(List.of(u1, u2));
        when(mapper.toResponse(u1)).thenReturn(r1);
        when(mapper.toResponse(u2)).thenReturn(r2);

        List<UserResponse> result = service.getAll();

        assertEquals(2, result.size());
    }
    
    @Test
    void shouldLoginSuccessfully() {
        UserRequest req = new UserRequest();
        req.setEmail("test@mail.com");
        req.setPassword("1234");

        User user = new User();
        user.setUserId("user-1");
        user.setPassword("encoded");

        UserResponse response = new UserResponse();

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded")).thenReturn(true);
        when(mapper.toResponse(user)).thenReturn(response);

        UserResponse result = service.login(req);

        assertNotNull(result.getToken());
    }
    
    @Test
    void shouldThrowWhenEmailNotFound() {
        UserRequest req = new UserRequest();
        req.setEmail("notfound@mail.com");

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.login(req);
        });

        assertEquals("Invalid email or password", ex.getMessage());
    }
    
    @Test
    void shouldThrowWhenPasswordInvalid() {
        UserRequest req = new UserRequest();
        req.setEmail("test@mail.com");
        req.setPassword("wrong");

        User user = new User();
        user.setPassword("encoded");

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.login(req);
        });

        assertEquals("Invalid password", ex.getMessage());
    }
}