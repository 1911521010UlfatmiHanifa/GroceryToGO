package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class PesananItem{

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("sum")
    private int sum;

    @SerializedName("id")
    private int idTransaksi;

    @SerializedName("status_transaksi")
    private String statusTransaksi;

    @SerializedName("status_jemput")
    private String statusJemput;

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

    public String getStatusJemput() {
        return statusJemput;
    }
}