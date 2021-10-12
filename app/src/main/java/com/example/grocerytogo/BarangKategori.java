package com.example.grocerytogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BarangKategori extends AppCompatActivity {

    private ImageView back, detail, home, keranjang, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_kategori);

        back = findViewById(R.id.imageView25);
        home = findViewById(R.id.imageView41);
        keranjang = findViewById(R.id.imageView42);
        setting = findViewById(R.id.pengat);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, Home.class);
                startActivity(i);
            }
        });

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, KeranjangSaya.class);
                startActivity(i);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, Pengaturan.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, Home.class);
                startActivity(i);
            }
        });

        //otw hapus
        detail = findViewById(R.id.imageView26);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, DetailBarang.class);
                startActivity(i);
            }
        });
    }
}