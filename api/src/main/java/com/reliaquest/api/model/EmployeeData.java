package com.reliaquest.api.model;

import java.util.List;

public class EmployeeData {

    private List<Employee> data;
    private String status;

    public EmployeeData(List<Employee> data, String status) {
        this.data = data;
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
