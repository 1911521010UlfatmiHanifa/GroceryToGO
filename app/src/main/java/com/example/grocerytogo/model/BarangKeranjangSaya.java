package com.example.grocerytogo.model;

public class BarangKeranjangSaya {

    public String nama, gambar;
    public Integer jumlah, harga, id_barang, sub;

    public BarangKeranjangSaya(String nama, Integer harga, String gambar, Integer jumlah, Integer id_barang, Integer sub) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.jumlah = jumlah;
        this.id_barang = id_barang;
        this.sub = sub;
    }
}
