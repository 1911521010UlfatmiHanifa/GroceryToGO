package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListKeranjang{

    @SerializedName("keranjang")
    private List<KeranjangItem> keranjang;

    public List<KeranjangItem> getKeranjang(){
        return keranjang;
    }
}