package com.personal_game.datn.Api.ServiceApi;

import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceLocation {
    @GET("api/p/")
    Call<ArrayList<ProvinceModel>> GetProvinces();

    @GET("api/p/{code}?depth=2")
    Call<ProvinceModel> GetDistricts(@Path("code") String code);

    @GET("api/d/{code}?depth=2")
    Call<DistrictModel> GetWards(@Path("code") String code);
}
