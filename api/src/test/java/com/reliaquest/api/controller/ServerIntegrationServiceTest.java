package com.reliaquest.api.controller;

import com.reliaquest.api.config.ServerConfig;
import com.reliaquest.api.model.*;
import com.reliaquest.api.service.ServerIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServerIntegrationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ServerConfig serverConfig;

    @InjectMocks
    private ServerIntegrationService serverIntegrationService;

    private Employee sampleEmployee;
    private EmployeeInput sampleEmployeeInput;
    private EmployeeData employeeData;

    @BeforeEach
    void setUp() {
        sampleEmployee = new Employee("1", "John Doe", 50000, 30, "Engineer", "abc@gmail.com");
        employeeData = new EmployeeData(List.of(sampleEmployee),"Successful");
        sampleEmployeeInput = new EmployeeInput("Jane Doe", 60000, 30, "Engineer");
        when(serverConfig.getServerUrl()).thenReturn("https://mockserver.com/employees");
    }

    @Test
    void testGetAllEmployees_Success() {
        ResponseEntity<EmployeeData> responseEntity = new ResponseEntity<>(employeeData, HttpStatus.OK);
        when(restTemplate.exchange(eq("https://mockserver.com/employees"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(EmployeeData.class)))
                .thenReturn(responseEntity);

        EmployeeData result = serverIntegrationService.getAllEmployees();
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("John Doe", result.getData().get(0).getEmployee_name());
        assertEquals(30, result.getData().get(0).getEmployee_age());
        assertEquals("Engineer", result.getData().get(0).getEmployee_title());
        assertEquals("abc@gmail.com", result.getData().get(0).getEmployee_email());
    }

    @Test
    void testGetEmployeeById_Success() {
        EmployeeById employeeById = new EmployeeById();
        employeeById.setData(new Employee("1", "John Doe", 50000, 30, "Engineer", "abc@gmail.com"));
        employeeById.setStatus("Successful");
        ResponseEntity<EmployeeById> responseEntity = new ResponseEntity<>(employeeById, HttpStatus.OK);

        when(restTemplate.exchange(eq("https://mockserver.com/employees/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(EmployeeById.class)))
                .thenReturn(responseEntity);

        Employee result = serverIntegrationService.getEmployeeById("1");
        assertNotNull(result);
        assertEquals("John Doe", result.getEmployee_name());
        assertEquals(30, result.getEmployee_age());
        assertEquals("Engineer", result.getEmployee_title());
        assertEquals("abc@gmail.com", result.getEmployee_email());
    }

    @Test
    void testCreateEmployee_Success() {
        EmployeeById employeeById = new EmployeeById();
        ResponseEntity<EmployeeById> responseEntity = new ResponseEntity<>(employeeById, HttpStatus.CREATED);
        employeeById.setData(new Employee("1", "John Doe", 50000, 30, "Engineer", "abc@gmail.com"));
        employeeById.setStatus("Successful");

        when(restTemplate.exchange(eq("https://mockserver.com/employees"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(EmployeeById.class)))
                .thenReturn(responseEntity);

        Employee result = serverIntegrationService.createEmployee(sampleEmployeeInput);
        assertNotNull(result);
        assertEquals("John Doe", result.getEmployee_name());
        assertEquals(30, result.getEmployee_age());
        assertEquals("Engineer", result.getEmployee_title());
        assertEquals("abc@gmail.com", result.getEmployee_email());
    }

    @Test
    void testDeleteEmployee_Success() {
        DeleteEmployeeResponse deleteResponse = new DeleteEmployeeResponse(true, "Successful");
        ResponseEntity<DeleteEmployeeResponse> responseEntity = new ResponseEntity<>(deleteResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq("https://mockserver.com/employees"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(DeleteEmployeeResponse.class)))
                .thenReturn(responseEntity);

        DeleteEmployeeResponse result = serverIntegrationService.deleteEmployee("John Doe");
        assertNotNull(result);
        assertTrue(result.getData());
    }
}
