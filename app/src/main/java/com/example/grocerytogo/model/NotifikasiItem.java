package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class NotifikasiItem{

    @SerializedName("pesan")
    private String pesan;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("id")
    private int id;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("title")
    private String title;

    @SerializedName("status_transaksi")
    private String status_transaksi;

    @SerializedName("id_transaksi")
    private int idTransaksi;

    public String getPesan(){
        return pesan;
    }

    public String getWaktu(){
        return waktu;
    }

    public int getId(){
        return id;
    }

    public String getTanggal(){
        return tanggal;
    }

    public int getIdTransaksi(){
        return idTransaksi;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public String getTitle() {
        return title;
    }
}