package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("id")
    private String id;

    @SerializedName("service")
    private String service_name;

    @SerializedName("service_photo_full_path")
    private String photo_full_path;

    @SerializedName("worker_name")
    private String worker_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPhoto_full_path() {
        return photo_full_path;
    }

    public void setPhoto_full_path(String photo_full_path) {
        this.photo_full_path = photo_full_path;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }
}
