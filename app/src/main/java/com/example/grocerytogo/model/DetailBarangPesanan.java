package com.example.grocerytogo.model;

public class DetailBarangPesanan {

    public String namaProduk, hargaProduk, kuantitas;
    public Integer gambarProduk;

    public DetailBarangPesanan(){}

    public DetailBarangPesanan(String namaProduk, String hargaProduk, Integer gambarProduk, String kuantitas) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.gambarProduk = gambarProduk;
        this.kuantitas = kuantitas;
    }
}
