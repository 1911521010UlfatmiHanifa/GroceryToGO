package com.example.grocerytogo.model;

public class BarangBerdasarKategori {

    public String namaProduk, hargaProduk;
    public Integer logoProduk;

    public BarangBerdasarKategori(){}

    public BarangBerdasarKategori(String namaProduk, String hargaProduk, Integer logoProduk) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.logoProduk = logoProduk;
    }
}
