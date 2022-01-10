package com.example.grocerytogo.model;

import com.google.gson.annotations.SerializedName;

public class DpesananItem{

    @SerializedName("total")
    private int total;

    @SerializedName("subtotal")
    private int subtotal;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("biaya_kirim")
    private int biayaKirim;

    @SerializedName("status_transaksi")
    private String statusTransaksi;

    @SerializedName("status_jemput")
    private String statusJemput;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public String getStatusJemput() {
        return statusJemput;
    }

    @SerializedName("alamat")
    private String alamat;

    public int getTotal(){
        return total;
    }

    public int getSubtotal(){
        return subtotal;
    }

    public String getWaktu(){
        return waktu;
    }

    public String getTanggal(){
        return tanggal;
    }

    public int getBiayaKirim(){
        return biayaKirim;
    }

    public String getStatusTransaksi(){
        return statusTransaksi;
    }

    public String getAlamat(){
        return alamat;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}