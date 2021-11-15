package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LihatDetailPesananActivity extends AppCompatActivity {

    private ImageView back;
    private Button produk;
    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_pesanan);

        back = findViewById(R.id.imageView24);
        produk = findViewById(R.id.detail_produk2);
        id = findViewById(R.id.textId);

        String IDPesanan = getIntent().getStringExtra("id");
        id.setText(IDPesanan);

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Button Tampilkan Produk Pesanan
        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LihatDetailPesananActivity.this, ProdukPesananActivity.class);
                startActivity(i);
            }
        });
    }
}