package com.iak.ali.mycrud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ali on 1/27/2018.
 */

public class ApiClient {
    //public static final String BASE_URL = "http://192.168.1.2/iak_API/";
   public static final String BASE_URL = "http://gagalcoding.000webhostapp.com/penjualan/";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
