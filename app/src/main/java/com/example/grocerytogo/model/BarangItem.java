package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class BarangItem{

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("ukuran_barang")
    private int ukuranBarang;

    @SerializedName("id_kategori")
    private int idKategori;

    @SerializedName("satuan_barang")
    private String satuanBarang;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_barang")
    private int hargaBarang;

    @SerializedName("id")
    private int id;

    @SerializedName("gambar")
    private String gambar;

    public String getKeterangan(){
        return keterangan;
    }

    public int getUkuranBarang(){
        return ukuranBarang;
    }

    public int getIdKategori(){
        return idKategori;
    }

    public String getSatuanBarang(){
        return satuanBarang;
    }

    public String getNamaBarang(){
        return namaBarang;
    }

    public int getHargaBarang(){
        return hargaBarang;
    }

    public int getId(){
        return id;
    }

    public String getGambar(){
        return gambar;
    }
}