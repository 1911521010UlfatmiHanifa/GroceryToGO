package com.example.grocerytogo;

import com.example.grocerytogo.retrofit.GtgClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Koneksi {
    public GtgClient gtgClient;

    public GtgClient setGtgClient(String API_BASE_URL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.gtgClient = retrofit.create(GtgClient.class);
        return gtgClient;
    }
}
