package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;

import java.util.ArrayList;

public class BarangKategoriActivity extends AppCompatActivity implements BarangBerdasarkanKategoriAdapter.KlikBarang{

    private ImageView back;
    private RecyclerView DataBarang;
    private TextView namaKategori;
    private BarangBerdasarkanKategoriAdapter barangBerdasarkanKategoriAdapter;
    public  ArrayList<BarangBerdasarKategori> list = new ArrayList<>();
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
        barangBerdasarkanKategoriAdapter = new BarangBerdasarkanKategoriAdapter(list);
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

    //Inisialisasi Data
    public ArrayList<BarangBerdasarKategori> getDataBarangBerdasarkanKategori() {
        list.add(new BarangBerdasarKategori("Daging", "200000", R.drawable.contoh4));
        list.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        list.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        list.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        list.add(new BarangBerdasarKategori("Daging", "200000", R.drawable.contoh4));
        list.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        list.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        list.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        list.add(new BarangBerdasarKategori("Daging", "200000", R.drawable.contoh4));
        list.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        list.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        list.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        return list;
    }

    @Override
    public void onClick(View view, BarangBerdasarKategori barangBerdasarKategori) {
        Intent a = new Intent(BarangKategoriActivity.this, DetailBarangActivity.class);
        String namaBarang = barangBerdasarKategori.namaProduk;
        a.putExtra("nama", namaBarang);
        int gambarBarang = barangBerdasarKategori.logoProduk;
        a.putExtra("gambar", gambarBarang);
        String harga = barangBerdasarKategori.hargaProduk;
        a.putExtra("harga", harga);
        startActivity(a);
    }
}