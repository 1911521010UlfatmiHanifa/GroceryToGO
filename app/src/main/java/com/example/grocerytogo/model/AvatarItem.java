package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class AvatarItem{

    @SerializedName("id")
    private int id;

    @SerializedName("gambar")
    private String gambar;

    public int getId(){
        return id;
    }

    public String getGambar(){
        return gambar;
    }
}