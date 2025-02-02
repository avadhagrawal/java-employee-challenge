package com.reliaquest.api.service;

import com.reliaquest.api.config.ServerConfig;
import com.reliaquest.api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Service class responsible for integrating with the external employee server.
 * It handles CRUD operations using RestTemplate.
 */
@Slf4j
@Service
public class ServerIntegrationService {

    private final RestTemplate restTemplate;

    private final ServerConfig serverConfig;

    public ServerIntegrationService(RestTemplate restTemplate, ServerConfig serverConfig) {
        this.restTemplate = restTemplate;
        this.serverConfig = serverConfig;
    }

    /**
     * Retrieves all employees from the external server.
     *
     * @return EmployeeData containing the list of employees.
     */
    public EmployeeData getAllEmployees() throws HttpClientErrorException.TooManyRequests {
            log.info("Fetching all employees from server");
            ResponseEntity<EmployeeData> responseEntity = restTemplate.exchange(
                    serverConfig.getServerUrl(),
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    EmployeeData.class);
            return responseEntity.getBody();
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId The ID of the employee.
     * @return Employee object if found, otherwise throws an exception.
     */
    public Employee getEmployeeById(String employeeId) {
            log.info("Fetching employee with ID: {}", employeeId);
            ResponseEntity<EmployeeById> responseEntity = restTemplate.exchange(
                    serverConfig.getServerUrl() + "/" + employeeId,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    EmployeeById.class);
            return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * Creates a new employee on the external server.
     *
     * @param employeeInput The input object containing employee details.
     * @return The created Employee object.
     */
    public Employee createEmployee(EmployeeInput employeeInput) {
        HttpEntity<EmployeeInput> requestEntity = new HttpEntity<>(employeeInput, new HttpHeaders());
            log.info("Creating new employee: {}", employeeInput.getName());
            ResponseEntity<EmployeeById> responseEntity = restTemplate.exchange(
                    serverConfig.getServerUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    EmployeeById.class);
            return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * Deletes an employee by name.
     *
     * @param employeeName The name of the employee to be deleted.
     * @return DeleteEmployeeResponse indicating success or failure.
     */
    public DeleteEmployeeResponse deleteEmployee(String employeeName) {
        DeleteEmployeeRequest deleteEmployeeRequest = new DeleteEmployeeRequest();
        deleteEmployeeRequest.setName(employeeName);
        HttpEntity<DeleteEmployeeRequest> requestEntity = new HttpEntity<>(deleteEmployeeRequest, new HttpHeaders());
            log.info("Deleting employee: {}", employeeName);
            ResponseEntity<DeleteEmployeeResponse> responseEntity = restTemplate.exchange(
                    serverConfig.getServerUrl(),
                    HttpMethod.DELETE,
                    requestEntity,
                    DeleteEmployeeResponse.class);
            return responseEntity.getBody();
    }
}
