package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    private SaloonData data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setData(SaloonData data){
        this.data = data;
    }

    public SaloonData getData(){
        return data;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "BaseResponse{" +
                        "data = '" + data + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
