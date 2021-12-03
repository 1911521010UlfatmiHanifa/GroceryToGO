package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class Pesan{

    @SerializedName("message")
    private String message;

    public String getMessage(){
        return message;
    }
}