package com.reliaquest.api.model;

public class EmployeeInput {
    private String name;
    private Integer salary;
    private Integer age;
    private String title;

    public EmployeeInput(String name, Integer salary, Integer age, String title) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.title = title;
    }

    public EmployeeInput() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getAge() {
        return age;
    }

    public String getTitle() {
        return title;
    }
}
