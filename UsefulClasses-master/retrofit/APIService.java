package com.underxs.app.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;
import com.underxs.app.models.AddressRespose;
import com.underxs.app.models.ApiResponse;
import com.underxs.app.models.CodeValidatePojo;
import com.underxs.app.models.JobDetailResponse;
import com.underxs.app.models.LocationModel;
import com.underxs.app.models.LocationResponse;
import com.underxs.app.models.LoginSignUpPojo;
import com.underxs.app.models.SuccessMessagePojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by priya on 5/9/17.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("users/login")
    /*@Headers("Content-Type:application/json")*/
    Call<LoginSignUpPojo> login(@Field("email_phone") String email_phone, @Field("password") String password, @Field("platform") String platform, @Field("device_id") String device_id, @Field("device_token") String device_token);

    @POST("job/{job_id}/give_feedback/")
    Call<SuccessMessagePojo> feedback(@Body JsonObject feedback, @Header("Authorization") String token, @Path("job_id") String job_id);

    @POST("users/register/")
    Call<LoginSignUpPojo> register(@Body JsonObject objectData);

    @POST("operator/customer/create")
    Call<ApiResponse> createCustomer(@Body JsonObject objectData, @Header("Authorization") String token);

    @POST("job/create")
    Call<SuccessMessagePojo> createJob(@Body JsonObject objectData, @Header("Authorization") String token);

    @POST("users/logout/")
    Call<SuccessMessagePojo> logout(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("users/forgotPassword/")
    Call<SuccessMessagePojo> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("operator/customer/search")
    Call<ApiResponse> searchCustomer(@Field("keyword") String keyword, @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("users/validateRegistrationCode/")
    Call<CodeValidatePojo> registration_code(@Field("registration_code") String code);

    @FormUrlEncoded
    @POST("users/edit_profile/")
    Call<SuccessMessagePojo> edit_profile(@Field("name") String name, @Field("phone") String phone, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("users/setRegistrationCodePassword/")
    Call<LoginSignUpPojo> setRegistrationCodePassword(@Field("registration_code") String registration_code, @Field("new_password") String new_password, @Field("confirm_new_password") String confirm_new_password, @Field("platform") String platform, @Field("device_id") String device_id, @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("users/change_password/")
    Call<SuccessMessagePojo> change_password(@Field("old_password") String old_password, @Field("new_password") String new_password, @Field("confirm_password") String confirm_password, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("car/add")
    Call<SuccessMessagePojo> addCar(@Field("make") String make, @Field("model") String model, @Field("color") String color, @Field("registration_no") String registration_no, @Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("car")
    Call<SuccessMessagePojo> updateCar(@Field("car_id") String cardId, @Field("make") String make, @Field("model") String model, @Field("color") String color, @Field("registration_no") String registration_no, @Header("Authorization") String token);

    @GET("service")
    Call<ApiResponse> getServices(@Header("Authorization") String token);

    @GET
    Call<ApiResponse> getCustomerData(@Url String url, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("underxsLocations")
    Call<LocationResponse> getShopLocations(@Field("lat") double lat, @Field("lng") double lng, @Header("Authorization") String token);

    @GET("getCarAllMake")
    Call<ApiResponse> getAllMake(@Header("Authorization") String token);

    @GET("car")
    Call<ApiResponse> getCars(@Header("Authorization") String token);

    @GET("operator/ratings")
    Call<ApiResponse> getFeedbackRatings(@Header("Authorization") String token);


    @HTTP(method = "DELETE", path = "car", hasBody = true)
    Call<SuccessMessagePojo> deleteCar(@Body JsonObject car_id, @Header("Authorization") String token);

    @GET
    Call<ApiResponse> getmodels(@Url String url, @Header("Authorization") String token);

    @GET("users/addresses/")
    Call<AddressRespose> getAddresses(@Header("Authorization") String token);

    @GET("job/{job_id}")
    Call<JobDetailResponse> getFeedBackDetails(@Header("Authorization") String token, @Path("job_id") String jobId);

    @HTTP(method = "DELETE", path = "users/addresses/", hasBody = true)
    Call<SuccessMessagePojo> deleteAddresses(@Header("Authorization") String token, @Body JsonObject body);

    @POST("users/addresses/")
    Call<SuccessMessagePojo> addAddress(@Header("Authorization") String token, @Body JsonObject body);

    @GET
    Call<LocationModel> getPlaceDetail(@Url String url);

    @POST("job")
    Call<ApiResponse> getRepairs(@Header("Authorization") String token, @Body JsonObject body);

    @POST("job/scheduling")
    Call<ApiResponse> getRepairsScheduled(@Header("Authorization") String token, @Body JsonObject body);

    @POST("job/change_status")
    Call<ApiResponse> acceptQuote(@Header("Authorization") String token, @Body JsonObject body);

    @GET("job/{job_id}/job_start")
    Call<ApiResponse> jobStart(@Header("Authorization") String token, @Path("job_id") String job_id);

    @GET("job/{job_id}/job_complete")
    Call<ApiResponse> jobComplete(@Header("Authorization") String token, @Path("job_id") String job_id);

    @POST("job/{job_id}/{service_id}/{repair_id}/change_status")
    Call<ApiResponse> serviceStatus(@Header("Authorization") String token, @Path("job_id") String jobId, @Path("service_id") String serviceId, @Path("repair_id") String repairId, @Body JsonObject body);

    @GET("job/{job_id}")
    Call<JobDetailResponse> jobService(@Header("Authorization") String token, @Path("job_id") String jobId);

    @Multipart
    @POST("job/{job_id}/{service_id}/{repair_id}/upload_image")
    Call<ApiResponse> uploadImage(@Header("Authorization") String token, @Path("job_id") String jobId, @Path("service_id") String serviceId, @Path("repair_id") String repairId,@NonNull @Part MultipartBody.Part file);

}
