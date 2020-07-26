package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaloonServiceData {

    @SerializedName("id")
    private String id;

    @SerializedName("saloon_id")
    private String saloon_id;

    @SerializedName("service_id")
    private String service_id;

    @SerializedName("price")
    private String price;

    @SerializedName("saloons")
    private SaloonData saloons;

    @SerializedName("services")
    private Service services;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaloon_id() {
        return saloon_id;
    }

    public void setSaloon_id(String saloon_id) {
        this.saloon_id = saloon_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public SaloonData getSaloons() {
        return saloons;
    }

    public void setSaloons(SaloonData saloons) {
        this.saloons = saloons;
    }

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
        this.services = services;
    }
}
