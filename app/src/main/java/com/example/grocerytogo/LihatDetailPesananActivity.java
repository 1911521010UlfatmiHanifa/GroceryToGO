package com.example.grocerytogo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.grocerytogo.model.DataPesanan;
import com.example.grocerytogo.model.DpesananItem;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserClass;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LihatDetailPesananActivity extends AppCompatActivity {

    private ImageView back;
    private Button produk, batalkanPesanan;
    private TextView id, tanggal, waktu, alamat, subttotal, total, status, ogkir, tulAlamat, lihatLokasi;
    private ProgressBar progressBar;
    private float lat, longs;
    private String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_pesanan);

        back = findViewById(R.id.imageView24);
        produk = findViewById(R.id.detail_produk2);
        id = findViewById(R.id.textId);
        tanggal = findViewById(R.id.textView38);
        waktu = findViewById(R.id.textWaktu);
        tulAlamat = findViewById(R.id.textView39);
        alamat = findViewById(R.id.textView40);
        subttotal = findViewById(R.id.textView45);
        ogkir = findViewById(R.id.textView46);
        total = findViewById(R.id.textView47);
        status = findViewById(R.id.textView48);
        batalkanPesanan = findViewById(R.id.btn_batal2);
        progressBar = findViewById(R.id.progressBar9);
        lihatLokasi = findViewById(R.id.textView51);

        lihatLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LihatDetailPesananActivity.this, LihatLocActivity.class);
                a.putExtra("Lat", lat);
                a.putExtra("Long", longs);
                startActivity(a);
            }
        });

        String IDPesanan = getIntent().getStringExtra("id");
        id.setText("Pesanan "+IDPesanan);
        String statusa = getIntent().getStringExtra("status");
        if(statusa.equals("Diproses")){
            batalkanPesanan.setVisibility(View.VISIBLE);
        }else{
            batalkanPesanan.setVisibility(View.INVISIBLE);
        }

        getDetail();

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(LihatDetailPesananActivity.this, PesananSayaActivity.class);
                finish();
                startActivity(a);
            }
        });

        //Button Tampilkan Produk Pesanan
        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LihatDetailPesananActivity.this, ProdukPesananActivity.class);
                String id = IDPesanan;
                i.putExtra("id_transaksi", id);
                startActivity(i);
            }
        });

        //Button Batalkan Pesanan
        batalkanPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder( LihatDetailPesananActivity.this)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Konfirmasi Batal")
                        .setMessage("Apakah anda yakin ingin membatalkan?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                batalkanPesanan();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        })
                        .create().show();
            }
        });
    }
    private void getDetail(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        String id = getIntent().getStringExtra("id");

        Call<DataPesanan> call = gtgClient.getDetailPesanan(token, id);
        tanggal.setVisibility(View.GONE);
        waktu.setVisibility(View.GONE);
        alamat.setVisibility(View.GONE);
        subttotal.setVisibility(View.GONE);
        ogkir.setVisibility(View.GONE);
        total.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<DataPesanan>() {
            @Override
            public void onResponse(Call<DataPesanan> call, Response<DataPesanan> response) {
                DataPesanan dataPesanan = response.body();
                if(dataPesanan != null){
                    tanggal.setVisibility(View.VISIBLE);
                    waktu.setVisibility(View.VISIBLE);
                    alamat.setVisibility(View.VISIBLE);
                    subttotal.setVisibility(View.VISIBLE);
                    ogkir.setVisibility(View.VISIBLE);
                    total.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    List<DpesananItem> dpesananItems = dataPesanan.getDpesanan();
                    for(DpesananItem item: dpesananItems){
                        tanggal.setText(item.getTanggal());
                        waktu.setText(item.getWaktu());
                        alamat.setText(item.getAlamat());
                        subttotal.setText("Rp."+item.getSubtotal());
                        total.setText("Rp. "+item.getTotal());
                        ogkir.setText("Rp. "+item.getBiayaKirim());
                        st = item.getStatusTransaksi();
                        status.setText(item.getStatusTransaksi());
                        lat = (float) item.getLatitude();
                        longs = (float) item.getLongitude();
                        if(item.getStatusJemput().equals("Jemput Langsung")){
                            tulAlamat.setVisibility(View.GONE);
                            alamat.setVisibility(View.GONE);
                            lihatLokasi.setVisibility(View.GONE);
                        }else if(item.getStatusJemput().equals("COD")){
                            tulAlamat.setVisibility(View.VISIBLE);
                            alamat.setVisibility(View.VISIBLE);
                            lihatLokasi.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DataPesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                tanggal.setVisibility(View.VISIBLE);
                waktu.setVisibility(View.VISIBLE);
                alamat.setVisibility(View.VISIBLE);
                subttotal.setVisibility(View.VISIBLE);
                ogkir.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);
            }
        });
    }

    private void batalkanPesanan(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        String id = getIntent().getStringExtra("id");

        Call<Pesan> call = gtgClient.batalkanPesanan(token, id);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                Pesan pesan = response.body();
                if(pesan != null) {
                    progressBar.setVisibility(View.GONE);
                    batalkanPesanan.setVisibility(View.GONE);
                    displayNotification("Notifikasi Transaksi", "Pesanan Berhasil Dibatalkan");
                    finish();
                    String pesans = "Pesanan Berhasil Dibatalkan";
                    Intent in = new Intent(LihatDetailPesananActivity.this, PesananSayaActivity.class);
                    in.putExtra("pesan", pesans);
                    startActivity(in);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LihatDetailPesananActivity.this, "Gagal Membatalkan Pesanan", Toast.LENGTH_SHORT).show();
                    batalkanPesanan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                Toast.makeText(LihatDetailPesananActivity.this, "Gagal Mengakses Server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                batalkanPesanan.setVisibility(View.VISIBLE);
            }
        });
    }

    private void displayNotification(String title, String message){
        String CHANNEL_ID = "com.example.grocerytogo.CH01";
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel Notifikasi Transaksi",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notif)
                .setContentTitle(title)
                .setContentText(message)
                .build();

        notificationManager.notify(123,notification);
    }
}