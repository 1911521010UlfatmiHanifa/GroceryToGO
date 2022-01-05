package com.example.grocerytogo.model;

public class Notifikasi {

    public String notif, waktu, status;
    public Integer logo, idNotif, idTransaksi;

    public Notifikasi(){}

    public Notifikasi(Integer idNotif, String notif, String waktu, Integer logo, Integer idTransaksi, String status) {
        this.idNotif = idNotif;
        this.notif = notif;
        this.waktu = waktu;
        this.logo = logo;
        this.idTransaksi = idTransaksi;
        this.status = status;
    }
}
