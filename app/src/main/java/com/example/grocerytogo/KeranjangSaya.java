package com.example.grocerytogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class KeranjangSaya extends AppCompatActivity {

    private ImageView home, setting;
    private Button produkpesan, pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_saya);

        home = findViewById(R.id.imageView41);
        setting = findViewById(R.id.atur);
        produkpesan = findViewById(R.id.btn_produkpesan);
        pesan = findViewById(R.id.btn_pesan);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeranjangSaya.this, Home.class);
                startActivity(i);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeranjangSaya.this, Pengaturan.class);
                startActivity(i);
            }
        });

        produkpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeranjangSaya.this, Home.class);
                startActivity(i);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeranjangSaya.this, PesananSaya.class);
                startActivity(i);
            }
        });
    }
}