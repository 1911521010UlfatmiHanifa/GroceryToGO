package com.example.grocerytogo.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserClass{

    @SerializedName("user")
    private List<UserItem> user;

    public List<UserItem> getUser(){
        return user;
    }
}