package com.sideeg.saloonapp.networking;

import com.sideeg.saloonapp.models.LoginResponse;
import com.sideeg.saloonapp.models.RegisterResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface NetWorkApi {
    ////////////////////////////COMPANY LOGIN//////////////////////////////


    @FormUrlEncoded
    @POST("pharmacy/login")
    Call<LoginResponse> pharmacyLogin(@Field("email") String email,
                                      @Field("password") String password
    );

    @Multipart
    @POST("pharmacy/register")
    Call<RegisterResponse> registrationPharmacy(
                                                @Part("user_phone") String phone,
                                                @Part("user_name") String user_name,
                                                @Part("user_email") String email,
                                                @Part("license_no") String license_no,
                                                @Part("password") String password,
                                                @Part("lat") double lat,
                                                @Part("lang") double lang,
                                                @Part MultipartBody.Part commerical_regstration_image,
                                                @Part MultipartBody.Part saloon_logo,
                                                @Part MultipartBody.Part photo_id
    );


 }
