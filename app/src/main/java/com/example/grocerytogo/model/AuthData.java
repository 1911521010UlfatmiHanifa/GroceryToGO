package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class AuthData{

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("foto")
    private Object foto;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("jenis_kelamin")
    private Object jenisKelamin;

    @SerializedName("tanggal_lahir")
    private Object tanggalLahir;

    @SerializedName("username")
    private String username;

    @SerializedName("token")
    private String token;

    @SerializedName("no_hp")
    private String no_hp;

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setFoto(Object foto){
        this.foto = foto;
    }

    public Object getFoto(){
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

    public void setJenisKelamin(Object jenisKelamin){
        this.jenisKelamin = jenisKelamin;
    }

    public Object getJenisKelamin(){
        return jenisKelamin;
    }

    public void setTanggalLahir(Object tanggalLahir){
        this.tanggalLahir = tanggalLahir;
    }

    public Object getTanggalLahir(){
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

    public void setNo_hp(String no_hp){
        this.no_hp = no_hp;
    }

    public String getNo_hp(){
        return no_hp;
    }
}