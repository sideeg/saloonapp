package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaloonServiceResponse {


    @SerializedName("data")
    private List<SaloonServiceData> data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SaloonServiceData> getData() {
        return data;
    }

    public void setData(List<SaloonServiceData> data) {
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
