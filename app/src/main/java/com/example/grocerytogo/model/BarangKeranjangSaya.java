package com.example.grocerytogo.model;

public class BarangKeranjangSaya {

    public String nama, gambar;
    public Integer jumlah, harga, id_barang;

    public BarangKeranjangSaya(String nama, Integer harga, String gambar, Integer jumlah, Integer id_barang) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.jumlah = jumlah;
        this.id_barang = id_barang;
    }
}
