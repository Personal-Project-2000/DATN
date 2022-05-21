package com.personal_game.datn.Api.ServiceApi;

import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;
import com.personal_game.datn.Api.ModelLocation1.MessageDistrict;
import com.personal_game.datn.Api.ModelLocation1.MessageProvince;
import com.personal_game.datn.Api.ModelLocation1.MessageWard;
import com.personal_game.datn.Api.ModelLocation1.RequestDistrict;
import com.personal_game.datn.Api.ModelLocation1.RequestWard;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceLocation1 {
    @POST("province")
    Call<MessageProvince> GetProvinces(@Header("token") String token);

    @POST("district")
    Call<MessageDistrict> GetDistricts(@Header("token") String token, @Body RequestDistrict code);

    @POST("ward")
    Call<MessageWard> GetWards(@Header("token") String token, @Body RequestWard code);
}
