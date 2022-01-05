package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListUserCek{

    @SerializedName("UserCek")
    private List<UserCekItem> userCek;

    public List<UserCekItem> getUserCek(){
        return userCek;
    }
}