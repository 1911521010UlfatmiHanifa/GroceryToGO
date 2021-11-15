package com.example.grocerytogo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailBarangActivity extends AppCompatActivity {

    private ImageView back, gambar;
    private TextView dnamaBar, harga, namaBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        back = findViewById(R.id.imageView8);
        dnamaBar = findViewById(R.id.detailnamaBar);
        namaBar = findViewById(R.id.textView23);
        harga = findViewById(R.id.textView25);
        gambar = findViewById(R.id.imageView9);

        String Barang = getIntent().getStringExtra("nama");
        dnamaBar.setText("Detail Barang "+Barang);
        namaBar.setText(Barang);
        String Harga = getIntent().getStringExtra("harga").toString();
        harga.setText("Rp. "+Harga);

        Bundle b = getIntent().getExtras();
        int Logo = b.getInt("gambar");
        gambar.setImageResource(Logo);

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}