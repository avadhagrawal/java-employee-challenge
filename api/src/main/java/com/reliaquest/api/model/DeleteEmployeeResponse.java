package com.reliaquest.api.model;

public class DeleteEmployeeResponse {
    private Boolean data;
    private String status;

    public DeleteEmployeeResponse(Boolean data, String status) {
        this.data = data;
        this.status = status;
    }

    public Boolean getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
