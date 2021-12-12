package com.example.grocerytogo;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.PesananSayaAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangItem;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListPesanan;
import com.example.grocerytogo.model.PesananItem;
import com.example.grocerytogo.model.PesananSaya;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PesananSayaActivity extends AppCompatActivity implements PesananSayaAdapter.KlikPesananSaya{

    private ImageView back, notFound;
    private RecyclerView DataPesananSaya;
    private PesananSayaAdapter pesananSayaAdapter;
    private CardView riwayat, sedangProses;
    private ProgressBar progressBar;
    private TextView pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_saya);

        back = findViewById(R.id.imageView8);
        DataPesananSaya = findViewById(R.id.listPesananSaya);
        riwayat = findViewById(R.id.riwayat);
        sedangProses = findViewById(R.id.cardView2);
        progressBar= findViewById(R.id.progressBar8);
        pesan = findViewById(R.id.pesana);
        notFound = findViewById(R.id.notFounda);

        getPesananProses();
        sedangProses.setCardBackgroundColor(0xFF8BC34A);

        //Card View Riwayat
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sedangProses.setCardBackgroundColor(0xFFFFFFFF);
                riwayat.setCardBackgroundColor(0xFF8BC34A);
                getPesananSelesai();
            }
        });

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
                getPesananProses();
                sedangProses.setCardBackgroundColor(0xFF8BC34A);
                riwayat.setCardBackgroundColor(0xFFFFFFFF);
            }
        });
    }

    private void viewRecyclerView(ArrayList<PesananSaya> listPesananSaya){
        pesananSayaAdapter = new PesananSayaAdapter();
        pesananSayaAdapter.setListPesananSaya(listPesananSaya);
        pesananSayaAdapter.setListener(this);
        DataPesananSaya.setAdapter(pesananSayaAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataPesananSaya.setLayoutManager(layout);
    }

    private void getPesananSelesai(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListPesanan> call = gtgClient.getPesananSelesai(token, id);
        DataPesananSaya.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ListPesanan>() {
            @Override
            public void onResponse(Call<ListPesanan> call, Response<ListPesanan> response) {
                ListPesanan listPesanan = response.body();
                ArrayList<PesananSaya> pesananSayas = new ArrayList<>();
                if(listPesanan != null){
                    DataPesananSaya.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    pesan.setVisibility(View.GONE);
                    notFound.setVisibility(View.GONE);
                    List<PesananItem> pesananItems= listPesanan.getPesanan();
                    for(PesananItem item : pesananItems){
                        PesananSaya pesananSaya = new PesananSaya(
                            item.getSum(),
                            item.getIdTransaksi(),
                            item.getWaktu(),
                            item.getStatusTransaksi()
                        );
                        pesananSayas.add(pesananSaya);
                    }
                    viewRecyclerView(pesananSayas);
                }else if (listPesanan == null){
                    progressBar.setVisibility(View.GONE);
                    pesan.setVisibility(View.VISIBLE);
                    notFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ListPesanan> call, Throwable t) {

            }
        });
    }

    private void getPesananProses(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListPesanan> call = gtgClient.getPesananProses(token, id);
        DataPesananSaya.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ListPesanan>() {
            @Override
            public void onResponse(Call<ListPesanan> call, Response<ListPesanan> response) {
                ListPesanan listPesanan = response.body();
                ArrayList<PesananSaya> pesananSayas = new ArrayList<>();
                if(listPesanan != null){
                    DataPesananSaya.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(GONE);
                    pesan.setVisibility(View.GONE);
                    notFound.setVisibility(View.GONE);
                    List<PesananItem> pesananItems= listPesanan.getPesanan();
                    for(PesananItem item : pesananItems){
                        PesananSaya pesananSaya = new PesananSaya(
                            item.getSum(),
                            item.getIdTransaksi(),
                            item.getWaktu(),
                            item.getStatusTransaksi()
                        );
                        pesananSayas.add(pesananSaya);
                    }
                    viewRecyclerView(pesananSayas);
                }else if (listPesanan == null){
                    progressBar.setVisibility(View.GONE);
                    pesan.setVisibility(View.VISIBLE);
                    notFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ListPesanan> call, Throwable t) {

            }
        });
    }

    public void onClick(View view, PesananSaya pesananSaya) {
        String statusa = pesananSaya.statusPesanan;
        String nomorPesanan = pesananSaya.idPesanan.toString();
        Intent a = new Intent(PesananSayaActivity.this, LihatDetailPesananActivity.class);
        a.putExtra("id", nomorPesanan);
        a.putExtra("status", statusa);
        startActivity(a);

//        switch (statusa){
//            case "Diproses":
//                Intent a = new Intent(PesananSayaActivity.this, SedangMemesanActivity.class);
//                a.putExtra("id", nomorPesanan);
//                break;
//            case "Diterima":
//                 Intent ab = new Intent(this, LihatDetailPesananActivity.class);
//                 ab.putExtra("id", nomorPesanan);
//                 startActivity(ab);
//                 break;
//            case "Dibatalkan":
//                 Intent abc = new Intent(this, LihatDetailPesananActivity.class);
//                 abc.putExtra("id", nomorPesanan);
//                 startActivity(abc);
//                 break;
//        }
    }
}