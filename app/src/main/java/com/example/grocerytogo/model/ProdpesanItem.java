package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class ProdpesanItem{

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("id_barang")
    private int idBarang;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_barang")
    private int hargaBarang;

    @SerializedName("gambar")
    private String gambar;

    public int getJumlah(){
        return jumlah;
    }

    public int getIdBarang(){
        return idBarang;
    }

    public String getNamaBarang(){
        return namaBarang;
    }

    public int getHargaBarang(){
        return hargaBarang;
    }

    public String getGambar(){
        return gambar;
    }
}