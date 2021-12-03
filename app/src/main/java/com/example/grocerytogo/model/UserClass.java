package com.example.grocerytogo.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserClass{

    @SerializedName("user")
    private ArrayList<UserItem> user;

    public ArrayList<UserItem> getUserData(){
        return user;
    }
}