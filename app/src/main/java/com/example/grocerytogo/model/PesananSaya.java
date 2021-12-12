package com.example.grocerytogo.model;

public class PesananSaya {

    public String statusPesanan, tanggalPesanan;
    public Integer idPesanan, hargaPesanan;

    public PesananSaya(){}

    public PesananSaya(Integer hargaPesanan, Integer idPesanan, String tanggalPesanan, String statusPesanan) {
        this.idPesanan = idPesanan;
        this.statusPesanan = statusPesanan;
        this.hargaPesanan = hargaPesanan;
        this.tanggalPesanan = tanggalPesanan;
    }
}
