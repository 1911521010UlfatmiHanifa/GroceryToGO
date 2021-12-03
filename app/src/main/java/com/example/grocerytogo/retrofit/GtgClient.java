package com.example.grocerytogo.retrofit;

import androidx.annotation.Nullable;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserClass;

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

    @FormUrlEncoded
    @POST("/register")
    Call<Pesan> register(@Field("username") String username, @Field("password") String password, @Nullable @Field("no_hp") String no_hp);

    @GET("api/kategori")
    Call<ListKategori> getKategori(@Header("token") String token);

    @POST("api/logout")
    Call<Pesan> logout(@Header("token") String token);

    @GET("api/barang/{id_kategori}")
    Call<ListBarang> getBarangKategori(@Header("token") String token, @Path("id_kategori") Integer id_kategori);

    @GET("api/user/{id}")
    Call<UserClass> getUser(@Header("token") String token, @Path("id") Integer id);
}
