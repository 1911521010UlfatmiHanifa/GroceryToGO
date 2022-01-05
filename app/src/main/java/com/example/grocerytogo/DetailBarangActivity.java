package com.example.grocerytogo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangItem;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailBarangActivity extends AppCompatActivity {

    private ImageView back, gambar, log, log2;
    private TextView dnamaBar, harga, namaBar,satuan, ket, des;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        back = findViewById(R.id.imageView8);
        dnamaBar = findViewById(R.id.detailnamaBar);
        namaBar = findViewById(R.id.textView23);
        satuan = findViewById(R.id.textView24);
        harga = findViewById(R.id.textView25);
        ket = findViewById(R.id.textView27);
        gambar = findViewById(R.id.imageView9);
        log = findViewById(R.id.imageView13);
        log2 = findViewById(R.id.imageView23);
        des = findViewById(R.id.textView26);
        progressBar = findViewById(R.id.progressBar4);

        back.setVisibility(View.GONE);
        dnamaBar.setVisibility(View.GONE);
        namaBar.setVisibility(View.GONE);
        satuan.setVisibility(View.GONE);
        harga.setVisibility(View.GONE);
        ket.setVisibility(View.GONE);
        gambar.setVisibility(View.GONE);
        log.setVisibility(View.GONE);
        log2.setVisibility(View.GONE);
        des.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        getDetailBarang();

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getDetailBarang(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Integer idbarang = getIntent().getIntExtra("id", 0);
        Toast.makeText(getApplicationContext(), idbarang.toString(), Toast.LENGTH_SHORT).show();

        Call<ListBarang> call = gtgClient.getDetailBarang(token, idbarang);
        call.enqueue(new Callback<ListBarang>() {
            @Override
            public void onResponse(Call<ListBarang> call, Response<ListBarang> response) {
                ListBarang listBarang = response.body();
                if (listBarang != null) {

                    back.setVisibility(View.VISIBLE);
                    dnamaBar.setVisibility(View.VISIBLE);
                    namaBar.setVisibility(View.VISIBLE);
                    satuan.setVisibility(View.VISIBLE);
                    harga.setVisibility(View.VISIBLE);
                    ket.setVisibility(View.VISIBLE);
                    gambar.setVisibility(View.VISIBLE);
                    log.setVisibility(View.VISIBLE);
                    log2.setVisibility(View.VISIBLE);
                    des.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    List<BarangItem> barangItems = listBarang.getBarang();
                    for (BarangItem item: barangItems) {
                        item.getId();
                        dnamaBar.setText("Detail "+item.getNamaBarang());
                        namaBar.setText(item.getNamaBarang());
                        satuan.setText(item.getUkuranBarang() + " "+ item.getSatuanBarang());
                        ket.setText(item.getKeterangan());
                        Picasso.get().load(api+item.getGambar()).into(gambar);
                        harga.setText("Rp." +item.getHargaBarang());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListBarang> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
            }
        });

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}