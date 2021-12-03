package com.example.grocerytogo.model;

public class BarangBerdasarKategori {

    public String namaProduk, satuanProduk, keterangan;
    public String gambar;
    public Integer idProduk;
    public Integer id_kategori;
    public Integer hargaProduk;
    public Integer ukuranProduk;

    public BarangBerdasarKategori(){}

    public BarangBerdasarKategori(Integer idProduk, String namaProduk, String satuanProduk, String keterangan, String gambar, Integer id_kategori, Integer hargaProduk, Integer ukuranProduk) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.satuanProduk = satuanProduk;
        this.keterangan = keterangan;
        this.gambar = gambar;
        this.id_kategori = id_kategori;
        this.hargaProduk = hargaProduk;
        this.ukuranProduk = ukuranProduk;
    }
}
