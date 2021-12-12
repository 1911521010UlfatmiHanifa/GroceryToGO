package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListPesanan{

    @SerializedName("pesanan")
    private List<PesananItem> pesanan;

    public List<PesananItem> getPesanan(){
        return pesanan;
    }
}