package com.example.grocerytogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    private ImageView settingg, notifikasi,keranjang, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settingg = findViewById(R.id.settingImage);
        notifikasi = findViewById(R.id.imageView12);
        keranjang = findViewById(R.id.imageView42);
        // otw hapus
        detail = findViewById(R.id.imageView17);

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, BarangKategori.class);
                startActivity(i);
            }
        });

        settingg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(Home.this, Pengaturan.class);
                startActivity(in);
            }
        });

        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Notifikasi.class);
                startActivity(i);
            }
        });

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, KeranjangSaya.class);
                startActivity(i);
            }
        });
    }
}