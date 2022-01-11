package com.example.grocerytogo;

import static android.view.View.GONE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.DetailBarangPesananAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.DetailBarangPesanan;
import com.example.grocerytogo.model.ListPesanan;
import com.example.grocerytogo.model.PesananItem;
import com.example.grocerytogo.model.PesananSaya;
import com.example.grocerytogo.model.ProdpesanItem;
import com.example.grocerytogo.model.ProdukPesanan;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getDataProduk();

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getDataProduk(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        String IDPesanan = getIntent().getStringExtra("id_transaksi");

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ProdukPesanan> call = gtgClient.getProdukPesanan(token, IDPesanan);
        call.enqueue(new Callback<ProdukPesanan>() {
            @Override
            public void onResponse(Call<ProdukPesanan> call, Response<ProdukPesanan> response) {
                ProdukPesanan produkPesanan = response.body();
                ArrayList<DetailBarangPesanan> detailBarangPesanans = new ArrayList<>();
                if(produkPesanan != null){
                    List<ProdpesanItem> prodpesanItems = produkPesanan.getProdpesan();
                    for (ProdpesanItem item : prodpesanItems){
                        DetailBarangPesanan detailBarangPesanan = new DetailBarangPesanan(
                                item.getNamaBarang(),
                                item.getHargaBarang(),
                                api+item.getGambar(),
                                item.getJumlah()
                        );
                        detailBarangPesanans.add(detailBarangPesanan);
                    }
                    viewRecyclerView(detailBarangPesanans);
                }
            }

            @Override
            public void onFailure(Call<ProdukPesanan> call, Throwable t) {

            }
        });
    }

    private void viewRecyclerView(ArrayList<DetailBarangPesanan> detailBarangPesanans){
        detailBarangPesananAdapter = new DetailBarangPesananAdapter();
        detailBarangPesananAdapter.setListDetailBarangPesanan(detailBarangPesanans);
        DataDetailBarangPesanan.setAdapter(detailBarangPesananAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DataDetailBarangPesanan.setLayoutManager(layout);
    }
}