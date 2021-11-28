package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.PesananSayaAdapter;
import com.example.grocerytogo.model.PesananSaya;

import java.util.ArrayList;

public class PesananSayaActivity extends AppCompatActivity implements PesananSayaAdapter.KlikPesananSaya{

    private ImageView back;
    private RecyclerView DataPesananSaya;
    private PesananSayaAdapter pesananSayaAdapter;
    private CardView riwayat, sedangProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_saya);

        back = findViewById(R.id.imageView8);
        DataPesananSaya = findViewById(R.id.listPesananSaya);
        riwayat = findViewById(R.id.riwayat);
        sedangProses = findViewById(R.id.cardView2);
        sedangProses.setCardBackgroundColor(0xFF8BC34A);

        //Card View Riwayat
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sedangProses.setCardBackgroundColor(0xFFFFFFFF);
                riwayat.setCardBackgroundColor(0xFF8BC34A);
                pesananSayaAdapter.setListPesananSaya(getDataPesananSayaSelesai());
                DataPesananSaya.setAdapter(pesananSayaAdapter);
            }
        });

        //Set Adapter dan Recycler View
        pesananSayaAdapter = new PesananSayaAdapter();
        pesananSayaAdapter.setListPesananSaya(getDataPesananSayaProses());
        pesananSayaAdapter.setListener(this);
        DataPesananSaya.setAdapter(pesananSayaAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataPesananSaya.setLayoutManager(layout);

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Card View Sedang Proses
        sedangProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesananSayaAdapter.setListPesananSaya(getDataPesananSayaProses());
                DataPesananSaya.setAdapter(pesananSayaAdapter);
                sedangProses.setCardBackgroundColor(0xFF8BC34A);
                riwayat.setCardBackgroundColor(0xFFFFFFFF);
            }
        });
    }

    //Inisialisasi AuthData Pesanan Dproses
    public ArrayList<PesananSaya> getDataPesananSayaProses(){
        ArrayList<PesananSaya> list = new ArrayList<>();
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Proses", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Proses", "24000", "Oktober 21, 2021"));
        return list;
    }

    //Inisialisasi AuthData Riwayat Pesanan
    public ArrayList<PesananSaya> getDataPesananSayaSelesai(){
        ArrayList<PesananSaya> list = new ArrayList<>();
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        list.add(new PesananSaya("Pesanan #123", "Diterima", "120000", "Oktober 20, 2021"));
        list.add(new PesananSaya("Pesanan #124", "Diterima", "24000", "Oktober 21, 2021"));
        return list;
    }

    @Override
    public void onClick(View view, PesananSaya pesananSaya) {
        Intent a = new Intent(PesananSayaActivity.this, SedangMemesanActivity.class);
        String nomorPesanan = pesananSaya.idPesanan;
        a.putExtra("id", nomorPesanan);
        startActivity(a);
    }
}