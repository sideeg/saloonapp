package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getAllServiceResponse {


    @SerializedName("data")
    private List<Service> data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<Service> getData() {
        return data;
    }

    public void setData(List<Service> data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
