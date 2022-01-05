package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListNotifikasi{

    @SerializedName("notifikasi")
    private List<NotifikasiItem> notifikasi;

    public List<NotifikasiItem> getNotifikasi(){
        return notifikasi;
    }
}