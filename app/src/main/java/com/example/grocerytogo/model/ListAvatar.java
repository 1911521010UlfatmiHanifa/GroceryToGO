package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListAvatar{

    @SerializedName("avatar")
    private List<AvatarItem> avatar;

    public List<AvatarItem> getAvatar(){
        return avatar;
    }
}