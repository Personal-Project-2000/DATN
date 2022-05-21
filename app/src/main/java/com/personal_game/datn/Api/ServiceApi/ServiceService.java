package com.personal_game.datn.Api.ServiceApi;

import com.personal_game.datn.Api.ModelFee.MessageFee;
import com.personal_game.datn.Api.ModelFee.RequestFee;
import com.personal_game.datn.Api.ModelFee.RequestService;
import com.personal_game.datn.Api.ModelFee.MessageService;
import com.personal_game.datn.Api.ModelLocation1.MessageDistrict;
import com.personal_game.datn.Api.ModelLocation1.RequestDistrict;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServiceService {
    @POST("available-services")
    Call<MessageService> GetServices(@Header("token") String token, @Body RequestService requestService);

    @POST("fee")
    Call<MessageFee> GetFee(@Header("token") String token, @Body RequestFee requestFee);
}
