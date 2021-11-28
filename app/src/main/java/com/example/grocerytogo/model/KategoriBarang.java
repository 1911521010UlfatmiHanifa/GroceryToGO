package com.example.grocerytogo.model;

public class KategoriBarang {

    public String kategori, gambar;
    public Integer idKategori;

    public KategoriBarang() {}

    public KategoriBarang(Integer idKategori, String kategori, String gambar) {
        this.idKategori = idKategori;
        this.kategori = kategori;
        this.gambar = gambar;
    }
}
