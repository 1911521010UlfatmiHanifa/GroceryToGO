package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class KategoriItem{

    @SerializedName("foto")
    private String foto;

    @SerializedName("id")
    private int id;

    @SerializedName("nama_kategori")
    private String namaKategori;

    public String getFoto(){
        return foto;
    }

    public int getId(){
        return id;
    }

    public String getNamaKategori(){
        return namaKategori;
    }
}