package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class PesananItem{

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("sum")
    private int sum;

    @SerializedName("id_transaksi")
    private int idTransaksi;

    @SerializedName("status_transaksi")
    private String statusTransaksi;

    public String getWaktu(){
        return waktu;
    }

    public int getSum(){
        return sum;
    }

    public int getIdTransaksi(){
        return idTransaksi;
    }

    public String getStatusTransaksi(){
        return statusTransaksi;
    }
}