package com.example.grocerytogo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.BarangKategoriActivity;
import com.example.grocerytogo.HomeFragment;
import com.example.grocerytogo.KeranjangSayaFragment;
import com.example.grocerytogo.Koneksi;
import com.example.grocerytogo.MainActivity;
import com.example.grocerytogo.R;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangBerdasarkanKategoriAdapter
        extends RecyclerView.Adapter<BarangBerdasarkanKategoriAdapter.BarangBersarkanKategoriViewHolder> {

    Context context;
    public Integer a;

    ArrayList<BarangBerdasarKategori> listBarang = new ArrayList<>();

    public void setListBarang(ArrayList<BarangBerdasarKategori> listBarang){
        this.listBarang = listBarang;
        notifyDataSetChanged();
    }

    public void setListFilter(ArrayList<BarangBerdasarKategori> listFilter){
        listBarang = listFilter;
        notifyDataSetChanged();
    }

    public class BarangBersarkanKategoriViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaBarang, hargaBarang, jumlah;
        ImageView gambarBarang;
        Button tambah, kurang, sebelumTambah, tambahKeranjang;

        public BarangBersarkanKategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.textBarang);
            hargaBarang = itemView.findViewById(R.id.textHargaBarang);
            gambarBarang = itemView.findViewById(R.id.imageBarang);
            jumlah = itemView.findViewById(R.id.textJumlah);
            tambah = itemView.findViewById(R.id.btn_tambah1);
            kurang = itemView.findViewById(R.id.btn_kurang1);
            sebelumTambah = itemView.findViewById(R.id.btn_tambahProduk);
            tambahKeranjang = itemView.findViewById(R.id.tambah_keranjang);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onClick(view, listBarang.get(getAdapterPosition()));
            }
        }
    }

    public interface KlikBarang{
        void onClick(View view, BarangBerdasarKategori barangBerdasarKategori);
    }

    KlikBarang listener = null;

    public void setListener(KlikBarang listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public BarangBersarkanKategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_barang_kategori, parent, false);
        BarangBersarkanKategoriViewHolder viewHolder = new BarangBersarkanKategoriViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BarangBersarkanKategoriViewHolder viewHolder, int position) {
        BarangBerdasarKategori barang = listBarang.get(position);
        viewHolder.namaBarang.setText(barang.namaProduk);
        viewHolder.hargaBarang.setText(barang.hargaProduk.toString());
        Picasso.get().load(barang.gambar).into(viewHolder.gambarBarang);
//        viewHolder.gambarBarang.setImageResource(barang.gambar.intValue());

        viewHolder.tambah.setVisibility(View.GONE);
        viewHolder.kurang.setVisibility(View.GONE);
        viewHolder.jumlah.setVisibility(View.GONE);
        viewHolder.tambahKeranjang.setVisibility(View.GONE);
        viewHolder.sebelumTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tambah.setVisibility(View.VISIBLE);
                viewHolder.kurang.setVisibility(View.VISIBLE);
                viewHolder.jumlah.setVisibility(View.VISIBLE);
                viewHolder.tambahKeranjang.setVisibility(View.VISIBLE);
                viewHolder.sebelumTambah.setVisibility(View.GONE);
                viewHolder.jumlah.setText(String.valueOf(1));
            }
        });

        viewHolder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = Integer.parseInt(viewHolder.jumlah.getText().toString());
                a++;
                viewHolder.jumlah.setText(String.valueOf(a));
            }
        });

        viewHolder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = Integer.parseInt(viewHolder.jumlah.getText().toString());
                a--;
                if(a == 0) {
                    viewHolder.tambah.setVisibility(View.GONE);
                    viewHolder.kurang.setVisibility(View.GONE);
                    viewHolder.tambahKeranjang.setVisibility(View.GONE);
                    viewHolder.jumlah.setVisibility(View.GONE);
                    viewHolder.sebelumTambah.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.jumlah.setText(String.valueOf(a));
                }
            }
        });

        viewHolder.tambahKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api = context.getString(R.string.apiGTG);
                Koneksi koneksi = new Koneksi();
                GtgClient gtgClient = koneksi.setGtgClient(api);

                SharedPreferences preferences = context.getSharedPreferences("com.example.grocerytogo", Context.MODE_PRIVATE);
                String token = preferences.getString("TOKEN","");
                Integer id = Integer.valueOf(preferences.getString("id", ""));

                Call<Pesan> call = gtgClient.tambahBarang(token, id, barang.idProduk,  a);
                call.enqueue(new Callback<Pesan>() {
                    @Override
                    public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                        Pesan pesan = response.body();
                        if(pesan != null) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Tambah Barang Ke Keranjang")
                                    .setMessage("Apakah yakin menambahkan barang ke keranjang?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            deleteItem(position);
                                            Toast.makeText(context.getApplicationContext(), "Berhasil Tambah", Toast.LENGTH_SHORT).show();
//                                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else {
                            Toast.makeText(context.getApplicationContext(), "Gagal Tambah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pesan> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "Gagal Mengakses Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    void deleteItem(int index) {
        listBarang.remove(index);
        notifyItemRemoved(index);
    }
}
