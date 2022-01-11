package com.example.grocerytogo;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.view.View.GONE;

import static androidx.constraintlayout.widget.ConstraintSet.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.BarangKeranjangSayaAdapter;
import com.example.grocerytogo.model.BarangKeranjangSaya;
import com.example.grocerytogo.model.KeranjangItem;
import com.example.grocerytogo.model.ListKeranjang;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangSayaFragment extends Fragment {

    private Button tambah, pesan;
    private TextView ubah_lokasi, textSub, textalamat, textBiaya, textTotal;
    private RecyclerView DataBarangKeranjangSaya;
    private BarangKeranjangSayaAdapter barangKeranjangSayaAdapter;
    private ArrayList<BarangKeranjangSaya> keranjangSayas;
    private Integer jenis_bayar, id, subal, total, biaya_kirim;
    private ConstraintLayout alamat;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String status_jemput, token, address;
    private float lat, slong, distance;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang_saya, container, false);

        tambah = view.findViewById(R.id.btn_rambahproduk);
        pesan = view.findViewById(R.id.btn_pesan);
        ubah_lokasi = view.findViewById(R.id.ubah_lokasi);
        DataBarangKeranjangSaya = view.findViewById(R.id.ListKeranjangSaya);
        radioGroup = view.findViewById(R.id.rg);
        jenis_bayar = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(jenis_bayar);
        alamat = view.findViewById(R.id.constraintAlamat);
        textSub = view.findViewById(R.id.textView45);
        textalamat = view.findViewById(R.id.textView40);
        textBiaya = view.findViewById(R.id.textView46);
        textTotal = view.findViewById(R.id.textView47);
        progressBar = view.findViewById(R.id.progressBar10);

        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");
        id = Integer.valueOf(preferences.getString("id", ""));

        alamat.setVisibility(View.GONE);

        getKeranjangSaya();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.langsung:
                        alamat.setVisibility(GONE);
                        status_jemput = "Jemput Langsung";
                        biaya_kirim = 0;
                        lat = 0;
                        slong = 0;
                        address = null;
                        break;
                    case R.id.cod:
                        alamat.setVisibility(VISIBLE);
                        status_jemput = "COD";
                        lat = preferences.getFloat("LATITUDE", 0);
                        slong = preferences.getFloat("LONGITUDE", 0);
                        address= preferences.getString("ADDRESS","");
                        textalamat.setText((address));
                        distance = Float.parseFloat(preferences.getString("DISTANCE", ""));
                        Toast.makeText(getContext(), distance+"km", Toast.LENGTH_SHORT).show();
                        biaya_kirim = (int) (Math.ceil(distance)) * 6000;
                        break;
                }
                total = subal + biaya_kirim;
                textBiaya.setText("Rp. "+String.valueOf(biaya_kirim));
                textSub.setText("Rp. "+ subal);
                textTotal.setText("Rp. "+ total);
            }
        });

        //Button Tambah Produk Pesanan, Fragment ke Fragment
        tambah.setOnClickListener(view2 -> {
            getFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        });

        //Button Pesan
        pesan.setOnClickListener(view2 -> {
            if(distance <= 30) {
                transaksi();
            }else{
                Toast.makeText(getContext(), "Jarak Maksimum Lokasi Hanya 30 KM", Toast.LENGTH_SHORT).show();
            }
        });

        //Text View Ubah Lokasi
        ubah_lokasi.setOnClickListener(view2 -> {
            Intent in = new Intent(getActivity(), LokasiActivity.class);
            startActivity(in);

        });
        return view;
    }

    private void getKeranjangSaya(){
        subal = 0; total = 0;

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);
        progressBar.setVisibility(View.VISIBLE);
        pesan.setVisibility(View.INVISIBLE);
        tambah.setVisibility(View.INVISIBLE);
        radioButton.setEnabled(false);
        Call<ListKeranjang> call = gtgClient.getKeranjang(token, id);
        call.enqueue(new Callback<ListKeranjang>() {
            @Override
            public void onResponse(Call<ListKeranjang> call, Response<ListKeranjang> response) {
                ListKeranjang listKeranjang = response.body();
                keranjangSayas = new ArrayList<>();
                if(listKeranjang != null){
                    progressBar.setVisibility(GONE);
                    pesan.setVisibility(View.VISIBLE);
                    tambah.setVisibility(View.VISIBLE);
                    radioButton.setEnabled(true);
                    List<KeranjangItem> keranjangItems = listKeranjang.getKeranjang();
                    for(KeranjangItem item : keranjangItems){
                        BarangKeranjangSaya barangKeranjangSaya = new BarangKeranjangSaya(
                                item.getNamaBarang(),
                                item.getHargaBarang(),
                                api+item.getGambar(),
                                item.getJumlah(),
                                item.getIdBarang(),
                                subal += item.getHargaBarang()*item.getJumlah()
                        );
                        keranjangSayas.add(barangKeranjangSaya);
                    }
                    viewRecyclerView(keranjangSayas);
                }
            }

            @Override
            public void onFailure(Call<ListKeranjang> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void transaksi(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);
        progressBar.setVisibility(View.VISIBLE);

        Call<Pesan> call = gtgClient.transaksi(token, address, biaya_kirim, lat, slong, status_jemput, id);
        if (keranjangSayas.isEmpty()) {
            Toast.makeText(getContext(), "Pilih Barang Pesanan Dahulu", Toast.LENGTH_SHORT).show();
        } else {
            call.enqueue(new Callback<Pesan>() {
                @Override
                public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                    Pesan pesan = response.body();
                    if (pesan != null) {
                        progressBar.setVisibility(View.GONE);
                        displayNotification("Transaksi", "Pesanan Berhasil Diproses");
                        Toast.makeText(getContext(), "Berhasil Memesan", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getActivity(), PesananSayaActivity.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(getContext(), "Pesanan Gagal", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(GONE);

                    }
                }

                @Override
                public void onFailure(Call<Pesan> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(GONE);
                }
            });
        }

    }

    private void viewRecyclerView(ArrayList<BarangKeranjangSaya> listKeranjang){
        //Set Adapter dan Recycler View
        barangKeranjangSayaAdapter = new BarangKeranjangSayaAdapter();
        barangKeranjangSayaAdapter.setListBarangKeranjangSaya(listKeranjang);
        DataBarangKeranjangSaya.setAdapter(barangKeranjangSayaAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        DataBarangKeranjangSaya.setLayoutManager(layout);
    }

    private void displayNotification(String title, String message){
        String CHANNEL_ID = "com.example.grocerytogo.CH01";
        NotificationManager notificationManager
                = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel Notifikasi Transaksi",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notif)
                .setContentTitle(title)
                .setContentText(message)
                .build();

        notificationManager.notify(123,notification);
    }
}