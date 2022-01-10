package com.example.grocerytogo;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.NotifikasiAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.ListNotifikasi;
import com.example.grocerytogo.model.ListPesanan;
import com.example.grocerytogo.model.Notifikasi;
import com.example.grocerytogo.model.NotifikasiItem;
import com.example.grocerytogo.model.PesananItem;
import com.example.grocerytogo.model.PesananSaya;
import com.example.grocerytogo.retrofit.GtgClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity implements NotifikasiAdapter.KlikNotifikasi{

    private ImageView back;
    private RecyclerView DataNotifikasi;
    private NotifikasiAdapter notifikasiAdapter;
    Integer ab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        back = findViewById(R.id.imageView13);
        DataNotifikasi = findViewById(R.id.ListNotifikasi);

        //Set Adapter dan Recycler View
        notifikasiAdapter = new NotifikasiAdapter();
//        notifikasiAdapter.setListNotifikasi(getDataNotifikasi());
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

        getNotifikasis();
    }

    private void getNotifikasis(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListNotifikasi> call = gtgClient.getNotifikasi(token, id);
        call.enqueue(new Callback<ListNotifikasi>() {
            @Override
            public void onResponse(Call<ListNotifikasi> call, Response<ListNotifikasi> response) {
                ListNotifikasi listNotifikasi = response.body();
                ArrayList<Notifikasi> notifikasis = new ArrayList<>();
                if(listNotifikasi != null){
                    List<NotifikasiItem> notifikasiItems = listNotifikasi.getNotifikasi();
                    for (NotifikasiItem item: notifikasiItems){
                        if(item.getPesan().equals("Pesanan Berhasil Dibatalkan")){
                            ab = R.drawable.notif;
                        }else{
                            ab = R.drawable.belanja;
                        }
                        Notifikasi notifikasi = new Notifikasi(
                            item.getId(),
                            item.getPesan(),
                            item.getTanggal()+" "+item.getWaktu(),
                            ab,
                            item.getIdTransaksi(),
                            item.getStatus_transaksi(),
                            item.getTitle()
                        );
                        notifikasis.add(notifikasi);
                    }
                    viewRecyclerView(notifikasis);
                }
            }

            @Override
            public void onFailure(Call<ListNotifikasi> call, Throwable t) {

            }
        });
    }

    private void viewRecyclerView(ArrayList<Notifikasi> listNotifikasi){
        notifikasiAdapter = new NotifikasiAdapter();
        notifikasiAdapter.setListNotifikasi(listNotifikasi);
        notifikasiAdapter.setListener((NotifikasiAdapter.KlikNotifikasi) this);
        DataNotifikasi.setAdapter(notifikasiAdapter);
//        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager layout = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataNotifikasi.setLayoutManager(layout);
    }

    @Override
    public void onClick(View view, Notifikasi notifikasi) {
        String id_transaksi = notifikasi.idTransaksi.toString();
        String status = notifikasi.status;
        finish();
        Intent a = new Intent(NotifikasiActivity.this, LihatDetailPesananActivity.class);
        a.putExtra("id", id_transaksi);
        a.putExtra("status", status);
        startActivity(a);
    }
}