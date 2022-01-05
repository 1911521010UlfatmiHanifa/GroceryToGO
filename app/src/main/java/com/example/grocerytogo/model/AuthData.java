package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class AuthData{

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("foto")
    private String foto;

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

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("fcm_token")
    private String FCMToken;

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }

    public String getFoto(){
        return foto;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setJenisKelamin(String jenisKelamin){
        this.jenisKelamin = jenisKelamin;
    }

    public String getJenisKelamin(){
        return jenisKelamin;
    }

    public void setTanggalLahir(String tanggalLahir){
        this.tanggalLahir = tanggalLahir;
    }

    public String getTanggalLahir(){
        return tanggalLahir;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setFCMToken(String FCMToken){
        this.FCMToken = FCMToken;
    }

    public String getFCMToken(){
        return FCMToken;
    }

    public void setNo_hp(String no_hp){
        this.no_hp = no_hp;
    }

    public String getNo_hp(){
        return no_hp;
    }
}