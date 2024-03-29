package com.example.grocerytogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangItem;
import com.example.grocerytogo.model.KategoriBarang;
import com.example.grocerytogo.model.KategoriItem;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarangKategoriActivity extends AppCompatActivity implements BarangBerdasarkanKategoriAdapter.KlikBarang{

    private ImageView back, notFound;
    private RecyclerView DataBarang;
    private TextView namaKategori, pesan;
    private BarangBerdasarkanKategoriAdapter barangBerdasarkanKategoriAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_kategori);

        back = findViewById(R.id.image_back);
        namaKategori = findViewById(R.id.textNamaKategori);
        DataBarang = findViewById(R.id.ListBarangKategori);
        progressBar = findViewById(R.id.progressBar3);
        notFound = findViewById(R.id.notFound3);
        pesan = findViewById(R.id.pesan3);

        namaKategori.setText(getIntent().getStringExtra("nama"));
        Integer idKategori = getIntent().getIntExtra("id", 0);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListBarang> call = gtgClient.getBarangKategori(token, idKategori, id);

        DataBarang.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ListBarang>() {
             @Override
             public void onResponse(Call<ListBarang> call, Response<ListBarang> response) {
                 ListBarang listBarang = response.body();
                 ArrayList<BarangBerdasarKategori> barangBerdasarKategoris = new ArrayList<>();
                 if (listBarang != null) {
                     DataBarang.setVisibility(View.VISIBLE);
                     progressBar.setVisibility(View.INVISIBLE);
                     List<BarangItem> barangItems = listBarang.getBarang();
                     for (BarangItem item: barangItems) {
                         BarangBerdasarKategori barangBerdasarKategori = new BarangBerdasarKategori(
                             item.getId(),
                             item.getNamaBarang(),
                             item.getSatuanBarang(),
                             item.getKeterangan(),
                             api+item.getGambar(),
                             item.getIdKategori(),
                             item.getHargaBarang(),
                             item.getUkuranBarang()
                         );
                         barangBerdasarKategoris.add(barangBerdasarKategori);
                     }
                     viewRecyclerView(barangBerdasarKategoris);
                     if(barangBerdasarKategoris.isEmpty()){
                         notFound.setVisibility(View.VISIBLE);
                         pesan.setVisibility(View.VISIBLE);
                     }else{
                         notFound.setVisibility(View.GONE);
                         pesan.setVisibility(View.GONE);
                     }
                 }
             }

            @Override
            public void onFailure(Call<ListBarang> call, Throwable t) {
                DataBarang.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void viewRecyclerView(ArrayList<BarangBerdasarKategori> listBarangKategori){
        barangBerdasarkanKategoriAdapter = new BarangBerdasarkanKategoriAdapter();
        barangBerdasarkanKategoriAdapter.setListBarang(listBarangKategori);
        barangBerdasarkanKategoriAdapter.setListener(this);
        DataBarang.setAdapter(barangBerdasarkanKategoriAdapter);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        DataBarang.setLayoutManager(layout);
    }

    @Override
    public void onClick(View view, BarangBerdasarKategori barangBerdasarKategori) {
        finish();
        Intent a = new Intent(BarangKategoriActivity.this, DetailBarangActivity.class);
        Integer id = barangBerdasarKategori.idProduk;
        a.putExtra("id", id);
        startActivity(a);
    }
}