package com.example.grocerytogo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.DetailBarangPesananAdapter;
import com.example.grocerytogo.model.DetailBarangPesanan;

import java.util.ArrayList;

public class ProdukPesananActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView DataDetailBarangPesanan;
    private DetailBarangPesananAdapter detailBarangPesananAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_pesanan);

        back = findViewById(R.id.imageView38);
        DataDetailBarangPesanan = findViewById(R.id.ListBarangPesanan);

        //Set Adapter dan Recycler View
        detailBarangPesananAdapter = new DetailBarangPesananAdapter();
        detailBarangPesananAdapter.setListDetailBarangPesanan(getDataDetailBarangPesanan());
        DataDetailBarangPesanan.setAdapter(detailBarangPesananAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataDetailBarangPesanan.setLayoutManager(layout);

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //Inisiasi Data Recycler View
    public ArrayList<DetailBarangPesanan> getDataDetailBarangPesanan() {
        ArrayList<DetailBarangPesanan> list = new ArrayList<>();
        list.add(new DetailBarangPesanan("Ciki-Ciki", "12000", R.drawable.contoh5, "2"));
        list.add(new DetailBarangPesanan("Buah", "20000", R.drawable.contoh1, "2"));
        list.add(new DetailBarangPesanan("Daging", "120000", R.drawable.contoh4, "1"));
        list.add(new DetailBarangPesanan("Jus", "12000", R.drawable.contoh3, "1"));
        return list;
    }
}