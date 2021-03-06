package com.sideeg.saloonapp.networking;

import com.sideeg.saloonapp.models.BaseResponse;
import com.sideeg.saloonapp.models.LoginResponse;
import com.sideeg.saloonapp.models.RegisterResponse;
import com.sideeg.saloonapp.models.SaloonServiceResponse;
import com.sideeg.saloonapp.models.getAllServiceResponse;

import okhttp3.MultipartBody;
import okhttp3.Response;
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
    @POST("saloonservices")
    Call<BaseResponse> addServiceToSaloon(@Field("saloon_id") String saloon_id,
                                          @Field("service_id") String service_id,
                                          @Field("price") String price,
                                          @Field("worker_name") String worker_name);


    @GET("saloonservices")
    Call<SaloonServiceResponse> getSaloonService();

    @GET("services")
    Call<getAllServiceResponse> getAllService();

    @FormUrlEncoded
    @POST("saloons/login")
    Call<LoginResponse> saloonLogin(@Field("name") String name,
                                    @Field("password") String password
    );

    @Multipart
    @POST("saloons/register")
    Call<LoginResponse> registerSaloon(
                                                @Part("phone") String phone,
                                                @Part("name") String user_name,
                                                @Part("email") String email,
                                                @Part("password") String password,
                                                @Part("lat") String lat,
                                                @Part("lang") String lang,
                                                @Part MultipartBody.Part commerical_regstration_image,
                                                @Part MultipartBody.Part saloon_logo,
                                                @Part MultipartBody.Part photo_id
    );




 }
