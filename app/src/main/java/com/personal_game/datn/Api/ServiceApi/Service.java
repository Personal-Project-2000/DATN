package com.personal_game.datn.Api.ServiceApi;

import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.Models.User;
import com.personal_game.datn.Request.Request_AddCart;
import com.personal_game.datn.Request.Request_Bill;
import com.personal_game.datn.Request.Request_Suggestion;
import com.personal_game.datn.Request.Request_UpdateBill;
import com.personal_game.datn.Response.BeforeCreateBill;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Address;
import com.personal_game.datn.Response.Message_Bill;
import com.personal_game.datn.Response.Message_Cart;
import com.personal_game.datn.Response.Message_Costume;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.Response.Message_Favourite;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.Response.Message_Info;
import com.personal_game.datn.Response.Message_Login;
import com.personal_game.datn.Response.Message_Suggestion;
import com.personal_game.datn.Response.Message_Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @POST("Resgitration_Post")
    Call<Message> Registration(@Header("Authorization") String token, @Body User user);

    @GET("Login_Get")
    Call<Message_Login> Login(@Header("Authorization") String token, @Query("phone") String phone, @Query("pass") String pass);

    @GET("Info_Get")
    Call<Message_Info> Info(@Header("Authorization") String token);

    @GET("ForgetPass_Get")
    Call<Message> ForgetPass(@Header("Authorization") String token, @Query("phone") String phone, @Query("passNew") String passNew);

    @POST("UpdateInfo_Post")
    Call<Message> UpdateInfo(@Header("Authorization") String token, @Body User newUser);

    @POST("UpdateImg_Post")
    @Multipart
    Call<Message> UpdateImg(@Header("Authorization") String token, @Part MultipartBody.Part photo, @Query("domain") String domain);

    @GET("Favourite_Get")
    Call<Message> AddFavourite(@Header("Authorization") String token, @Query("costumeId") String costumeId);

    @GET("Favourites_Get")
    Call<Message_Favourite> GetFavourites(@Header("Authorization") String token);

    @POST("Cart_Post")
    Call<Message> AddCart(@Header("Authorization") String token, @Body Request_AddCart input);

    @GET("Carts_Get")
    Call<Message_Cart> GetCart(@Header("Authorization") String token);

    @POST("UpdateCart_Post")
    Call<Message> UpdateCart(@Header("Authorization") String token, @Body Cart cart);

    @POST("UpdateAllCart_Post")
    Call<Message> UpdateAllCart(@Header("Authorization") String token);

    @POST("Address_Post")
    Call<Message> AddAddress(@Header("Authorization") String token, @Body Address newAddress);

    @GET("Addresses_Get")
    Call<Message_Address> GetAddress(@Header("Authorization") String token);

    @POST("UpdateAddress_Post")
    Call<Message> UpdateAddess(@Header("Authorization") String token, @Body Address address);

    @POST("Bill_Post")
    Call<Message> AddBill(@Header("Authorization") String token, @Body Request_Bill bill);

    @GET("BillsWithUser_Get")
    Call<Message_Bill> BillsWithUser(@Header("Authorization") String token);

    @POST("UpdateBill_Post")
    Call<Message> UpdateBill(@Header("Authorization") String token, @Body Request_UpdateBill updateBill);

    @GET("Costume_Get")
    Call<Message_Costume> GetCostume(@Header("Authorization") String token, @Query("costumeId") String costumeId);

    @GET("CostumeWithStyles_Get")
    Call<Message_CostumeWithStyle> CostumeWithStyles(@Header("Authorization") String token, @Query("costumeStyleId") String costumeStyleId);

    @POST("CostumeWithSugs_Post")
    Call<Message_CostumeWithStyle> CostumeWithSugs(@Header("Authorization") String token, @Body Request_Suggestion request);

    @GET("Tests_Get")
    Call<Message_Test> GetTests(@Header("Authorization") String token);

    @GET("Suggestion_Get")
    Call<Message_Suggestion> GetSuggestion(@Header("Authorization") String token);

    @GET("Home_Get")
    Call<Message_Home> GetHome(@Header("Authorization") String token);

    @POST("CostumeSearch_Post")
    Call<Message_CostumeWithStyle> CostumeSearch(@Header("Authorization") String token, @Query("inputSearch") String inputSearch);

    @GET("bills/before-create")
    Call<BeforeCreateBill> BeforeCreate(@Header("Authorization") String token);
}
