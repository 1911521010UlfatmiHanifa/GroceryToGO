package com.example.grocerytogo.retrofit;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GtgClient {

    @FormUrlEncoded
    @POST("/login")
    Call<AuthClass> checkLogin(@Field("username") String username, @Field("password") String password);

    @POST("/register")
    Call<AuthClass> register(@Field("username") String username, @Field("no_hp") String no_hp, @Field("password") String password);

    @GET("api/kategori")
    Call<ListKategori> getKategori(@Header("token") String token);

    @GET("api/barang/{id_kategori}")
    Call<ListBarang> getBarang(@Header("token") String token, @Path("id_kategori") String id_kategori);
}
