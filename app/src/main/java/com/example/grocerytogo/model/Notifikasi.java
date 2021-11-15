package com.example.grocerytogo.model;

public class Notifikasi {

    public String idNotif, notif, waktu;
    public Integer logo;

    public Notifikasi(){}

    public Notifikasi(String idNotif, String notif, String waktu, Integer logo) {
        this.idNotif = idNotif;
        this.notif = notif;
        this.waktu = waktu;
        this.logo = logo;
    }
}
