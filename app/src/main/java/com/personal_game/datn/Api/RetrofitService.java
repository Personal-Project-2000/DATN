package com.personal_game.datn.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit;
    private final static String BASE_URL="https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/";

    public static Retrofit getRetrofitLocation(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
}
