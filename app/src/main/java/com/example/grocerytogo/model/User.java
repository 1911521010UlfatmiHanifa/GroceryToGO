package com.example.grocerytogo.model;

public class User {

    public String password;
    public String updatedAt;
    public String foto;
    public String noHp;
    public String createdAt;
    public int id;
    public String jenisKelamin;
    public String tanggalLahir;
    public String username;
    public String token;

    public User(String password, String updatedAt, String foto, String noHp, String createdAt, int id, String jenisKelamin, String tanggalLahir, String username, String token) {
        this.password = password;
        this.updatedAt = updatedAt;
        this.foto = foto;
        this.noHp = noHp;
        this.createdAt = createdAt;
        this.id = id;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.username = username;
        this.token = token;
    }
}
