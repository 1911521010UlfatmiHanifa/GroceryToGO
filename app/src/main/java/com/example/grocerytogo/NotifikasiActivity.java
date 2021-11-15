package com.example.grocerytogo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.NotifikasiAdapter;
import com.example.grocerytogo.model.Notifikasi;

import java.util.ArrayList;

public class NotifikasiActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView DataNotifikasi;
    private NotifikasiAdapter notifikasiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        back = findViewById(R.id.imageView13);
        DataNotifikasi = findViewById(R.id.ListNotifikasi);

        //Set Adapter dan Recycler View
        notifikasiAdapter = new NotifikasiAdapter();
        notifikasiAdapter.setListNotifikasi(getDataNotifikasi());
        DataNotifikasi.setAdapter(notifikasiAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataNotifikasi.setLayoutManager(layout);

        //Button Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //Inisialisasi Data Recycler View
    public ArrayList<Notifikasi> getDataNotifikasi(){
        ArrayList<Notifikasi> list = new ArrayList<>();
        list.add(new Notifikasi("Pesanan #123", "Pesanan Sudah Sampai Dan Sudah Diterima dan Aku Sayang kamu", "12:20 WIB", R.drawable.notif));
        list.add(new Notifikasi("Pesanan #123", "Pesanan Belum Sampai", "12:20 WIB", R.drawable.notif));
        list.add(new Notifikasi("Pesanan #123", "Pesanan Sudah Sampai Dan Sudah Diterima", "12:20 WIB", R.drawable.notif));
        list.add(new Notifikasi("Pesanan #123", "Pesanan Belum Sampai", "12:20 WIB", R.drawable.notif));
        return list;
    }
}