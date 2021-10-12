package com.example.grocerytogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Pengaturan extends AppCompatActivity {

    private CardView lihatDataDiri, ubahKataSandi, pesananSaya, keluar;
    private ImageView home, keranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        lihatDataDiri = findViewById(R.id.LihatProfil);
        ubahKataSandi = findViewById(R.id.UbahKataSandi);
        pesananSaya = findViewById(R.id.PesananSaya);
        keluar = findViewById(R.id.Keluar);
        home = findViewById(R.id.imageView41);
        keranjang = findViewById(R.id.imageVie42);

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Pengaturan.this, KeranjangSaya.class);
                startActivity(i);
            }
        });

        lihatDataDiri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Pengaturan.this, LihatDataDiri.class);
                startActivity(i);
            }
        });

        ubahKataSandi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Pengaturan.this, UbahKataSandi.class);
                startActivity(i);
            }
        });

        pesananSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Pengaturan.this, PesananSaya.class);
                startActivity(i);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new AlertDialog.Builder( Pengaturan.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Keluar")
                        .setMessage("Apakah anda yakin ingin keluar?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Intent in = new Intent(Pengaturan.this, MainActivity.class);
                                startActivity(in);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        })
                        .create().show();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Pengaturan.this, Home.class);
                startActivity(in);
                finish();
            }
        });

    }
}