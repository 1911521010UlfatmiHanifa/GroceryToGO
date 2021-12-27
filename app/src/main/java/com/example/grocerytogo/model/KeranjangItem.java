package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class KeranjangItem{

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("id_barang")
    private int idBarang;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("gambar")
    private String gambar;

    @SerializedName("harga_barang")
    private int hargaBarang;

    public int getJumlah(){
        return jumlah;
    }

    public int getIdBarang(){
        return idBarang;
    }

    public String getNamaBarang(){
        return namaBarang;
    }

    public String getGambar(){
        return gambar;
    }

    public int getHargaBarang(){
        return hargaBarang;
    }
}