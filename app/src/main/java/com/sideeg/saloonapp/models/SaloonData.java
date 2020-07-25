package com.sideeg.saloonapp.models;

import com.google.gson.annotations.SerializedName;

public class SaloonData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("rate")
    private String rate;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lan")
    private String lan;

    @SerializedName("saloon_logo_full_path")
    private String saloon_logo_full_path;

    @SerializedName("commerical_regstration_image_full_path")
    private String commerical_regstration_image_full_path;

    @SerializedName("photo_id_full_path")
    private String photo_id_full_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getSaloon_logo_full_path() {
        return saloon_logo_full_path;
    }

    public void setSaloon_logo_full_path(String saloon_logo_full_path) {
        this.saloon_logo_full_path = saloon_logo_full_path;
    }

    public String getCommerical_regstration_image_full_path() {
        return commerical_regstration_image_full_path;
    }

    public void setCommerical_regstration_image_full_path(String commerical_regstration_image_full_path) {
        this.commerical_regstration_image_full_path = commerical_regstration_image_full_path;
    }

    public String getPhoto_id_full_path() {
        return photo_id_full_path;
    }

    public void setPhoto_id_full_path(String photo_id_full_path) {
        this.photo_id_full_path = photo_id_full_path;
    }
}
