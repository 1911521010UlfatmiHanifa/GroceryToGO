package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataPesanan{

    @SerializedName("dpesanan")
    private List<DpesananItem> dpesanan;

    public List<DpesananItem> getDpesanan(){
        return dpesanan;
    }
}