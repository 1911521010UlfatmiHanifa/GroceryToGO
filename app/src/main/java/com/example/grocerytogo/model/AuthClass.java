package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class AuthClass{

    @SerializedName("AuthData")
    private AuthData authData;

    public void setAuthData(AuthData authData){
        this.authData = authData;
    }

    public AuthData getAuthData(){
        return authData;
    }
}