package com.example.grocerytogo.model;

public class DetailBarangPesanan {

    public String namaProduk, gambarProduk;
    public Integer hargaProduk, kuantitas;

    public DetailBarangPesanan(){}

    public DetailBarangPesanan(String namaProduk, Integer hargaProduk, String gambarProduk, Integer kuantitas) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.gambarProduk = gambarProduk;
        this.kuantitas = kuantitas;
    }
}
