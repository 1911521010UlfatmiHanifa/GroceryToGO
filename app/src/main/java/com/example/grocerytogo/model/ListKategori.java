package com.example.grocerytogo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListKategori{

    @SerializedName("kategori")
    private List<KategoriItem> kategori;

    public List<KategoriItem> getKategori(){
        return kategori;
    }
}