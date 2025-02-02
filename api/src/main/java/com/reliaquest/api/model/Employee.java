package com.reliaquest.api.model;

public class Employee {
    private String id;
    private String employee_name;
    private Integer employee_salary;
    private Integer employee_age;
    private String employee_title;
    private String employee_email;

    public String getId() {
        return id;
    }

    public Employee(String id, String employee_name, Integer employee_salary, Integer employee_age, String employee_title, String employee_email) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.employee_title = employee_title;
        this.employee_email = employee_email;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public Integer getEmployee_salary() {
        return employee_salary;
    }

    public Integer getEmployee_age() {
        return employee_age;
    }

    public String getEmployee_title() {
        return employee_title;
    }

    public String getEmployee_email() {
        return employee_email;
    }
}
