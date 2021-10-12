package com.example.grocerytogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SedangMemesan extends AppCompatActivity {

    private Button detail_produk, batal_pesan;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedang_memesan);

        detail_produk = findViewById(R.id.detail_produk2);
        batal_pesan = findViewById(R.id.btn_batal);
        back = findViewById(R.id.imageView35);

        detail_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SedangMemesan.this, ProdukPesanan.class);
                startActivity(i);
            }
        });

        batal_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder( SedangMemesan.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Keluar")
                        .setMessage("Apakah anda yakin ingin membatalkan?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Intent in = new Intent(SedangMemesan.this, PesananSaya.class);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}