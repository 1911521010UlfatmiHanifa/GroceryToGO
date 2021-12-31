package com.example.grocerytogo.retrofit;

import androidx.annotation.Nullable;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.DataPesanan;
import com.example.grocerytogo.model.ListAvatar;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;
import com.example.grocerytogo.model.ListKeranjang;
import com.example.grocerytogo.model.ListPesanan;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.ProdukPesanan;
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
    Call<AuthClass> checkLogin(@Field("username") String username,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/register")
    Call<Pesan> register(@Field("username") String username,
                         @Field("password") String password,
                         @Nullable @Field("no_hp") String no_hp);

    @GET("api/kategori")
    Call<ListKategori> getKategori(@Header("token") String token);

    @POST("api/logout")
    Call<Pesan> logout(@Header("token") String token);

    @GET("api/barang/{id_kategori}/{id_user}")
    Call<ListBarang> getBarangKategori(@Header("token") String token,
                                       @Path("id_kategori") Integer id_kategori,
                                       @Path("id_user") Integer id_user);

    @GET("api/user/{id}")
    Call<UserClass> getUser(@Header("token") String token,
                            @Path("id") Integer id);

    @GET("api/detail_barang/{id_barang}")
    Call<ListBarang> getDetailBarang(@Header("token") String token,
                                     @Path("id_barang") Integer id_barang);

    @GET("api/pesananSelesai/{id_user}")
    Call<ListPesanan> getPesananSelesai(@Header("token") String token,
                                        @Path("id_user") Integer id_user);

    @GET("api/pesananProses/{id_user}")
    Call<ListPesanan> getPesananProses(@Header("token") String token,
                                       @Path("id_user") Integer id_user);

    @GET("api/detail_pesanan/{id_transaksi}")
    Call<DataPesanan> getDetailPesanan(@Header("token") String token,
                                       @Path("id_transaksi") String id_transaksi);

    @POST("api/batalkanPesanan/{id_transaksi}")
    Call<Pesan> batalkanPesanan(@Header("token") String token,
                                @Path("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("api/editDataUser/{id}")
    Call<Pesan> editDataDiri(@Header("token") String token,
                             @Field("tanggal_lahir") String tanggal_lahir,
                             @Field("jenis_kelamin") String jenis_kelamin,
                             @Field("no_hp") String no_hp, @Path("id") Integer id,
                             @Nullable @Field("foto") String foto);

    @GET("api/produkPesanan/{id_transaksi}")
    Call<ProdukPesanan> getProdukPesanan(@Header("token") String token,
                                         @Path("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("api/ubahSandi/{id}")
    Call<Pesan> ubahSandi(@Header("token") String token,
                          @Field("passwordLama") String passwordLama,
                          @Field("passwordBaru") String passwordBaru,
                          @Path("id") Integer id);

    @GET("api/keranjang/{id_user}")
    Call<ListKeranjang> getKeranjang(@Header("token") String token,
                                     @Path("id_user") Integer id);

    @POST("api/hapusBarangKeranjang/{id_user}/{id_barang}")
    Call<Pesan> hapusBarang(@Header("token") String token,
                            @Path("id_user") Integer id_user,
                            @Path("id_barang") Integer id_barang);

    @FormUrlEncoded
    @POST("api/tambahBarangKeranjang/{id_user}/{id_barang}")
    Call<Pesan> tambahBarang(@Header("token") String token,
                             @Path("id_user") Integer id_user,
                             @Path("id_barang") Integer id_barang,
                             @Field("jumlah") Integer jumlah);

    @GET("api/avatar")
    Call<ListAvatar> getAvatar(@Header("token") String token);

    @FormUrlEncoded
    @POST("api/transaksi")
    Call<Pesan> transaksi(@Header("token") String token,
                          @Nullable @Field("alamat") String alamat,
                          @Nullable @Field("biaya_kirim") Integer biaya_kirim,
                          @Field("latitude") float latitude,
                          @Field("longitude") float longitude,
                          @Field("id_user") Integer id_user);

}
