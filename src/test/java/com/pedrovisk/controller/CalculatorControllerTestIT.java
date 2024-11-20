package com.pedrovisk.controller;

import com.pedrovisk.infra.security.TokenService;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.repository.UserRepository;
import com.pedrovisk.service.OperationService;
import com.pedrovisk.service.RandomGeneratorService;
import com.pedrovisk.service.RecordService;
import com.pedrovisk.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
@AutoConfigureMockMvc
class CalculatorControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    private RecordService recordService;
    @MockBean
    private RandomGeneratorService randomGeneratorService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private OperationService operationService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserService userService;

    @InjectMocks
    private CalculatorController calculatorController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(value = "test")
    void testExecuteOperation_Success() throws Exception {

        Long userId = 1L;
        var user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        var operation = new OperationEntity();
        operation.setId(10L);
        operation.setCost(20.0f);
        operation.setType("addition");

        RecordResponse savedRecord = new RecordResponse(1L, operation.getId(), user.getId(), 80f,
                "10.0", LocalDateTime.now(), Status.ACTIVE.getValue());

        CalculatorRequest request = new CalculatorRequest(1L, 1, 10.0f, 2f);
        when(recordService.saveOperation("test", request)).thenReturn(savedRecord);


        mockMvc.perform(post("/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader())
                        .header("Authorization", "Bearer token")
                        .content(fromFile("execute_operation_simple_request.json")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testExecuteOperation_Forbidden() throws Exception {

        // Act & Assert
        mockMvc.perform(post("/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fromFile("execute_operation_simple_request.json")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testExecuteOperation_Unauthorized() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader())
                        .header("Authorization", "Bearer token")
                        .content(fromFile("execute_operation_simple_request.json")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "test")
    void testExecuteOperation_Failure() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(1L, 1, 10.0f, 2f);
        when(recordService.saveOperation("test", request)).thenThrow(new RuntimeException("Operation failed"));

        // Act & Assert
        mockMvc.perform(post("/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader())
                        .header("Authorization", "Bearer token")
                        .content(fromFile("execute_operation_simple_request.json")))
                .andExpect(status().isInternalServerError());
    }


    @SneakyThrows
    private byte[] fromFile(String path) {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
