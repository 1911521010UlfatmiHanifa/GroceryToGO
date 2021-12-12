package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProdukPesanan{

    @SerializedName("prodpesan")
    private List<ProdpesanItem> prodpesan;

    public List<ProdpesanItem> getProdpesan(){
        return prodpesan;
    }
}