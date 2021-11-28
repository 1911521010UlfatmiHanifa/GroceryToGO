package com.example.grocerytogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangItem;
import com.example.grocerytogo.model.KategoriBarang;
import com.example.grocerytogo.model.KategoriItem;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarangKategoriActivity extends AppCompatActivity implements BarangBerdasarkanKategoriAdapter.KlikBarang{

    private ImageView back;
    private RecyclerView DataBarang;
    private TextView namaKategori;
    private BarangBerdasarkanKategoriAdapter barangBerdasarkanKategoriAdapter;
    public  ArrayList<BarangBerdasarKategori> barangBerdasarKategoris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_kategori);

        back = findViewById(R.id.image_back);
        DataBarang = findViewById(R.id.ListBarangKategori);
        namaKategori = findViewById(R.id.textNamaKategori);

        String Kategori = getIntent().getStringExtra("nama");
        namaKategori.setText(Kategori);

        //Set Adapter dan Recycler View
        barangBerdasarkanKategoriAdapter = new BarangBerdasarkanKategoriAdapter(barangBerdasarKategoris);
//        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
//        String token = preferences.getString("TOKEN","");
////        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
//
//        String API_BASE_URL = "https://groceriestogo1208.herokuapp.com/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        GtgClient gtgClient = retrofit.create(GtgClient.class);
//
//        String idKategori = getIntent().getStringExtra("id").toString();
////        Toast.makeText(getApplicationContext(), idKategori, Toast.LENGTH_SHORT).show();
//
//        Call<ListBarang> call = gtgClient.getBarang(token, idKategori);
//        call.enqueue(new Callback<ListBarang>() {
//             @Override
//             public void onResponse(Call<ListBarang> call, Response<ListBarang> response) {
//                 ListBarang listBarang = response.body();
//                 ArrayList<BarangBerdasarKategori> barangBerdasarKategoris = new ArrayList<>();
//                 if (listBarang != null) {
//                     List<BarangItem> barangItems = listBarang.getBarang();
//                     for (BarangItem item : barangItems) {
//                         BarangBerdasarKategori barangBerdasarKategori = new BarangBerdasarKategori(
//                                 item.getId(),
//                                 item.getNamaBarang(),
//                                 item.getSatuanBarang(),
//                                 item.getKeterangan(),
//                                 item.getGambar(),
//                                 item.getIdKategori(),
//                                 item.getHargaBarang(),
//                                 item.getUkuranBarang()
//                         );
//                         barangBerdasarKategoris.add(barangBerdasarKategori);
//                         Toast.makeText(getApplicationContext(), barangBerdasarKategori.namaProduk, Toast.LENGTH_SHORT).show();
//                     }
//                 }
//                 barangBerdasarkanKategoriAdapter.setListBarang(barangBerdasarKategoris);
//             }
//
//            @Override
//            public void onFailure(Call<ListBarang> call, Throwable t) {
//
//            }
//        });

        barangBerdasarkanKategoriAdapter.setListBarang(getDataBarangBerdasarkanKategori());
        barangBerdasarkanKategoriAdapter.setListener(this);
        DataBarang.setAdapter(barangBerdasarkanKategoriAdapter);
        GridLayoutManager layout = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false);
        DataBarang.setLayoutManager(layout);

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private ArrayList<BarangBerdasarKategori> getDataBarangBerdasarkanKategori() {
        barangBerdasarKategoris.add(new BarangBerdasarKategori(1, "Daging Ayam", "kg", "1", R.drawable.contoh5,1,20000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(2, "Daging Ikan", "kg", "1", R.drawable.contoh4,1,40000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(3, "Daging Sapi", "kg", "1", R.drawable.contoh3,1,50000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(4, "Daging Kikil", "kg", "1", R.drawable.contoh2,1,20000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(1, "Daging Ayam", "kg", "1", R.drawable.contoh5,1,20000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(2, "Daging Ikan", "kg", "1", R.drawable.contoh4,1,40000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(3, "Daging Sapi", "kg", "1", R.drawable.contoh3,1,50000,1));
        barangBerdasarKategoris.add(new BarangBerdasarKategori(4, "Daging Kikil", "kg", "1", R.drawable.contoh2,1,20000,1));
        return barangBerdasarKategoris;
    }

    @Override
    public void onClick(View view, BarangBerdasarKategori barangBerdasarKategori) {
        Intent a = new Intent(BarangKategoriActivity.this, DetailBarangActivity.class);
        String namaBarang = barangBerdasarKategori.namaProduk;
        a.putExtra("nama", namaBarang);
        int gambarBarang = barangBerdasarKategori.gambar;
        a.putExtra("gambar", gambarBarang);
        String harga = barangBerdasarKategori.hargaProduk.toString();
        a.putExtra("harga", harga);
        startActivity(a);
    }
}