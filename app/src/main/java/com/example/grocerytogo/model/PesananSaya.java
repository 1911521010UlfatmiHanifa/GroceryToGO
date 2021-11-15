package com.example.grocerytogo.model;

public class PesananSaya {

    public String idPesanan, statusPesanan, hargaPesanan, tanggalPesanan;

    public PesananSaya(){}

    public PesananSaya(String idPesanan, String statusPesanan, String hargaPesanan, String tanggalPesanan) {
        this.idPesanan = idPesanan;
        this.statusPesanan = statusPesanan;
        this.hargaPesanan = hargaPesanan;
        this.tanggalPesanan = tanggalPesanan;
    }
}
