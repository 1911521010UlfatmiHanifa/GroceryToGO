package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListBarang{

    @SerializedName("barang")
    private List<BarangItem> barang;

    public List<BarangItem> getBarang(){
        return barang;
    }
}