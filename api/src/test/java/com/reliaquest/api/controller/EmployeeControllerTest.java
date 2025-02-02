package com.reliaquest.api.controller;

import com.reliaquest.api.model.DeleteEmployeeResponse;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeData;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.ServerIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private ServerIntegrationService serverIntegrationService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee sampleEmployee;
    private EmployeeInput sampleEmployeeInput;

    @BeforeEach
    void setUp() {
        sampleEmployee = new Employee("1", "John Doe", 50000, 30, "Engineer", "abc@gmail.com");
        sampleEmployeeInput = new EmployeeInput("Jane Doe", 60000, 30, "Engineer");
    }

    @Test
    void testGetAllEmployees_Success() {
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData(List.of(sampleEmployee),"Successful"));
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllEmployees_EmptyList() {
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData((Collections.emptyList()),"Successful"));
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetEmployeesByNameSearch_Found() {
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData(List.of(sampleEmployee),"Successful"));
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("John");
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetEmployeesByNameSearch_NotFound() {
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData(List.of(sampleEmployee),"Successful"));
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("Jane");
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetEmployeeById_Success() {
        when(serverIntegrationService.getEmployeeById("1")).thenReturn(sampleEmployee);
        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");
        assertEquals("John Doe", response.getBody().getEmployee_name());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(serverIntegrationService.getEmployeeById("2")).thenReturn(null);
        ResponseEntity<Employee> response = employeeController.getEmployeeById("2");
        assertNull(response.getBody());
    }

    @Test
    void testGetHighestSalaryOfEmployees_Success() {
        List<Employee> employees = Arrays.asList(new Employee("1", "John Doe", 70000, 30, "Engineer", "abc@gmail.com"));
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData(employees,"Successful"));
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(70000, response.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames_Success() {
        List<Employee> employees = Arrays.asList(
                new Employee("1", "John Doe", 20000, 30, "Engineer", "abc@gmail.com"),
                new Employee("1", "John Doe", 30000, 30, "Engineer", "abc@gmail.com"),
                new Employee("1", "John Doe", 40000, 30, "Engineer", "abc@gmail.com")
        );
        when(serverIntegrationService.getAllEmployees()).thenReturn(new EmployeeData(employees,"Successful"));
        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testCreateEmployee_Success() {
        when(serverIntegrationService.createEmployee(sampleEmployeeInput)).thenReturn(sampleEmployee);
        ResponseEntity<Employee> response = employeeController.createEmployee(sampleEmployeeInput);
        assertEquals("John Doe", response.getBody().getEmployee_name());
    }

    @Test
    void testDeleteEmployeeById_Success() {
        when(serverIntegrationService.getEmployeeById("1")).thenReturn(sampleEmployee);
        when(serverIntegrationService.deleteEmployee("John Doe")).thenReturn(new DeleteEmployeeResponse(true, "Successful"));
        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals("John Doe", response.getBody());
    }

    @Test
    void testDeleteEmployeeById_EmployeeNotFound() {
        when(serverIntegrationService.getEmployeeById("2")).thenReturn(null);
        ResponseEntity<String> response = employeeController.deleteEmployeeById("2");
        assertNull(response.getBody());
    }
}
