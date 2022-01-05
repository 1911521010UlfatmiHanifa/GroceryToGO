package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class UserCekItem{

    @SerializedName("password")
    private String password;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("foto")
    private String foto;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("fcm_token")
    private String fcmToken;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    @SerializedName("tanggal_lahir")
    private String tanggalLahir;

    @SerializedName("username")
    private String username;

    @SerializedName("token")
    private String token;

    public String getPassword(){
        return password;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public String getFoto(){
        return foto;
    }

    public String getNoHp(){
        return noHp;
    }

    public String getFcmToken(){
        return fcmToken;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public int getId(){
        return id;
    }

    public String getJenisKelamin(){
        return jenisKelamin;
    }

    public String getTanggalLahir(){
        return tanggalLahir;
    }

    public String getUsername(){
        return username;
    }

    public String getToken(){
        return token;
    }
}