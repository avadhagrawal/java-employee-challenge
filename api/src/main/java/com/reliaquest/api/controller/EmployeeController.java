package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.ServerIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@Retryable(
        retryFor = { HttpClientErrorException.TooManyRequests.class },
        backoff = @Backoff(delay = 45000, multiplier = 2)
)
public class EmployeeController implements IEmployeeController <Employee, EmployeeInput> {

    @Autowired
    ServerIntegrationService serverIntegrationService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.of(Optional.ofNullable(serverIntegrationService.getAllEmployees().getData()));
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = serverIntegrationService.getAllEmployees().getData();
        List<Employee> filteredList = employeeList.stream().filter(employee -> employee.getEmployee_name().contains(searchString)).toList();
        return ResponseEntity.of(Optional.of(filteredList));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return ResponseEntity.of(Optional.ofNullable(serverIntegrationService.getEmployeeById(id)));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        List<Employee> employeeList = serverIntegrationService.getAllEmployees().getData();
        Employee highestSalaryEmployee = employeeList.stream().max(Comparator.comparingInt(Employee::getEmployee_salary))
                .get();
        return ResponseEntity.of(Optional.of(highestSalaryEmployee.getEmployee_salary()));
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employeeList = serverIntegrationService.getAllEmployees().getData();
        List<String> filteredList = employeeList.stream().sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).map(Employee::getEmployee_name).limit(10).toList();
        return ResponseEntity.of((Optional.of(filteredList)));
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeInput employeeInput) {
        return ResponseEntity.of(Optional.ofNullable(serverIntegrationService.createEmployee(employeeInput)));
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        Employee employee = serverIntegrationService.getEmployeeById(id);
        if (employee != null) {
            serverIntegrationService.deleteEmployee(employee.getEmployee_name());
            return ResponseEntity.of(Optional.ofNullable(employee.getEmployee_name()));
        }
        return ResponseEntity.notFound().build();
    }
}
