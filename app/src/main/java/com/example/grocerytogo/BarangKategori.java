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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangKategori.this, HomeFragment.class);
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