package com.personal_game.datn.Api.ServiceApi;

import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.Models.User;
import com.personal_game.datn.Request.Request_Bill;
import com.personal_game.datn.Request.Request_UpdateBill;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Address;
import com.personal_game.datn.Response.Message_Bill;
import com.personal_game.datn.Response.Message_Cart;
import com.personal_game.datn.Response.Message_Favourite;
import com.personal_game.datn.Response.Message_Info;
import com.personal_game.datn.Response.Message_Login;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @POST("Resgitration_Post")
    Call<Message> Registration(@Header("Authorization") String token, User user);

    @GET("Login_Get")
    Call<Message_Login> Login(@Header("Authorization") String token, @Query("phone") String phone, @Query("pass") String pass);

    @GET("Info_Get")
    Call<Message_Info> Info(@Header("Authorization") String token);

    @GET("ForgetPass_Get")
    Call<Message_Info> ForgetPass(@Header("Authorization") String token, @Query("passNew") String passNew);

    @POST("UpdateInfo_Post")
    Call<Message_Info> UpdateInfo(@Header("Authorization") String token, User newUser);

    @POST("UpdateImg_Post")
    Call<Message_Info> UpdateImg(@Header("Authorization") String token, @Part List<MultipartBody.Part> photo, @Query("domain") String domain);

    @GET("Favourite_Get")
    Call<Message> AddFavourite(@Header("Authorization") String token, @Query("costumeId") String costumeId);

    @GET("Favourites_Get")
    Call<Message_Favourite> GetFavourites(@Header("Authorization") String token);

    @GET("Cart_Get")
    Call<Message> AddCart(@Header("Authorization") String token, @Query("costumeId") String costumeId);

    @GET("Carts_Get")
    Call<Message_Cart> GetCart(@Header("Authorization") String token);

    @POST("UpdateCart_Post")
    Call<Message> UpdateCart(@Header("Authorization") String token, Cart cart);

    @POST("Address_Post")
    Call<Message> AddAddress(@Header("Authorization") String token, Address newAddress);

    @GET("Addresses_Get")
    Call<Message_Address> GetAddress(@Header("Authorization") String token);

    @POST("UpdateAddress_Post")
    Call<Message> UpdateAddess(@Header("Authorization") String token, Address address);

    @POST("Bill_Post")
    Call<Message> AddBill(@Header("Authorization") String token, Request_Bill bill);

    @GET("BillsWithUser_Get")
    Call<Message_Bill> BillsWithUser(@Header("Authorization") String token);

    @POST("UpdateBill_Post")
    Call<Message> UpdateBill(@Header("Authorization") String token, Request_UpdateBill updateBill);
}